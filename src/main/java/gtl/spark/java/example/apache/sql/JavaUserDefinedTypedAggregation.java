package gtl.spark.java.example.apache.sql;

import org.apache.spark.sql.*;
import org.apache.spark.sql.expressions.Aggregator;

import java.io.Serializable;


/**
 * 定义好相对应的数据结构 然后再是去读取数据
 * */


public class JavaUserDefinedTypedAggregation {

    public static void main(String[] args) {
        SparkSession spark = SparkSession
                .builder()
                .appName("Java Spark SQL user-defined Datasets aggregation example")
                .master("local[*]")
                .getOrCreate();

        Encoder<Employee> employeeEncoder = Encoders.bean(Employee.class);
        String path = "D:\\devs\\3rdparties\\spark\\spark-2.2.1\\examples\\src\\main\\resources\\employees.json";
        Dataset<Employee> ds = spark.read().json(path).as(employeeEncoder);
        ds.show();
        // +-------+------+
        // |   name|salary|
        // +-------+------+
        // |Michael|  3000|
        // |   Andy|  4500|
        // | Justin|  3500|
        // |  Berta|  4000|
        // +-------+------+

        MyAverage myAverage = new MyAverage();
        // Convert the function to a `TypedColumn` and give it a name
        TypedColumn<Employee, Double> averageSalary = myAverage.toColumn().name("average_salary");
        Dataset<Double> result = ds.select(averageSalary);
        result.show();
        // +--------------+
        // |average_salary|
        // +--------------+
        // |        3750.0|
        // +--------------+
        spark.stop();
    }

    public static class Employee implements Serializable {
        private String name;
        private long salary;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public long getSalary() {
            return salary;
        }

        public void setSalary(long salary) {
            this.salary = salary;
        }

    }

    /**
     * 必须实现序列化 否则无法进行计算
     * 其次是设置成静态类
     * */
    public static class Average implements Serializable {
        private long sum;
        private long count;

        public Average() {
        }

        public Average(long sum, long count) {
            this.sum = sum;
            this.count = count;
        }

        public long getSum() {
            return sum;
        }

        public void setSum(long sum) {
            this.sum = sum;
        }

        public long getCount() {
            return count;
        }

        public void setCount(long count) {
            this.count = count;
        }
    }

    /**
     * 真实的计算过程
     * -IN, BUF, OUT
     * 可以对应下面的bufferEncoder和outputEncoder
     *
     *也就是输入的是一个类及其数据 但是输出的就是其自己最终想要的结果
     * */
    public static class MyAverage extends Aggregator<Employee, Average, Double> {
        // A zero value for this aggregation. Should satisfy the property that any b + zero = b
        public Average zero() {
            return new Average(0L, 0L);
        }

        // Combine two values to produce a new value. For performance, the function may modify `buffer`
        // and return it instead of constructing a new object
        public Average reduce(Average buffer, Employee employee) {
            long newSum = buffer.getSum() + employee.getSalary();
            long newCount = buffer.getCount() + 1;
            buffer.setSum(newSum);
            buffer.setCount(newCount);
            return buffer;
        }

        // Merge two intermediate values
        public Average merge(Average b1, Average b2) {
            long mergedSum = b1.getSum() + b2.getSum();
            long mergedCount = b1.getCount() + b2.getCount();
            b1.setSum(mergedSum);
            b1.setCount(mergedCount);
            return b1;
        }


        /**
         *上面的是定义的一些基本的函数操作  下面这些就是最终的一些转换过程
         * */


        // Transform the output of the reduction
        public Double finish(Average reduction) {
            return ((double) reduction.getSum()) / reduction.getCount();
        }

        // Specifies the Encoder for the intermediate value type
        public Encoder<Average> bufferEncoder() {
            return Encoders.bean(Average.class);
        }

        // Specifies the Encoder for the final output value type
        public Encoder<Double> outputEncoder() {
            return Encoders.DOUBLE();
        }
    }

}
