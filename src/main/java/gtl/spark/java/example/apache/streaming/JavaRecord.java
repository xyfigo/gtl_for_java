package gtl.spark.java.example.apache.streaming;

/**
 * Java Bean class to be used with the example JavaSqlNetworkWordCount.
 */
public class JavaRecord implements java.io.Serializable {
    private String word;

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }
}
