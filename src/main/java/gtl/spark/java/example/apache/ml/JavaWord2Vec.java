package gtl.spark.java.example.apache.ml;

import org.apache.spark.ml.feature.Word2Vec;
import org.apache.spark.ml.feature.Word2VecModel;
import org.apache.spark.ml.linalg.Vector;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.RowFactory;
import org.apache.spark.sql.SparkSession;
import org.apache.spark.sql.types.*;

import java.util.Arrays;
import java.util.List;


/**
 * RowFactory
 * A factory class used to construct Row objects.
 * <p>
 * create()
 * Create a Row from the given arguments. Position i in the argument list becomes position i in the created Row object.
 * <p>
 * createDataFrame()
 * Creates a `DataFrame` from a `java.util.List` containing [[Row]]s using the given schema.
 * It is important to make sure that the structure of every [[Row]] of the provided List matches
 * the provided schema. Otherwise, there will be runtime exception.
 * <p>
 * transform(String word)
 * Transforms a word to its vector representation
 */

public class JavaWord2Vec {
    public static void main(String[] args) {
        SparkSession spark = SparkSession
                .builder()
                .appName("JavaWord2Vec")
                .master("local[*]")
                .getOrCreate();
        // Input data: Each row is a bag of words from a sentence or document.
        List<Row> data = Arrays.asList(
                RowFactory.create(Arrays.asList("Hi I heard about Spark".split(" "))),
                RowFactory.create(Arrays.asList("I wish Java could use case classes".split(" "))),
                RowFactory.create(Arrays.asList("Logistic regression models are neat".split(" ")))
        );

        StructType schema = new StructType(new StructField[]{
                new StructField("text", new ArrayType(DataTypes.StringType, true), false, Metadata.empty())
        });


        Dataset<Row> documentDF = spark.createDataFrame(data, schema);

        // Learn a mapping from words to Vectors.
        Word2Vec word2Vec = new Word2Vec()
                .setInputCol("text")
                .setOutputCol("result")
                .setVectorSize(3)
                .setMinCount(0);

        Word2VecModel model = word2Vec.fit(documentDF);
        Dataset<Row> result = model.transform(documentDF);

        for (Row row : result.collectAsList()) {
            List<String> text = row.getList(0);
            Vector vector = (Vector) row.get(1);
            System.out.println("Text: " + text + " => \nVector: " + vector + "\n");
        }

        spark.stop();
    }
}


/**
 * Text: [Hi, I, heard, about, Spark] =>
 * Vector: [-0.028139343485236168,0.04554025698453188,-0.013317196490243079]
 * <p>
 * Text: [I, wish, Java, could, use, case, classes] =>
 * Vector: [0.06872416580361979,-0.02604914902310286,0.02165239889706884]
 * <p>
 * Text: [Logistic, regression, models, are, neat] =>
 * Vector: [0.023467857390642166,0.027799883112311366,0.0331136979162693]
 */