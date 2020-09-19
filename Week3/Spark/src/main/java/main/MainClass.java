package main;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.Function;
import org.apache.spark.api.java.function.Function2;
import org.apache.spark.api.java.function.PairFunction;
import org.apache.spark.util.LongAccumulator;
import scala.Array;
import scala.Tuple2;

import java.util.Arrays;
import java.util.List;

public class MainClass {

    public static void main(String[] args) {
        SparkConf sparkConf = new SparkConf().setAppName("WordCount");
        JavaSparkContext sc = new JavaSparkContext(sparkConf);
        JavaRDD<String> lines = sc.textFile("hdfs://localhost:9000/user/nmq/data/a.txt");
        JavaRDD<String> words = lines.flatMap(new FlatMap());

        JavaPairRDD<String, Integer> ones = words.mapToPair((PairFunction<String, String, Integer>) key -> new Tuple2<>(key, 1));

        JavaPairRDD<String, Integer> counts = ones.reduceByKey(
                (Function2<Integer, Integer, Integer>) Integer::sum
        )
                 ;

        //counts.saveAsTextFile("hdfs://localhost:9000/user/nmq/output/out.txt");
        counts.collect().forEach(data -> System.out.println(data._1 + "____" + data._2));
    }

//    public static void main(String[] args) {
//        SparkConf sparkConf = new SparkConf().setAppName("WordCount");
//        JavaSparkContext sc = new JavaSparkContext(sparkConf);
//        JavaRDD<String> lines = sc.textFile("hdfs://localhost:9000/user/nmq/data/a.txt");
//        LongAccumulator lineCount = sc.sc().longAccumulator();
//        LongAccumulator charCount = sc.sc().longAccumulator();
//        lineCount.add(lines.count());
//        JavaPairRDD<String, Integer> counts = lines.flatMap(s -> Arrays.asList(s.split(" ")).iterator())
//                .mapToPair((PairFunction<String, String, Integer>) key -> new Tuple2<>(key, 1))
//                .reduceByKey((Function2<Integer, Integer, Integer>) Integer::sum);
//        counts.foreach(data -> charCount.add(data._1().length() * data._2));
//
//    }
}
