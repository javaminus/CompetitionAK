## JShell

## 模块化系统

任意一个jar文件，只要加上一个模块描述文件（`module-info.java`），就可以升级为一个模块。

使代码的可重用性更好。

## G1成为默认的垃圾回收器

在 Java 8 的时候，默认垃圾回收器是 Parallel Scavenge（新生代）+Parallel Old（老年代）。到了 Java 9, CMS 垃圾回收器被废弃了，**G1（Garbage-First Garbage Collector）** 成为了默认垃圾回收器。

G1 还是在 Java 7 中被引入的，经过两个版本优异的表现成为成为默认垃圾回收器。

## 快速创建不可变集合

增加了`List.of()`、`Set.of()`、`Map.of()`和`Map.ofEntries()`等工厂方法来创建不可变集合。

```java
List.of("Java", "C++");
Set.of("Java", "C++");
Map.of("Java", 1, "C++", 2);
```

使用 `of()` 创建的集合为不可变集合，不能进行添加、删除、替换、 排序等操作，不然会报 `java.lang.UnsupportedOperationException` 异常。 

## String存储结构优化

Java 8 及之前的版本，`String` 一直是用 `char[]` 存储。在 Java 9 之后，`String` 的实现改用 `byte[]` 数组存储字符串，节省了空间。 

## 接口私有方法

## try-with-resources增强

在 Java 9 之前，我们只能在 `try-with-resources` 块中声明变量：

```java
try (Scanner scanner = new Scanner(new File("testRead.txt"));
    PrintWriter writer = new PrintWriter(new File("testWrite.txt"))) {
    // omitted
}
```

在 Java 9 之后，在 `try-with-resources` 语句中可以使用 effectively-final 变量。

```java
final Scanner scanner = new Scanner(new File("testRead.txt"));
PrintWriter writer = new PrintWriter(new File("testWrite.txt"))
try (scanner;writer) {
    // omitted
}
```

**什么是 effectively-final 变量？** 简单来说就是没有被 `final` 修饰但是值在初始化后从未更改的变量。

正如上面的代码所演示的那样，即使 `writer` 变量没有被显示声明为 `final`，但它在第一次被赋值后就不会改变了，因此，它就是 effectively-final 变量。

## Stream & Optional 增强

## 进程 API

Java 9 增加了 `java.lang.ProcessHandle` 接口来实现对原生进程进行管理，尤其适合于管理长时间运行的进程。

## 响应式流 （ Reactive Streams ）

## 变量句柄