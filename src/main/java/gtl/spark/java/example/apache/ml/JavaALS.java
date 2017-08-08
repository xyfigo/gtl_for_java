package gtl.spark.java.example.apache.ml;

import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.ml.evaluation.RegressionEvaluator;
import org.apache.spark.ml.recommendation.ALS;
import org.apache.spark.ml.recommendation.ALSModel;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;

import java.io.Serializable;

/**
 * ALS交替最小二乘法的协同过滤算法
 */
public class JavaALS {

    public static class Rating implements Serializable {
        private int userId;
        private int movieId;
        private float rating;
        private long timestamp;

        public Rating() {
        }

        public Rating(int userId, int movieId, float rating, long timestamp) {
            this.userId = userId;
            this.movieId = movieId;
            this.rating = rating;
            this.timestamp = timestamp;
        }

        public int getUserId() {
            return userId;
        }

        public int getMovieId() {
            return movieId;
        }

        public float getRating() {
            return rating;
        }

        public long getTimestamp() {
            return timestamp;
        }

        public static Rating parseRating(String str) {
            String[] fields = str.split("::");
            if (fields.length != 4) {
                throw new IllegalArgumentException("Each line must contain 4 fields");
            }
            int userId = Integer.parseInt(fields[0]);
            int movieId = Integer.parseInt(fields[1]);
            float rating = Float.parseFloat(fields[2]);
            long timestamp = Long.parseLong(fields[3]);
            return new Rating(userId, movieId, rating, timestamp);
        }
    }

    public static void main(String[] args) {
        SparkSession spark = SparkSession
                .builder()
                .master("local")
                .appName("JavaALSExample")
                .getOrCreate();

        JavaRDD<Rating> ratingsRDD = spark
                .read().textFile("D:\\devs\\3rdparties\\spark\\spark-2.2.1\\data\\mllib\\als\\sample_movielens_ratings.txt").javaRDD()
                .map(Rating::parseRating);
        Dataset<Row> ratings = spark.createDataFrame(ratingsRDD, Rating.class);  //StructType  直接就是用类名.class替代
        Dataset<Row>[] splits = ratings.randomSplit(new double[]{0.8, 0.2});
        Dataset<Row> training = splits[0];
        Dataset<Row> test = splits[1];

        // Build the recommendation model using ALS on the training data
        ALS als = new ALS()
                .setMaxIter(5)
                .setRegParam(0.01)
                .setUserCol("userId")
                .setItemCol("movieId")
                .setRatingCol("rating");
        ALSModel model = als.fit(training);

        // Evaluate the model by computing the RMSE on the test data
        // Note we set cold start strategy to 'drop' to ensure we don't get NaN evaluation metrics
        model.setColdStartStrategy("drop");
        Dataset<Row> predictions = model.transform(test);

        RegressionEvaluator evaluator = new RegressionEvaluator()
                .setMetricName("rmse")
                .setLabelCol("rating")
                .setPredictionCol("prediction");
        Double rmse = evaluator.evaluate(predictions);
        System.out.println("Root-mean-square error = " + rmse);

        // Generate top 10 movie recommendations for each user
        Dataset<Row> userRecs = model.recommendForAllUsers(10);
        // Generate top 10 user recommendations for each movie
        Dataset<Row> movieRecs = model.recommendForAllItems(10);
        userRecs.show();
        movieRecs.show();

        spark.stop();
    }
}

/**
 * +-------+--------------------+
 * |movieId|     recommendations|
 * +-------+--------------------+
 * |     31|[[12,3.9911263], ...|
 * |     85|[[8,4.7645507], [...|
 * |     65|[[14,2.00695], [5...|
 * |     53|[[22,5.4863014], ...|
 * |     78|[[18,1.3879201], ...|
 * |     34|[[2,4.163689], [3...|
 * |     81|[[28,5.327964], [...|
 * |     28|[[18,4.776966], [...|
 * |     76|[[14,5.0026283], ...|
 * |     26|[[12,3.636134], [...|
 * |     27|[[2,6.1352353], [...|
 * |     44|[[18,3.7078097], ...|
 * |     12|[[28,4.506456], [...|
 * |     91|[[2,3.3678875], [...|
 * |     22|[[26,4.870357], [...|
 * |     93|[[24,5.8956866], ...|
 * |     47|[[25,3.8081481], ...|
 * |      1|[[12,4.1235514], ...|
 * |     52|[[8,5.2009945], [...|
 * |     13|[[23,3.921773], [...|
 * +-------+--------------------+
 * only showing top 20 rows
 */
