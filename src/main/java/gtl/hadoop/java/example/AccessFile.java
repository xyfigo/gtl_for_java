package gtl.hadoop.java.example;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;

import java.io.IOException;


public class AccessFile {
    public static void main(String [] args){
        Configuration conf = new Configuration();
        try {
            FileSystem hdfs =  FileSystem.get(conf);
            System.out.println("fs.defaultFS=" +
                    conf.get("fs.defaultFS") + "\n");
            Path path = new Path("usr/he/example.dat");

            System.out.println("----------create file-----------");
            String text = "Hello world!\n";
            FSDataOutputStream fsdos = hdfs.create(path);
            fsdos.write(text.getBytes());
            fsdos.close();

            System.out.println("----------read file-----------");
            byte[] line = new byte[text.length()];
            FSDataInputStream fis = hdfs.open(path);
            int len = fis.read(line);
            fis.close();
            System.out.println(len + ":" + new String(line));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
