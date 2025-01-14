## Stream

Stream API定义了大量的函数式接口来支持各种操作，如：

1. **Predicate**：这是一个用于测试某个条件是否满足的函数式接口。它定义了一个`test`方法，该方法接受一个输入参数并返回一个布尔值。常用于`filter`操作中。
2. **Consumer**：这是一个接受单个输入参数但不返回任何结果的操作，它对输入参数执行指定的操作。常用于`forEach`和`accept`操作中。
3. **Function<T,R>**：这是一个将输入参数T映射到结果R的函数式接口。它定义了一个`apply`方法，该方法接受一个输入参数并返回相应的结果。常用于`map`操作中。
4. **Supplier**：这是一个不接受任何参数但返回一个结果的函数式接口。它定义了一个`get`方法，用于返回结果。常用于需要生成新对象或值的场景中。
5. **UnaryOperator**：这是`Function<T,T>`的一个特殊化接口，表示接受一个参数并返回相同类型结果的函数。
6. **BinaryOperator**：这是一个表示接受两个同类型参数并返回它们组合结果的函数式接口。它定义了一个`apply`方法，该方法接受两个输入参数并返回一个结果。常用于归约操作中，如`reduce`。
7. **Comparator**：这是一个用于比较两个对象的函数式接口。它定义了一个`compare`方法，该方法比较两个对象并返回一个整数来表示它们的顺序。虽然`Comparator`不是专门为Stream API设计的，但它在排序和比较操作中非常有用。
8. **ToIntFunction**、**ToLongFunction**、**ToDoubleFunction**：这些是`Function`接口的特殊化，分别用于返回`int`、`long`和`double`类型的值。
9. **IntFunction**、**LongFunction**、**DoubleFunction**：这些是`Function`接口的特殊化，它们的输入参数是`int`、`long`或`double`类型，并返回一个泛型结果`R`。
10. **ObjIntConsumer**、**ObjLongConsumer**、**ObjDoubleConsumer**：这些接口结合了`Consumer`和数值类型参数（`int`、`long`、`double`），用于对单个对象和一个数值进行操作。
11. **BiConsumer<T,U>**：这是一个接受两个输入参数且没有返回值的函数式接口。虽然它不是Stream API特有的，但在需要对两个参数执行操作的场景中很有用。
12. **BiFunction<T,U,R>**：这是一个将两个输入参数T和U映射到结果R的函数式接口。它定义了一个`apply`方法，该方法接受两个输入参数并返回一个结果。

请注意，这里列出的只是Stream API中常用的一部分函数式接口。Java的`java.util.function`包中定义了许多其他函数式接口，它们可以与Stream API或其他Java 8及以上版本的函数式编程特性一起使用。这些接口提供了丰富的功能，支持各种复杂的操作和数据转换。



#### 4. 流式编程的基本步骤（详细）

1. **创建流**：
   - 通过调用集合的`stream()`方法创建顺序流。
   - 通过调用集合的`parallelStream()`方法创建并行流（如果可用）。
   - 通过数组：使用Arrays.stream(T[] array)方法。
   - 通过Stream的of()方法：直接生成包含指定元素的Stream。
   - 其他：如使用IntStream.range(int startInclusive, int endExclusive)等生成数字序列的Stream。
2. **中间操作**（可选，可多个）：
   - **过滤**：使用`filter(Predicate<? super T> predicate)`方法过滤流中的元素。
   - **映射**：使用`map(Function<? super T,? extends R> mapper)`方法将流中的每个元素映射成另一种形式。
   - **排序**：使用`sorted()`或`sorted(Comparator<? super T> comparator)`方法对流中的元素进行排序。
   - **去重**：使用`distinct()`方法去除流中的重复元素（基于元素的`equals()`和`hashCode()`方法）。
   - **截断**：使用`limit(long maxSize)`方法限制流中元素的数量。
   - **跳过**：使用`skip(long n)`方法跳过流中的前n个元素。
   - **其他**：如`peek(Consumer<? super T> action)`方法，主要用于调试目的，对流中的每个元素执行操作，但不改变流本身。
3. **终端操作**（必须）：
   - **遍历/消费**：使用`forEach(Consumer<? super T> action)`方法遍历流中的每个元素，并对其执行操作。
   - **归约**：使用`reduce(BinaryOperator<T> accumulator)`方法将流中的元素组合起来，得到一个值。
   - **收集**：使用`collect(Collectors.toList())`、`collect(Collectors.toSet())`等方法将流中的元素收集到新的集合中。
   - **匹配**：使用`anyMatch(Predicate<? super T> predicate)`、`allMatch(Predicate<? super T> predicate)`、`noneMatch(Predicate<? super T> predicate)`等方法检查流中的元素是否满足某个条件。
   - **查找**：使用`findFirst()`、`findAny()`等方法查找流中的元素。
   - **最大值/最小值**：使用`max(Comparator<? super T> comparator)`、`min(Comparator<? super T> comparator)`方法找到流中的最大或最小元素。
   - **计数**：使用`count()`方法计算流中的元素数量。