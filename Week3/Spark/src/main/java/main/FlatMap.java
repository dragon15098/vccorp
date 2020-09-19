package main;

import org.apache.spark.api.java.function.FlatMapFunction;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class FlatMap implements FlatMapFunction<String, String> {
    public Iterator<String> call(String s) {
        List<String> result = new ArrayList<>();
        for (String s1 : s.split(" ")) {
            if (!s1.trim().equals("")) {
                result.add(s1);
            }
        }
        return result.iterator();
    }
}
