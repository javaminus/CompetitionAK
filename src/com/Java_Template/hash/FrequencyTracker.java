package com.Java_Template.hash;

import java.util.HashMap;

/**
 * 2671. 频率跟踪器(https://leetcode.cn/problems/frequency-tracker/description/)
 *
 * 请你设计并实现一个能够对其中的值进行跟踪的数据结构，并支持对频率相关查询进行应答。
 *
 * 实现 FrequencyTracker 类：
 *
 * FrequencyTracker()：使用一个空数组初始化 FrequencyTracker 对象。
 * void add(int number)：添加一个 number 到数据结构中。
 * void deleteOne(int number)：从数据结构中删除一个 number 。数据结构 可能不包含 number ，在这种情况下不删除任何内容。
 * bool hasFrequency(int frequency): 如果数据结构中存在出现 frequency 次的数字，则返回 true，否则返回 false。
 */
class FrequencyTracker {
    HashMap<Integer,Integer> cnt;
    HashMap<Integer, Integer> freq;
    public FrequencyTracker() {
        cnt = new HashMap<>();
        freq = new HashMap<>();
    }

    private void update(int number, int delta) {
        Integer c = cnt.merge(number, delta, Integer::sum);
        freq.merge(c - delta, -1, Integer::sum);
        freq.merge(c, 1, Integer::sum);
    }
    public void add(int number) {
        update(number, 1);
    }

    public void deleteOne(int number) {
        if (cnt.getOrDefault(number, 0) > 0) {
            update(number, -1);
        }
    }

    public boolean hasFrequency(int frequency) {
        return freq.getOrDefault(frequency, 0) > 0;
    }
}

/**
 * Your FrequencyTracker object will be instantiated and called as such:
 * FrequencyTracker obj = new FrequencyTracker();
 * obj.add(number);
 * obj.deleteOne(number);
 * boolean param_3 = obj.hasFrequency(frequency);
 */