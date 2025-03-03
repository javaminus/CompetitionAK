package com.Java_Template.util.testUtil;

import org.junit.jupiter.api.Assertions;

import java.io.*;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.*;

/**
 * Java 版的对拍测试工具类
 *
 * 说明：
 * 1. 本工具类提供了类似 Go 版本的断言、对拍、无限对拍和无限验证等功能。
 * 2. IOFunc 为输入输出处理接口，其 run 方法接收 Reader 与 Writer，允许模拟文件/控制台的 I/O。
 * 3. 使用 JUnit 的 Assertions 来进行断言和报告错误。
 * 4. 部分全局配置可由静态变量设置：
 *      - DEBUG_TLE: 超时时间（毫秒），0 表示不检测超时
 *      - ONCE: 是否只执行一次（无限对拍模式下）
 *      - DISABLE_LOG_INPUT: 当为 true 时，不输出测试输入日志
 */
public class TestUtil {

    // 设置超时时间（毫秒）。若为 0 则表示不做 TLE 检查。
    public static long DEBUG_TLE = 2000;
    // 无限对拍模式下是否只执行一次；默认 false 表示一直执行
    public static boolean ONCE = false;
    // 是否禁用显示测试输入日志；默认 false 表示显示
    public static boolean DISABLE_LOG_INPUT = false;

    /**
     * 输入输出处理接口。
     */
    @FunctionalInterface
    public interface IOFunc {
        void run(Reader in, Writer out) throws Exception;
    }

    /**
     * 校验输出结果的回调接口，用于无限验证模式。
     */
    public interface OutputChecker {
        boolean check(String output);
    }

    /**
     * 一个用于存储两个值的简单工具类。
     */
    public static class Pair<F, S> {
        public F first;
        public S second;
        public Pair(F first, S second) {
            this.first = first;
            this.second = second;
        }
    }

    /**
     * 去掉字符串中多余的空白字符：包括首尾空白，及中间连续空白替换为单个空格。
     */
    public static String removeExtraSpace(String s) {
        return s.trim().replaceAll("\\s+", " ");
    }

    /**
     * 判断指定的 Runnable 是否会超时执行。
     *
     * @param f 需要运行的任务
     * @return 超时返回 true，否则返回 false
     */
    public static boolean isTLE(Runnable f) {
        if (DEBUG_TLE == 0) {
            f.run();
            return false;
        }
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Future<?> future = executor.submit(f);
        try {
            future.get(DEBUG_TLE, TimeUnit.MILLISECONDS);
            executor.shutdownNow();
            return false;
        } catch (TimeoutException e) {
            executor.shutdownNow();
            return true;
        } catch (Exception e) {
            executor.shutdownNow();
            return false;
        }
    }

    /**
     * 使用前缀校验字符串测试用例。
     *
     * @param testCases 测试用例数组，每个测试用例为长度为2的字符串数组，依次为输入和期望输出
     * @param targetCaseNum 指定测试用例编号；0 表示全部，负值表示从后往前数（例如 -1 表示最后一个）
     * @param runFunc 待测试的 I/O 处理函数
     * @param prefix 输出测试用例时的前缀
     */
    public static void AssertEqualStringCaseWithPrefix(String[][] testCases, int targetCaseNum, IOFunc runFunc, String prefix) {
        if (testCases.length == 0) {
            Assertions.fail("empty testcase");
            return;
        }
        // 例如，-1 表示最后一个测试用例
        if (targetCaseNum < 0) {
            targetCaseNum += testCases.length + 1;
        }
        boolean allPassed = true;
        for (int i = 0; i < testCases.length; i++) {
            // 如果 targetCaseNum > 0，表示只测试单个用例
            if (targetCaseNum > 0 && (i + 1) != targetCaseNum) {
                continue;
            }
            String input = removeExtraSpace(testCases[i][0]);
            final String inputInfo;
            final int maxInputSize = 150;
            if (input.length() > maxInputSize) {
                inputInfo = input.substring(0, maxInputSize) + "...";
            } else {
                inputInfo = input;
            }
            String expectedOutput = removeExtraSpace(testCases[i][1]);

            StringReader mockReader = new StringReader(input);
            StringWriter mockWriter = new StringWriter();
            Runnable task = () -> {
                try {
                    runFunc.run(mockReader, mockWriter);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            };

            if (targetCaseNum == 0 && isTLE(task)) {
                allPassed = false;
                Assertions.fail("Time Limit Exceeded " + (i + 1) + "\nInput:\n" + inputInfo);
                return;
            } else if (targetCaseNum != 0) {
                try {
                    task.run();
                } catch (Exception e) {
                    Assertions.fail("Exception in test case " + (i + 1) + "\nInput:\n" + inputInfo + "\nException: " + e.getMessage());
                }
            }

            String actualOutput = removeExtraSpace(mockWriter.toString());
            try {
                Assertions.assertEquals(expectedOutput, actualOutput, "Wrong Answer " + (i + 1) + "\nInput:\n" + inputInfo);
            } catch (AssertionError e) {
                allPassed = false;
                handleOutput(actualOutput);
            }
        }
        if (!allPassed) {
            return;
        }
        // 若测试的是单个用例，则接着测试所有用例
        if (targetCaseNum > 0) {
            System.out.println("Case " + targetCaseNum + " is passed");
            AssertEqualStringCase(testCases, 0, runFunc);
            return;
        }
        System.out.println("OK");
    }

    /**
     * 处理输出结果（可根据需要扩展）。
     */
    public static void handleOutput(String output) {
        // 此处仅简单打印输出，可扩展为记录日志等
        System.out.println("Output: " + output);
    }

    /**
     * 校验字符串测试用例，不带前缀。
     */
    public static void AssertEqualStringCase(String[][] testCases, int targetCaseNum, IOFunc runFunc) {
        AssertEqualStringCaseWithPrefix(testCases, targetCaseNum, runFunc, "");
    }

    /**
     * 从指定目录中按文件名模糊匹配输入/答案文件进行测试。
     *
     * @param dir 目录路径
     * @param inName 输入文件模糊匹配模式（正则形式，如 "in.*\\.txt"）
     * @param ansName 答案文件模糊匹配模式（正则形式，如 "ans.*\\.txt"）
     * @param targetCaseNum 指定测试用例编号
     * @param runFunc 待测的 I/O 处理函数
     */
    public static void AssertEqualFileCaseWithName(String dir, String inName, String ansName, int targetCaseNum, IOFunc runFunc) {
        try {
            File d = new File(dir);
            File[] inputFiles = d.listFiles((f, name) -> name.matches(inName));
            File[] answerFiles = d.listFiles((f, name) -> name.matches(ansName));
            if (inputFiles == null || answerFiles == null) {
                Assertions.fail("Error reading files in " + dir);
            }
            if (inputFiles.length != answerFiles.length) {
                Assertions.fail("missing sample files");
            }
            if (inputFiles.length == 0) {
                System.out.println("[WARN] empty test file");
                return;
            }

            String[][] testCases = new String[inputFiles.length][2];
            Arrays.sort(inputFiles, Comparator.comparing(File::getName));
            Arrays.sort(answerFiles, Comparator.comparing(File::getName));
            for (int i = 0; i < inputFiles.length; i++) {
                testCases[i][0] = new String(Files.readAllBytes(inputFiles[i].toPath()));
            }
            for (int i = 0; i < answerFiles.length; i++) {
                testCases[i][1] = new String(Files.readAllBytes(answerFiles[i].toPath()));
            }

            AssertEqualStringCase(testCases, targetCaseNum, runFunc);
        } catch (Exception e) {
            Assertions.fail(e.getMessage());
        }
    }

    /**
     * 按目录中 "in*.txt" 和 "ans*.txt" 文件进行测试。
     */
    public static void AssertEqualFileCase(String dir, int targetCaseNum, IOFunc runFunc) {
        AssertEqualFileCaseWithName(dir, "in.*\\.txt", "ans.*\\.txt", targetCaseNum, runFunc);
    }

    /**
     * 根据原始文本分割生成测试用例进行测试。
     *
     * 原始文本中需要包含分隔符，例如 "input" 与 "output"（或其变体）。
     */
    public static void AssertEqualCase(String rawText, int targetCaseNum, IOFunc runFunc) {
        rawText = rawText.trim();
        if (rawText.isEmpty()) {
            Assertions.fail("rawText is empty");
        }
        String sepInput = "inputCopy";
        if (!rawText.contains(sepInput)) {
            sepInput = "input";
        }
        String sepOutput = "outputCopy";
        if (!rawText.contains(sepOutput)) {
            sepOutput = "output";
        }

        List<String[]> testCaseList = new ArrayList<>();
        String[] examples = rawText.split(sepInput);
        for (String s : examples) {
            s = s.trim();
            if (!s.isEmpty()) {
                String[] splits = s.split(sepOutput, 2);
                if (splits.length >= 2) {
                    testCaseList.add(new String[] { splits[0], splits[1] });
                }
            }
        }
        String[][] testCases = testCaseList.toArray(new String[testCaseList.size()][]);
        AssertEqualStringCase(testCases, targetCaseNum, runFunc);
    }

    /**
     * 直接根据原始文本测试。
     */
    public static void AssertEqual(String rawText, IOFunc runFunc) {
        AssertEqualCase(rawText, 0, runFunc);
    }

    /**
     * 对拍测试：
     * 分别使用 runFuncAC（暴力/已 AC 逻辑）和 runFunc（待测逻辑）运行同一输入，并比较输出是否一致。
     *
     * @param inputs 测试输入列表
     * @param targetCaseNum 指定测试用例编号（0 表示全部）
     * @param runFuncAC 暴力/AC 解法
     * @param runFunc 待测解法
     */
    public static void AssertEqualRunResults(List<String> inputs, int targetCaseNum, IOFunc runFuncAC, IOFunc runFunc) {
        if (inputs.isEmpty()) {
            return;
        }
        for (int i = 0; i < inputs.size(); i++) {
            if (targetCaseNum > 0 && (i + 1) != targetCaseNum)
                continue;
            String input = removeExtraSpace(inputs.get(i));
            final String inputInfo;
            final int maxInputSize = 150;
            if (input.length() > maxInputSize) {
                inputInfo = input.substring(0, maxInputSize) + "...";
            } else {
                inputInfo = input;
            }

            // 运行 AC 解法
            StringReader mockReaderAC = new StringReader(input);
            StringWriter mockWriterAC = new StringWriter();
            try {
                runFuncAC.run(mockReaderAC, mockWriterAC);
            } catch (Exception e) {
                Assertions.fail("Exception in runFuncAC: " + e.getMessage());
            }
            String expectedOutput = removeExtraSpace(mockWriterAC.toString());

            // 运行待测解法
            StringReader mockReader = new StringReader(input);
            StringWriter mockWriter = new StringWriter();
            Runnable task = () -> {
                try {
                    runFunc.run(mockReader, mockWriter);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            };
            if (targetCaseNum == 0 && isTLE(task)) {
                Assertions.fail("Time Limit Exceeded " + (i + 1) + "\nInput:\n" + inputInfo);
                continue;
            } else if (targetCaseNum != 0) {
                try {
                    task.run();
                } catch (Exception ex) {
                    Assertions.fail("Exception in candidate solution: " + ex.getMessage());
                }
            }
            String actualOutput = removeExtraSpace(mockWriter.toString());
            Assertions.assertEquals(expectedOutput, actualOutput, "Wrong Answer " + (i + 1) + "\nInput:\n" + inputInfo);
        }
    }

    /**
     * 无限对拍模式：不断生成随机输入并比较 runFuncAC 与 runFunc 的输出。
     *
     * @param inputGenerator 随机输入数据生成器，返回输入字符串
     * @param runFuncAC AC 或暴力解法
     * @param runFunc 待测解法
     */
    public static void AssertEqualRunResultsInf(Callable<String> inputGenerator, IOFunc runFuncAC, IOFunc runFunc) {
        int tc = 1;
        while (true) {
            String input;
            try {
                input = removeExtraSpace(inputGenerator.call());
            } catch (Exception e) {
                break;
            }
            StringReader mockReaderAC = new StringReader(input);
            StringWriter mockWriterAC = new StringWriter();
            try {
                runFuncAC.run(mockReaderAC, mockWriterAC);
            } catch (Exception e) {
                Assertions.fail("Exception in runFuncAC: " + e.getMessage());
            }
            String expectedOutput = removeExtraSpace(mockWriterAC.toString());
            StringReader mockReader = new StringReader(input);
            StringWriter mockWriter = new StringWriter();
            if (isTLE(() -> {
                try {
                    runFunc.run(mockReader, mockWriter);
                } catch (Exception ex) {
                    throw new RuntimeException(ex);
                }
            })) {
                Assertions.fail("Time Limit Exceeded " + tc + "\nInput:\n" + input);
                continue;
            }
            String actualOutput = removeExtraSpace(mockWriter.toString());
            if (DISABLE_LOG_INPUT) {
                Assertions.assertEquals(expectedOutput, actualOutput, "Wrong Answer " + tc);
            } else {
                Assertions.assertEquals(expectedOutput, actualOutput, "Wrong Answer " + tc + "\nInput:\n" + input + "\nOutput:\n" + actualOutput);
            }
            if ((tc & (tc - 1)) == 0) {
                System.out.println(tc + " cases checked.");
            }
            tc++;
            if (ONCE) break;
        }
    }

    /**
     * 无限验证模式：
     * 输入生成器不仅返回测试数据，还返回验证函数，用以根据输出验证正确性。
     *
     * @param inputGenerator 返回 Pair，其中 first 为输入字符串，second 为验证输出的回调函数
     * @param targetCaseNum 指定测试用例编号（0 表示全部）
     * @param runFunc 待测解法
     */
    public static void CheckRunResultsInfWithTarget(Callable<Pair<String, OutputChecker>> inputGenerator, int targetCaseNum, IOFunc runFunc) {
        int tc = 1;
        try {
            while (true) {
                Pair<String, OutputChecker> pair = inputGenerator.call();
                if (targetCaseNum > 0 && tc != targetCaseNum) {
                    tc++;
                    continue;
                }
                String input = removeExtraSpace(pair.first);
                OutputChecker checker = pair.second;
                StringReader mockReader = new StringReader(input);
                StringWriter mockWriter = new StringWriter();
                if (isTLE(() -> {
                    try {
                        runFunc.run(mockReader, mockWriter);
                    } catch (Exception ex) {
                        throw new RuntimeException(ex);
                    }
                })) {
                    Assertions.fail("Time Limit Exceeded " + tc + "\nInput:\n" + input);
                    continue;
                }
                String actualOutput = removeExtraSpace(mockWriter.toString());
                boolean ok = checker.check(actualOutput);
                if (DISABLE_LOG_INPUT) {
                    Assertions.assertTrue(ok, "Wrong Answer " + tc);
                } else {
                    Assertions.assertTrue(ok, "Wrong Answer " + tc + "\nInput:\n" + input + "\nOutput:\n" + actualOutput);
                }
                if (targetCaseNum > 0) {
                    if (ok) {
                        CheckRunResultsInfWithTarget(inputGenerator, 0, runFunc);
                    }
                    return;
                }
                if ((tc & (tc - 1)) == 0) {
                    System.out.println(tc + " cases checked.");
                }
                tc++;
                if (ONCE) break;
            }
        } catch (Exception e) {
            Assertions.fail("Runtime Error " + tc + "\nInput:\n" + e.getMessage());
        }
    }

    /**
     * 无限验证模式：使用输入生成器对 runFunc 进行验证。
     */
    public static void CheckRunResultsInf(Callable<Pair<String, OutputChecker>> inputGenerator, IOFunc runFunc) {
        CheckRunResultsInfWithTarget(inputGenerator, 0, runFunc);
    }
}