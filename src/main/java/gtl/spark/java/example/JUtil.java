package gtl.spark.java.example;

import java.io.*;
import java.util.Arrays;

/**
 * http://zhangbaoming815.iteye.com/blog/1578438
 *
 * Bayes数据整理写Utils
 */
class Jutil {
    public static void main(String args[]) {
        try { // 防止文件建立或读取失败，用catch捕捉错误并打印，也可以throw
            /* 读入TXT文件 */
            String pathname = "D:\\devs\\data\\spark\\all.txt"; // 绝对路径或相对路径都可以，这里是绝对路径，写入文件时演示相对路径
            File filename = new File(pathname); // 要读取以上路径的input.txt文件
            InputStreamReader reader = new InputStreamReader(
                    new FileInputStream(filename)); // 建立一个输入流对象reader
            BufferedReader br = new BufferedReader(reader); // 建立一个对象，它把文件内容转成计算机能读懂的语言
            String result = "";
            String line = "";
            int i = 0;
            while ((line = br.readLine()) != null) {  // 一次读入一行数据 且不为空
                if (i < 50) {
                    line = "1," + String.join(" ",line.split(" "));
                    result = result + line + "\r\n";
                    i++;
                } else if (i < 100) {
                    line = "2," + String.join(" ",line.split(" "));
                    result = result + line + "\r\n";
                    i++;
                } else if (i < 150) {
                    line = "3," + String.join(" ",line.split(" "));
                    result = result + line + "\r\n";
                    i++;
                }
            }
            /* 写入Txt文件 */
            File writename = new File("D:\\devs\\data\\spark\\output3.txt"); // 相对路径，如果没有则要建立一个新的output。txt文件
            writename.createNewFile(); // 创建新文件
            FileOutputStream fos = new FileOutputStream(writename);
            fos.write(result.getBytes());
            fos.flush(); // 把缓存区内容压入文件
            fos.close(); // 最后记得关闭文件
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}