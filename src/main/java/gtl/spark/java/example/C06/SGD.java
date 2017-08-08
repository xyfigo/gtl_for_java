package gtl.spark.java.example.C06;

import java.util.HashMap;
import java.util.Map;

public class SGD {
    //初始化参数
    static public double contemp1 = 0, contemp2 = 0.2;

    //迭代公式
    public static double sgc(double x, double y) {
        contemp1 = contemp1 - contemp2 * ((contemp1 * x) - y);
        return contemp1;
    }

    //主函数
    public static void main(String[] args) {
        Map<Integer, Integer> map = new HashMap<Integer, Integer>();
        for (int i = 0; i <= 500; i++) {
            map.put(i, i * 12);
        }
        for (Map.Entry<Integer, Integer> entry : map.entrySet()){
            sgc(entry.getKey(),entry.getValue());  //http://blog.csdn.net/tjcyjd/article/details/11111401
        }

        System.out.println(contemp1);
    }
}

