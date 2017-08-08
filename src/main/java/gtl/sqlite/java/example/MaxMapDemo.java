package gtl.sqlite.java.example;

import java.util.*;


/**
 * 从map中取出最大或最小value值对应的key值
 * */
public class MaxMapDemo {

    public static void main(String[] args) {
        Map<String, Double> map = new HashMap<>();
        map.put("3", 3.0);
        map.put("2", 2.0);
        map.put("1", 1.0);
        map.put("4", 4.0);
        map.put("7", 7.0);
        map.put("6", 6.0);
        map.put("5", 5.0);
        System.out.println(getMinKey(map));


        Map<String, Double> map2 = new HashMap<>();
        map2.put("3", 3.1);
        map2.put("2", 3.2);
        map2.put("1", 3.3);
        map2.put("4", 3.4);
        map2.put("7", 3.7);
        map2.put("6", 3.6);
        map2.put("5", 3.5);
        System.out.println(getMinKey(map2));
    }

    /**
     * 求Map<K,V>中Key(键)的最小值对应的key
     *
     * @param map
     * @return
     */
    public static String getMinKey(Map<String, Double> map) {
        if (map == null) return "";
        List<Map.Entry<String, Double>> list = new ArrayList<Map.Entry<String, Double>>(map.entrySet());
        Collections.sort(list, (Comparator<Map.Entry<String, Double>>) (o1, o2) -> {
            //升序排列
            if ((o1.getValue() - o2.getValue()) > 0) {
                return 1;
            } else if ((o1.getValue() - o2.getValue()) < 0) {
                return -1;
            } else {
                return 0;
            }
        });
        return list.get(0).getKey();
    }
}