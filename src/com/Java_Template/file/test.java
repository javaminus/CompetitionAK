package com.Java_Template.file;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class test {
    public static void main(String[] args) {
        String fileName = "E:\\IdeaProjects\\Algorithm\\src\\lanqiao\\authenticTest\\year_2023\\times.txt"; // 指定文件名
        List<Long> timestampEntries = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                LocalDateTime dateTime = LocalDateTime.parse(line.trim(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
                long timestamp = dateTime.toEpochSecond(java.time.ZoneOffset.UTC);
                timestampEntries.add(timestamp);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // 对时间戳排序
        Collections.sort(timestampEntries);

        long ans = 0;
        for(int i = 1; i < timestampEntries.size(); i += 2){
            long working = timestampEntries.get(i) - timestampEntries.get(i - 1);
            ans += working;
        }
        System.out.println(ans);
        System.out.println(5101913);

    }


}
