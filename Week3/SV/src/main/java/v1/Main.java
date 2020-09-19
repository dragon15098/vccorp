package v1;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.Function;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Encoders;
import org.apache.spark.sql.SparkSession;
import scala.Tuple2;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        SparkConf sparkConf = new SparkConf().setAppName("WordCount");
        JavaSparkContext sc = new JavaSparkContext(sparkConf);
        JavaRDD<String> lines = sc.textFile("hdfs://localhost:9000/user/nmq/data_sv/");


        JavaRDD<String> type = sc.textFile("file:////home/nmq/Desktop/vccorp/Week3/type.txt");
        JavaRDD<ExamType> examTypeRDD = type.map(s -> {
            String[] split = s.split("\t");
            return new ExamType(Integer.parseInt(split[1]), split[0]);
        });
        List<ExamType> examTypes = examTypeRDD.collect();


//        examTypes.forEach(System.out::println);
//        lines.collect().forEach(System.out::println);
        JavaRDD<StudentPoint> mapStudentPoints = lines.map((Function<String, StudentPoint>) s -> {
            String[] split = s.split("\t");
            ExamType examType = new ExamType(split[3]);
            int index = examTypes.indexOf(examType);
            examType = examTypes.get(index);
            return new StudentPoint(Long.parseLong(split[0]), Float.parseFloat(split[1]), split[2], examType);
        });

        //mapStudentPoints.collect().forEach(System.out::println);
        JavaPairRDD<String, Point> pair =
                mapStudentPoints.mapToPair(studentPoint ->
                        new Tuple2<>(studentPoint.getId() + "_"
                                + studentPoint.getSubject(),
                                new Point(studentPoint.getPoint(), studentPoint.getExamType().getMul())));

        System.out.println("========================================");
        pair.collect().forEach(System.out::println);
        System.out.println("========================================");

        JavaPairRDD<String, Iterable<Point>> group = pair.groupByKey();

        JavaRDD<Tuple2<String, Point>> map = group.map(data -> {
            float sum = 0f;
            int mul = 0;
            for (Point p : data._2) {
                sum += p.value * p.mul;
                mul += p.mul;
            }
            return new Tuple2<>(data._1, new Point(sum, mul));
        });


        System.out.println("========================================");
        group.collect().forEach(System.out::println);
        System.out.println("========================================");

        JavaRDD<StudentPoint> lastPoints = map.map(tuple2 -> {
            int studentId = Integer.parseInt(tuple2._1.split("_")[0]);
            String subject = tuple2._1.split("_")[1];
            return new StudentPoint(studentId, Math.round((tuple2._2.value / tuple2._2.mul) * 100) / 100f, subject);
        });

        System.out.println("========================================");
        lastPoints.collect().forEach(System.out::println);
        System.out.println("========================================");
//
//        JavaRDD<v1.StudentPoint> mapToSave = lastPoints.map(data -> {
//            String[] idSubjectType = data._1.split(" ");
//            return new v1.StudentPoint(Long.parseLong(idSubjectType[0]), data._2.lastPoint, idSubjectType[1]);
//        });

        SparkSession spark = SparkSession
                .builder()
                .appName("Java Spark SQL basic example")
                .getOrCreate();


        Dataset<StudentPoint> df = spark
                .createDataset(lastPoints.rdd(), Encoders.bean(StudentPoint.class));
        df.write().parquet("hdfs://localhost:9000/user/nmq/output_sv/sv.parquet");

        spark.stop();
    }
}
