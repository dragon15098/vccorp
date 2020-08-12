package bai1;

import java.util.*;

public class Test1 {
    static int TOTAL_SIZE = 200000;
    static Set<Integer> set1 = new HashSet<>();
    static Set<Integer> set2 = new HashSet<>();
    static Random random1 = new Random();
    static Random random2 = new Random();


    public static int randomNumber1() {
        return random1.nextInt(1000000);
    }

    public static int randomNumber2() {
        return random2.nextInt(1000000);
    }

    public static void main(String[] args) {
        setUpData();
        solve();
    }

    public static void setUpData() {
        int matchSize = random1.nextInt(20000);
        setUpMatchNumber(matchSize);
        System.out.println("match size = " + matchSize);
        int notMatchSize = TOTAL_SIZE - matchSize;
        setUpNotMatchNumber(notMatchSize);
    }

    public static void setUpMatchNumber(int matchSize) {
        for (int i = 0; i < matchSize; i++) {
            int randomNumber = randomNumber1();
            while (set1.contains(randomNumber)) {
                randomNumber = randomNumber1();
            }
            set1.add(randomNumber);
            set2.add(randomNumber);
        }
    }

    public static void setUpNotMatchNumber(int notMatchSize) {
        for (int i = 0; i < notMatchSize; i++) {
            int randomNumber1 = randomNumber1();
            int randomNumber2 = randomNumber2();
            while (existInSet1OrSet2(randomNumber1, randomNumber2) || isRandomNumberEqual(randomNumber1, randomNumber2)) {
                randomNumber1 = randomNumber1();
                randomNumber2 = randomNumber2();
            }
            set1.add(randomNumber1);
            set2.add(randomNumber2);
        }
        System.out.println("set1 size: " + set1.size());
        System.out.println("set2 size: " + set2.size());
    }

    private static boolean isRandomNumberEqual(int randomNumber1, int randomNumber2) {
        return randomNumber1 == randomNumber2;
    }

    public static boolean existInSet1OrSet2(int randomNumber1, int randomNumber2) {
        return set1.contains(randomNumber1) || set2.contains(randomNumber2)
                || set1.contains(randomNumber2) || set2.contains(randomNumber1);
    }

    public static void solve() {
        Set<Integer> results = new HashSet<>();
        results.addAll(set1);
        results.addAll(set2);
        System.out.println("Result1 size : " + results.size());

        List<Integer> result2 = new ArrayList<>();
        for (Integer numberSet1 : set1) {
            if (set2.contains(numberSet1)) {
                result2.add(numberSet1);
            }
        }
//        result2.forEach(integer -> System.out.println(integer));
        System.out.println("Result2 size : " + result2.size());
    }

}
