## Interface

接口其实就是一种规范。

缺点：但是在java8之前，接口修改的时候，实现它的类也必须跟着修改。

改进：为了解决接口的修改与现有的实现类不兼容的问题。新的Interface可以使用`default`和`static`修饰，这样就可以在接口里面写方法体，实现类不必重写这个方法。

1.`default`修饰的方法，是普通的实例方法，可以用`this`调用，也可以被子类继承、重写。

2.`static`修饰的方法，使用和一般的静态方法一样。但是它不能被子类继承，只能用`Interface`调用。

### 问题：在Java8中，接口与抽象类有什么区别？

1.首先是接口与类的区别，主要有：

- 接口多实现，类单继承
- 接口的方法是 public abstract 修饰，变量是 public static final 修饰。 abstract class 可以用其他修饰符

2.interface 的方法是更像是一个扩展插件。而 abstract class 的方法是要继承的。

> 解释：
>
> 在 Java 中，接口的方法默认是 `public abstract` 修饰的，接口的变量默认是 `public static final` 修饰的。也就是说，即使不显式地加上这些修饰符，接口的方法和变量也会自动具有这些修饰符。
>
> `abstract class` 是抽象类，可以使用其他修饰符来修饰其方法和变量。抽象类中的方法可以是 `public`、`protected` 或 `private`，也可以是 `abstract` 或非 `abstract` 的。抽象类中的变量可以是实例变量或类变量，并且可以使用任何访问修饰符（`public`、`protected`、`private`）来修饰。
>
> 总结：
> - 接口中的方法默认是 `public abstract` 修饰的。（必须，只能，不能改成Private,protected）
> - 接口中的变量默认是 `public static final` 修饰的。
> - 抽象类中的方法和变量可以使用其他修饰符。

## functional interface函数式接口

定义：也称为SAM接口（Single Abstract Method interfaces），(有且只有一个抽象方法，但是可以有多个非抽象方法) 的接口。

java8中专门有一个包放函数式接口`java.util.function`，该包下的所有接口都有`@FunctionalInterface`注解，提供函数式编程。

## Lambda表达式

- 便利

- 替代匿名内部类

  过去给方法传动态参数的唯一方法是使用匿名内部类。比如：

  1.`Runnable`接口

  ```java
  // 匿名内部类
  new Thread(new Runnable(){
  	@Override
      public void run(){
          sout;
      }
  }).start();
  // 使用lambda
  new Thread(()->sout;).start();
  ```

  2.`Comparator`接口

  ```java
  Collections.sort(list, new Comparator<Integer>(){
    	@Override
      public int compare(Integer o1, Integer o2){
          return o1 - o2;
  	}
  });
  
  // lambda
  Collections.sort(list, (Integer o1, Integer o2)->o1 - o2);
  // 分解开
  Comparator<Integer> comparator = (Integer o1, Integer o2)->o1 - o2;
  Collections.sort(list, comparator);
  ```

  方法的引用：

  Java8允许使用 $::$ 关键字传递方法或者构造函数引用，无论如何，表达式返回的类型必须是 `functional-interface`。

  访问变量

  ```java
  int i = 0;
  Collections.sort(list, (Integer o1, Integer o2)->o1 - i);
  // i = 3;  这yi
  ```

  lambda表达式可以引用外边的变量，但是该变量默认拥有 **final** 属性，不能被修改，如果被修改，编译就会报错。

    

  延迟执行：

  stream流的操作不会立即执行，而是等需要返回一个非`stream`的方法后才执行。

## Optional

在[阿里巴巴开发手册关于 Optional 的介绍](https://share.weiyun.com/ThuqEbD5)中这样写到：

> 防止 NPE，是程序员的基本修养，注意 NPE 产生的场景：
>
> 1） 返回类型为基本数据类型，return 包装数据类型的对象时，自动拆箱有可能产生 NPE。
>
> 反例：public int f() { return Integer 对象}， 如果为 null，自动解箱抛 NPE。
>
> 2） 数据库的查询结果可能为 null。
>
> 3） 集合里的元素即使 isNotEmpty，取出的数据元素也可能为 null。
>
> 4） 远程调用返回对象时，一律要求进行空指针判断，防止 NPE。
>
> 5） 对于 Session 中获取的数据，建议进行 NPE 检查，避免空指针。
>
> 6） 级联调用 obj.getA().getB().getC()；一连串调用，易产生 NPE。
>
> 正例：使用 JDK8 的 Optional 类来防止 NPE 问题。

------

```java
Zoo zoo = getZoo();
if(zoo != null){
   Dog dog = zoo.getDog();
   if(dog != null){
      int age = dog.getAge();
      System.out.println(age);
   }
}

// 使用Optional
Optional.ofNullable(zoo).map(o->o.getDog()).map(d->d.getAge()).ifPresent(age->sout(age));
```

创建Optional的方法：`Optional.ofNullable()`, `Optional.of()`, `Optional.empty()`

其中`ofNullable`方法与`of`方法唯一区别就是当`value`为`null`时，`ofNullable`返回的是`EMPTY`，`of`会抛出`NPE`异常。

### `map()` 和`flatmap()`有什么区别?

`map`和`flatmap`都是将一个函数应用于集合的每个元素，但不同的是`map`返回一个新的集合，`flatMap`是将每个元素都映射为一个集合，最后将这个集合展平。

```java
public class MapAndFlatMapExample {
    public static void main(String[] args) {
        List<String[]> listOfArrays = Arrays.asList(
                new String[]{"apple", "banana", "cherry"},
                new String[]{"orange", "grape", "pear"},
                new String[]{"kiwi", "melon", "pineapple"}
        );

        List<String[]> mapResult = listOfArrays.stream()
                .map(array -> Arrays.stream(array).map(String::toUpperCase).toArray(String[]::new))
                .collect(Collectors.toList());

        System.out.println("Using map:");
        mapResult.forEach(arrays-> System.out.println(Arrays.toString(arrays)));

        List<String> flatMapResult = listOfArrays.stream()
                .flatMap(array -> Arrays.stream(array).map(String::toUpperCase))
                .collect(Collectors.toList());

        System.out.println("Using flatMap:");
        System.out.println(flatMapResult);
    }
}
```

```plain
Using map:
[[APPLE, BANANA, CHERRY], [ORANGE, GRAPE, PEAR], [KIWI, MELON, PINEAPPLE]]

Using flatMap:
[APPLE, BANANA, CHERRY, ORANGE, GRAPE, PEAR, KIWI, MELON, PINEAPPLE]
```

判断 value 是否为 null

```java
/**
* value是否为null
*/
public boolean isPresent() {
    return value != null;
}
/**
* 如果value不为null执行consumer.accept
*/
public void ifPresent(Consumer<? super T> consumer) {
   if (value != null)
    consumer.accept(value);
}
```

获取 value

```java
/**
* Return the value if present, otherwise invoke {@code other} and return
* the result of that invocation.
* 如果value != null 返回value，否则返回other的执行结果
*/
public T orElseGet(Supplier<? extends T> other) {
    return value != null ? value : other.get();
}

/**
* 如果value != null 返回value，否则返回T
*/
public T orElse(T other) {
    return value != null ? value : other;
}

/**
* 如果value != null 返回value，否则抛出参数返回的异常
*/
public <X extends Throwable> T orElseThrow(Supplier<? extends X> exceptionSupplier) throws X {
        if (value != null) {
            return value;
        } else {
            throw exceptionSupplier.get();
        }
}
/**
* value为null抛出NoSuchElementException，不为空返回value。
*/
public T get() {
  if (value == null) {
      throw new NoSuchElementException("No value present");
  }
  return value;
}
```

过滤值

```java
/**
* 1. 如果是empty返回empty
* 2. predicate.test(value)==true 返回this，否则返回empty
*/
public Optional<T> filter(Predicate<? super T> predicate) {
        Objects.requireNonNull(predicate);
        if (!isPresent())
            return this;
        else
            return predicate.test(value) ? this : empty();
}
```

## Date-Time API

这是对`java.util.Date`强有力的补充，解决了Date类的大部分痛点：

1、非线程安全

2、时区处理麻烦

3、各种格式化、和计算繁琐

4、设计有缺陷，Date类同时包含日期和时间；还有一个`java.sql.Date`容易混淆。

### java.time主要类

`java.util.Date`既包含日期又包含时间，而`java.time`把它们进行了分离

```java
LocalDateTime.class //日期+时间 format: yyyy-MM-ddTHH:mm:ss.SSS
LocalDate.class //日期 format: yyyy-MM-dd
LocalTime.class //时间 format: HH:mm:ss
```

### 格式化

Java8之前：

```java
public void oldFormat(){
    Date now = new Date();
    //format yyyy-MM-dd
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    String date  = sdf.format(now);
    System.out.println(String.format("date format : %s", date));

    //format HH:mm:ss
    SimpleDateFormat sdft = new SimpleDateFormat("HH:mm:ss");
    String time = sdft.format(now);
    System.out.println(String.format("time format : %s", time));

    //format yyyy-MM-dd HH:mm:ss
    SimpleDateFormat sdfdt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    String datetime = sdfdt.format(now);
    System.out.println(String.format("dateTime format : %s", datetime));
}
```

Java8之后：

```java
public void newFormat(){
    //format yyyy-MM-dd
    LocalDate date = LocalDate.now();
    System.out.println(String.format("date format : %s", date));

    //format HH:mm:ss
    LocalTime time = LocalTime.now().withNano(0);
    System.out.println(String.format("time format : %s", time));

    //format yyyy-MM-dd HH:mm:ss
    LocalDateTime dateTime = LocalDateTime.now();
    DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    String dateTimeStr = dateTime.format(dateTimeFormatter);
    System.out.println(String.format("dateTime format : %s", dateTimeStr));
}
```

### 字符串转日期格式

Java8之前：

```java
//已弃用
Date date = new Date("2021-01-26");
//替换为
SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
Date date1 = sdf.parse("2021-01-26");
```

**Java 8 之后:** 

```java
LocalDate date = LocalDate.of(2021, 1, 26);
LocalDate.parse("2021-01-26");

LocalDateTime dateTime = LocalDateTime.of(2021, 1, 26, 12, 12, 22);
LocalDateTime.parse("2021-01-26 12:12:22");

LocalTime time = LocalTime.of(12, 12, 22);
LocalTime.parse("12:12:22");
```

Java8之前转换需要借助`SimpleDateFormat`类，而Java8之后只需要`LocalDate`、`LocalTime`、`LocalDateTime`的`of`或则`parse`方法。

### 日期计算

**Java 8 之前:** 

```java
public void afterDay(){
     //一周后的日期
     SimpleDateFormat formatDate = new SimpleDateFormat("yyyy-MM-dd");
     Calendar ca = Calendar.getInstance();
     ca.add(Calendar.DATE, 7);
     Date d = ca.getTime();
     String after = formatDate.format(d);
     System.out.println("一周后日期：" + after);

   //算两个日期间隔多少天，计算间隔多少年，多少月方法类似
     String dates1 = "2021-12-23";
   	 String dates2 = "2021-02-26";
     SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
     Date date1 = format.parse(dates1);
     Date date2 = format.parse(dates2);
     int day = (int) ((date1.getTime() - date2.getTime()) / (1000 * 3600 * 24));
     System.out.println(dates1 + "和" + dates2 + "相差" + day + "天");
     //结果：2021-02-26和2021-12-23相差300天
}
```

**Java 8 之后：**

```java
public void pushWeek(){
     //一周后的日期
     LocalDate localDate = LocalDate.now();
     //方法1
     LocalDate after = localDate.plus(1, ChronoUnit.WEEKS);
     //方法2
     LocalDate after2 = localDate.plusWeeks(1);
     System.out.println("一周后日期：" + after);

     //算两个日期间隔多少天，计算间隔多少年，多少月
     LocalDate date1 = LocalDate.parse("2021-02-26");
     LocalDate date2 = LocalDate.parse("2021-12-23");
     Period period = Period.between(date1, date2);
     System.out.println("date1 到 date2 相隔："
                + period.getYears() + "年"
                + period.getMonths() + "月"
                + period.getDays() + "天");
   //打印结果是 “date1 到 date2 相隔：0年9月27天”
     //这里period.getDays()得到的天是抛去年月以外的天数，并不是总天数
     //如果要获取纯粹的总天数应该用下面的方法
     long day = date2.toEpochDay() - date1.toEpochDay();
     System.out.println(date1 + "和" + date2 + "相差" + day + "天");
     //打印结果：2021-02-26和2021-12-23相差300天
}
```

### [获取指定日期](https://javaguide.cn/java/new-features/java8-common-new-features.html#%E8%8E%B7%E5%8F%96%E6%8C%87%E5%AE%9A%E6%97%A5%E6%9C%9F)

除了日期计算繁琐，获取特定一个日期也很麻烦，比如获取本月最后一天，第一天。

```java
public void getDay() {

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        //获取当前月第一天：
        Calendar c = Calendar.getInstance();
        c.set(Calendar.DAY_OF_MONTH, 1);
        String first = format.format(c.getTime());
        System.out.println("first day:" + first);

        //获取当前月最后一天
        Calendar ca = Calendar.getInstance();
        ca.set(Calendar.DAY_OF_MONTH, ca.getActualMaximum(Calendar.DAY_OF_MONTH));
        String last = format.format(ca.getTime());
        System.out.println("last day:" + last);

        //当年最后一天
        Calendar currCal = Calendar.getInstance();
        Calendar calendar = Calendar.getInstance();
        calendar.clear();
        calendar.set(Calendar.YEAR, currCal.get(Calendar.YEAR));
        calendar.roll(Calendar.DAY_OF_YEAR, -1);
        Date time = calendar.getTime();
        System.out.println("last day:" + format.format(time));
}
```

**Java 8 之后:** 

```java
public void getDayNew() {
    LocalDate today = LocalDate.now();
    //获取当前月第一天：
    LocalDate firstDayOfThisMonth = today.with(TemporalAdjusters.firstDayOfMonth());
    // 取本月最后一天
    LocalDate lastDayOfThisMonth = today.with(TemporalAdjusters.lastDayOfMonth());
    //取下一天：
    LocalDate nextDay = lastDayOfThisMonth.plusDays(1);
    //当年最后一天
    LocalDate lastday = today.with(TemporalAdjusters.lastDayOfYear());
    //2021年最后一个周日，如果用Calendar是不得烦死。
    LocalDate lastMondayOf2021 = LocalDate.parse("2021-12-31").with(TemporalAdjusters.lastInMonth(DayOfWeek.SUNDAY));
}
```

### [JDBC 和 java8](#jdbc-和-java8)

现在 jdbc 时间类型和 java8 时间类型对应关系是

1. `Date` ---> `LocalDate`
2. `Time` ---> `LocalTime`
3. `Timestamp` ---> `LocalDateTime`

而之前统统对应 `Date`，也只有 `Date`。

### 时区

> 时区：正式的时区划分为每隔经度 15° 划分一个时区，全球共 24 个时区，每个时区相差 1 小时。但为了行政上的方便，常将 1 个国家或 1 个省份划在一起，比如我国幅员宽广，大概横跨 5 个时区，实际上只用东八时区的标准时即北京时间为准。

java.util.Date 对象实质上存的是 1970 年 1 月 1 日 0 点（ GMT）至 Date 对象所表示时刻所经过的毫秒数。也就是说不管在哪个时区 new Date，它记录的毫秒数都一样，和时区无关。但在使用上应该把它转换成当地时间，这就涉及到了时间的国际化。java.util.Date 本身并不支持国际化，需要借助 TimeZone。

```java
//北京时间：Wed Jan 27 14:05:29 CST 2021
Date date = new Date();

SimpleDateFormat bjSdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//北京时区
bjSdf.setTimeZone(TimeZone.getTimeZone("Asia/Shanghai"));
System.out.println("毫秒数:" + date.getTime() + ", 北京时间:" + bjSdf.format(date));

//东京时区
SimpleDateFormat tokyoSdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
tokyoSdf.setTimeZone(TimeZone.getTimeZone("Asia/Tokyo"));  // 设置东京时区
System.out.println("毫秒数:" + date.getTime() + ", 东京时间:" + tokyoSdf.format(date));

//如果直接print会自动转成当前时区的时间
System.out.println(date);
//Wed Jan 27 14:05:29 CST 2021
```

在新特性中引入了 `java.time.ZonedDateTime` 来表示带时区的时间。它可以看成是 `LocalDateTime + ZoneId`。 

```java
//当前时区时间
ZonedDateTime zonedDateTime = ZonedDateTime.now();
System.out.println("当前时区时间: " + zonedDateTime);

//东京时间
ZoneId zoneId = ZoneId.of(ZoneId.SHORT_IDS.get("JST"));
ZonedDateTime tokyoTime = zonedDateTime.withZoneSameInstant(zoneId);
System.out.println("东京时间: " + tokyoTime);

// ZonedDateTime 转 LocalDateTime
LocalDateTime localDateTime = tokyoTime.toLocalDateTime();
System.out.println("东京时间转当地时间: " + localDateTime);

//LocalDateTime 转 ZonedDateTime
ZonedDateTime localZoned = localDateTime.atZone(ZoneId.systemDefault());
System.out.println("本地时区时间: " + localZoned);

//打印结果
当前时区时间: 2021-01-27T14:43:58.735+08:00[Asia/Shanghai]
东京时间: 2021-01-27T15:43:58.735+09:00[Asia/Tokyo]
东京时间转当地时间: 2021-01-27T15:43:58.735
当地时区时间: 2021-01-27T15:53:35.618+08:00[Asia/Shanghai]
```



