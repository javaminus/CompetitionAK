## AQS

全称：AbstractQueuedSynchronizer

翻译：**抽象队列同步器**  

位置：java.util.concurrent.locks包下面

AQS就是一个抽象类，主要用于构建**锁**和**同步器**

```java
public abstract class AbstractQueuedSynchronizer extends AbstractOwnableSynchronizer implements java.io.Serializable {
}
```

基于AQS的同步器有：ReentrantLock，Semaphore，ReentrantReadWriteLock，SynchronousQueue

### AQS原理

AQS的核心思想是，如果被请求的共享资源空闲，则将当前请求资源的线程设置为有效的工作线程，并且共享资源设置为锁定状态。如果被请求的共享资源被占用，那么就需要一套线程阻塞等待以及被唤醒时锁分配的机制，这个机制AQS是用**CLH 队列锁**实现的，即将暂时获取不到锁的线程加入到队列中。 

CLH(Craig,Landin,and Hagersten) 队列是一个**虚拟的双向队列**（虚拟的双向队列即不存在队列实例，仅存在结点之间的关联关系）。AQS 是将每条请求共享资源的线程封装成一个 CLH锁队列的一个结点（Node）来实现锁的分配。在CLH同步队列中，一个节点表示一个线程，它保存着线程的引用（thread）、当前节点在队列中的状态（waitStatus）、前驱节点（prev）、后继节点（next）。

CLH队列结构图：

![img](https://oss.javaguide.cn/p3-juejin/40cb932a64694262993907ebda6a0bfe~tplv-k3u1fbpfcp-zoom-1.png)

AQS（AbstractQueuedSynchronizer）的核心原理图：

![img](https://oss.javaguide.cn/github/javaguide/java/CLH.png)

AQS使用**int 成员变量state**表示同步状态，通过内置的 **线程等待队列** 来完成获取资源线程的排队工作。

state变量由vilatile修饰，用于展示当前临界资源的获锁情况。

```java
// 共享变量，使用volatile修饰保证线程可见性
private volatile int state;
```

另外，状态信息state可以通过protected类型的getState()、setState()和compareAndSetState()进行操作。并且，这几个方法都是**final**修饰的，在子类中无法被重写。

```java
//返回同步状态的当前值
protected final int getState() {
     return state;
}
 // 设置同步状态的值
protected final void setState(int newState) {
     state = newState;
}
//原子地（CAS操作）将同步状态值设置为给定值update如果当前同步状态的值等于expect（期望值）
protected final boolean compareAndSetState(int expect, int update) {
      return unsafe.compareAndSwapInt(this, stateOffset, expect, update);
}
```





