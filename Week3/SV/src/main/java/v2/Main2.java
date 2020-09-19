package v2;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.Function;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Encoders;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;
import scala.collection.JavaConverters;
import scala.collection.Seq;
import v1.ExamType;

import java.util.ArrayList;
import java.util.List;

public class Main2 {
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

        JavaRDD<StudentPointV2> mapStudentPoints = lines.map((Function<String, StudentPointV2>) s -> {
            String[] split = s.split("\t");
            ExamType examType = new ExamType(split[3]);
            int index = examTypes.indexOf(examType);
            examType = examTypes.get(index);
            return new StudentPointV2(Long.parseLong(split[0]), Float.parseFloat(split[1]), split[2], examType);
        });

        SparkSession spark = SparkSession
                .builder()
                .appName("Java Spark SQL basic example")
                .getOrCreate();

        Dataset<StudentPointV2> df = spark
                .createDataset(mapStudentPoints.rdd(), Encoders.bean(StudentPointV2.class));
        df.show();
        Dataset<Row> sumPoint = df
                .groupBy("id", "subject")
                .sum("lastPoint");
        sumPoint.show();
        Dataset<Row> mul = df
                .groupBy("subject", "id")
                .sum("mul");
        mul.show();

        ArrayList<String> cols = new ArrayList<>();
        cols.add("id");
        cols.add("subject");
        Dataset<Row> dataJoin = sumPoint.join(mul, convertListToSeq(cols));

        dataJoin.show();
        Dataset<Row> rowDataset = dataJoin
                .withColumn("lastPoint", dataJoin.col("sum(lastPoint)").divide(dataJoin.col("sum(mul)")));
        rowDataset.show();

        Dataset<Row> lastData = rowDataset
                .select("id", "subject", "lastPoint");
        lastData.show();
        lastData.write().parquet("hdfs://localhost:9000/user/nmq/output_sv/data_sv.parquet");
//        lastData.show();
//        lastData.select("pointId").where(lastData.col("pointId").equalTo(1)).show();
    }

    public static Seq<String> convertListToSeq(List<String> inputList) {
        return JavaConverters.asScalaIteratorConverter(inputList.iterator()).asScala().toSeq();
    }
}
