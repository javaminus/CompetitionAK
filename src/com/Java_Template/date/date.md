# LocalDate、LocalTime和LocalDateTime用法

## 一.LocalDate用法

### 1.1.获取当前时间，最大时间，最小时间

```java
/**
     * 获取当前时间，最大时间，最小时间
     */
    @Test
    public void demo01() {
        LocalDate now = LocalDate.now();
        LocalDate max = LocalDate.MAX;
        LocalDate min = LocalDate.MIN;
        log.info("当前时间：" + now);
        log.info("获取最大时间：" + max);
        log.info("获取最小时间：" + min);
    }
```

### 1.2.自定义日期

```java
/**
     * 自定义日期
     */
    @Test
    public void demo02() {
        LocalDate date = LocalDate.of(2019, 12, 24);
        log.info("自定义日期：" + date);
    }
```

### 1.3.获取当前日期是所在年的第几天、月的第几天、当前星期

```java
/**
     * 获取当前日期是所在年的第几天、月的第几天、当前星期
     */
    @Test
    public void demo03() {
        LocalDate now = LocalDate.now();
        int year = now.getDayOfYear();
        int month = now.getDayOfMonth();
        DayOfWeek week = now.getDayOfWeek();
        log.info("获取年份：" + year);
        log.info("获取月份：" + month);
        log.info("获取星期：" + week);
    }
```

### 1.4.获取当前月和当前月天数

```java
 /**
     * 获取当前月，当前月天数
     */
    @Test
    public void demo04() {
        LocalDate now = LocalDate.now();
        Month month = now.getMonth();
        int monthValue = now.getMonthValue();
        int i = now.lengthOfMonth();
        log.info("当前月：" + month);
        log.info("当前月：" + monthValue);
        log.info("当前月天数：" + i);
    }
```

### 1.5.获取当前年份的天数和是否是闰年

```java
 /**
     * 获取当前年份的天数和是否是闰年
     */
    @Test
    public void demo05() {
        LocalDate now = LocalDate.now();
        int i = now.lengthOfYear();
        boolean leapYear = now.isLeapYear();
        log.info("获取当前年的天数：" + i);
        log.info("获取当前年是否是闰年：" + leapYear);
    }
```

### 1.6.with指定年月日

```java
/**
     * with指定年月日
     */
    @Test
    public void demo06() {
        LocalDate now = LocalDate.now();
        LocalDate localDate = now.withDayOfMonth(1);
        LocalDate localDate1 = now.withDayOfYear(21);
        LocalDate localDate2 = now.withMonth(3);
        LocalDate localDate3 = now.withYear(2004);
        log.info("当月的指定日:" + localDate);
        log.info("获取今年第一月的指定日期:" + localDate1);
        log.info("今年指定月份，日为当天的日期:" + localDate2);
        log.info("指定年份的今天:" + localDate3);
    }
```

### 1.7.当前日期加减

```java
/**
     * 当前日期加减
     */
    @Test
    public void demo07() {
        LocalDate now = LocalDate.now();
        LocalDate localDate = now.minusDays(1);
        LocalDate localDate1 = now.minusWeeks(1);
        LocalDate localDate2 = now.minusMonths(1);
        LocalDate localDate3 = now.minusYears(1);
        LocalDate localDate4 = now.plusDays(1);
        LocalDate localDate5 = now.plusWeeks(1);
        LocalDate localDate6 = now.plusMonths(1);
        LocalDate localDate7 = now.plusYears(1);
        log.info("当前日期的前一天：" + localDate);
        log.info("当前日期的前一星期：" + localDate1);
        log.info("当前日期的前一个月：" + localDate2);
        log.info("当前日期的前一年：" + localDate3);
        log.info("当前日期的后一天：" + localDate4);
        log.info("当前日期的后一星期：" + localDate5);
        log.info("当前日期的后一月：" + localDate6);
        log.info("当前日期的后一年：" + localDate7);
    }
```

## 二.LocalTime

### 2.1.获取当前时间

```java
/**
     * 获取当前时间
     */
    @Test
    public void demo08() {
        LocalTime now = LocalTime.now();
        log.info("当前时间：" + now);
    }
```

### 2.2.指定时分秒

```java
 /**
     * 指定时分秒
     */
    @Test
    public void demo09() {
        LocalTime time1 = LocalTime.of(12, 9);
        LocalTime time2 = LocalTime.of(12, 9, 10);
        log.info("指定时分：" + time1);
        log.info("指定时分秒：" + time2);
    }
```

### 2.3.获取当前时间的时分秒

```java
  /**
     * 获取当前时间的时分秒
     */
    @Test
    public void demo10() {
        LocalTime now = LocalTime.now();
        int hour = now.getHour();
        int minute = now.getMinute();
        int second = now.getSecond();
        log.info("当前时间的时：" + hour);
        log.info("当前时间的分：" + minute);
        log.info("当前时间的秒：" + second);
    }
```

### 2.4.替换当前的时分秒

```java
  /**
     * 替换时分秒
     */
    @Test
    public void demo11() {
        LocalTime now = LocalTime.now();
        LocalTime time1 = now.withHour(1);
        LocalTime time2 = now.withMinute(21);
        LocalTime time3 = now.withSecond(31);
        log.info("替换当前的时：" + time1);
        log.info("替换当前的分：" + time2);
        log.info("替换当前的秒：" + time3);
    }
```

### 2.5.当前时间加减

```java
 /**
     * 当前时间加减
     */
    @Test
    public void demo12() {
        LocalTime now = LocalTime.now();
        LocalTime time1 = now.minusHours(1);
        LocalTime time2 = now.minusMinutes(1);
        LocalTime time3 = now.minusSeconds(1);
        LocalTime time4 = now.plusHours(1);
        LocalTime time5 = now.plusMinutes(1);
        LocalTime time6 = now.plusSeconds(1);
        log.info("当前时间减一小时：" + time1);
        log.info("当前时间减一分钟：" + time2);
        log.info("当前时间减一秒：" + time3);
        log.info("当前时间加一小时：" + time4);
        log.info("当前时间加一分钟：" + time5);
        log.info("当前时间加一秒：" + time6);
    }
```

## 三.LocalDateTime

### 3.1.获取当前时间

```java
  /**
     * 获取当前时间
     */
    @Test
    public void demo13() {
        LocalDateTime now = LocalDateTime.now();
        log.info("获取当前时间：" + now);
    }
```

### 3.2.指定日期

```java
 /**
     * 指定当前时间
     */
    @Test
    public void demo14() {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime time1 = now.withYear(2012);
        LocalDateTime time2 = now.withMonth(12);
        LocalDateTime time3 = now.withDayOfMonth(23);
        LocalDateTime time4 = now.withHour(8);
        LocalDateTime time5 = now.withMinute(8);
        LocalDateTime time6 = now.withSecond(8);
        log.info("指定当前年份：" + time1);
        log.info("指定当前月份：" + time2);
        log.info("指定当前日：" + time3);
        log.info("指定当前时：" + time4);
        log.info("指定当前分：" + time5);
        log.info("指定当前秒：" + time6);
    }
```

### 3.3.获取当前日期的单个参数

```java
/**
     * 获取当前日期的单个参数
     */
    @Test
    public void demo15() {
        LocalDateTime now = LocalDateTime.now();
        int dayOfYear = now.getDayOfYear();
        int dayOfMonth = now.getDayOfMonth();
        int monthValue = now.getMonthValue();
        DayOfWeek dayOfWeek = now.getDayOfWeek();
        int hour = now.getHour();
        int minute = now.getMinute();
        int second = now.getSecond();
        log.info("获取当前日期在今年的第几天：" + dayOfYear);
        log.info("获取当前日期是本月的第几天：" + dayOfMonth);
        log.info("获取当前的月份：" + monthValue);
        log.info("获取当前的星期：" + dayOfWeek);
        log.info("获取当前的时：" + hour);
        log.info("获取当前的分：" + minute);
        log.info("获取当前的秒：" + second);
    }
```

### 3.4.当前日期加减

```java
   /**
     * 当前日期加减
     */
    @Test
    public void demo16() {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime time1 = now.minusYears(1);
        LocalDateTime time2 = now.minusMonths(12);
        LocalDateTime time3 = now.minusWeeks(1);
        LocalDateTime time4 = now.minusDays(21);
        LocalDateTime time5 = now.minusHours(21);
        LocalDateTime time6 = now.minusMinutes(21);
        LocalDateTime time7 = now.minusSeconds(21);
        LocalDateTime time8 = now.plusYears(1);
        LocalDateTime time9 = now.plusMonths(12);
        LocalDateTime time10 = now.plusWeeks(1);
        LocalDateTime time11 = now.plusDays(21);
        LocalDateTime time12 = now.plusHours(21);
        LocalDateTime time13 = now.plusMinutes(21);
        LocalDateTime time14 = now.plusSeconds(21);
        log.info("当前时间减一年：" + time1);
        log.info("当前时间减12个月：" + time2);
        log.info("当前时间减一星期：" + time3);
        log.info("当前时间减21天：" + time4);
        log.info("当前时间减21小时：" + time5);
        log.info("当前时间减21分钟：" + time6);
        log.info("当前时间减21秒：" + time7);
        log.info("当前时间加一年：" + time8);
        log.info("当前时间加12个月：" + time9);
        log.info("当前时间加一星期：" + time10);
        log.info("当前时间加21天：" + time11);
        log.info("当前时间加21小时：" + time12);
        log.info("当前时间加21分钟：" + time13);
        log.info("当前时间加21秒：" + time14);
    }
```

### 3.5.获取当前日期和指定日期

```java
 /**
     * 获取当前日期和指定日期
     */
    @Test
    public void demo17() {
        LocalDateTime now = LocalDateTime.of(LocalDate.now(), LocalTime.now());
        LocalDateTime time1 = LocalDateTime.of(2010, Month.JULY, 12, 12, 12);
        log.info("获取当前日期：" + now);
        log.info("指定日期：" + time1);
    }
```

## 四.开发常用

### 4.1.转换

```java
  /**
     * 转化
     */
    @Test
    public void demo18() {
        LocalDateTime now1 = LocalDateTime.now();
        LocalDate date1 = now1.toLocalDate();
        LocalTime time1 = now1.toLocalTime();
        LocalDate now2 = LocalDate.now();
        LocalDateTime date2 = now2.atStartOfDay();
        String format = now1.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:ss:mm"));
        Long newSecond = LocalDateTime.now().toInstant(ZoneOffset.of("+8")).toEpochMilli();
        log.info("LocalDateTime转LocalDate：" + date1);
        log.info("LocalDateTime转LocalTime：" + time1);
        log.info("LocalDate转LocalDateTime：" + date2);
        log.info("LocalDateTime格式化：" + format);
        log.info("获取毫秒时间：" + newSecond);
    }
```

### 4.2.计算差值

```java
 /**
     * 计算差值
     */
    @Test
    public void demo19() {
        String date = "2019-01-01 01:12:12";
        LocalDateTime parseDate = LocalDateTime.parse(date, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:ss:mm"));
        Duration betweenDate = Duration.between(parseDate, LocalDateTime.now());
        long days = betweenDate.toDays();
        long hours = betweenDate.toHours();
        long minutes = betweenDate.toMinutes();

        LocalDate parseDate1 = parseDate.toLocalDate();
        Period betweenDate1 = Period.between(parseDate1, LocalDate.now());
        int months = betweenDate1.getMonths();
        int days1 = betweenDate1.getDays();
        int months1 = betweenDate1.getMonths();
        int years = betweenDate1.getYears();
        log.info("天的差值:" + days);
        log.info("时差值:" + hours);
        log.info("分差值:" + minutes);
        log.info("比较月之间差值:" + months);
        log.info("比较天之间的差值:" + days1);
        log.info("月份榨汁:" + months1);
        log.info("年份差值:" + years);
    }
```

### 4.3.获取年、月、周的第一天和最后一天

```java
   /**
     * 极限值
     */
    @Test
    public void demo20() {
        LocalDateTime now = LocalDateTime.now();
     
        LocalDateTime monday = now.with(DayOfWeek.MONDAY);
        LocalDateTime sunday = now.with(DayOfWeek.SUNDAY);
        log.info("当前周的周一：" + monday);
        log.info("当前周的周日：" + sunday);
        LocalDateTime firstday = now.with(TemporalAdjusters.firstDayOfMonth());
        LocalDateTime lastDay = now.with(TemporalAdjusters.lastDayOfMonth());
        log.info("当前月的第一天：" + firstday);
        log.info("当前月的最后一天：" + lastDay);
        LocalDate start = LocalDate.now().with(TemporalAdjusters.firstDayOfYear());
        LocalDate end = LocalDate.now().with(TemporalAdjusters.lastDayOfYear());
        log.info("今年的第一天：" + start);
        log.info("今年的最后一天：" + end);
        LocalDate lastStart = LocalDate.now().plusYears(-1).with(TemporalAdjusters.firstDayOfYear());
        LocalDate lastEnd = LocalDate.now().plusYears(-1).with(TemporalAdjusters.lastDayOfYear());
        log.info("去年的第一天：" + lastStart);
        log.info("去年的最后一天：" + lastEnd);
        LocalDate nextStart1 = LocalDate.now().plusYears(1).with(TemporalAdjusters.firstDayOfYear());
        LocalDate nextStart2 = LocalDate.now().with(TemporalAdjusters.firstDayOfNextYear());
        LocalDate nextEnd1 = LocalDate.now().plusYears(1).with(TemporalAdjusters.lastDayOfYear());
        log.info("明年的第一天：" + nextStart1);
        log.info("明年的第一天：" + nextStart2);
        log.info("明年的最后一天：" + nextEnd1);
    }
```

### 4.4.获得当天的最大时间和最小时间

```java
   /**
     * 获取今天的最大时间和最小时间
     */
    @Test
    public void demo21() {
        LocalDateTime now = LocalDateTime.now();
        // 获取当前星期的第一天和最后一天
        LocalDateTime startTime = LocalDateTime.of(LocalDate.now(), LocalTime.MAX);
        LocalDateTime endTime = LocalDateTime.of(LocalDate.now(), LocalTime.MIN);
        log.info("当天最小时间：" + startTime);
        log.info("当天最大时间：" + endTime);
    }
```

