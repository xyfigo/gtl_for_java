import org.apache.spark.mllib.feature.Word2Vec
import org.apache.spark.{SparkConf, SparkContext}


/**
  * http://blog.csdn.net/zhangchen2449/article/details/52795529
  *
  * 将语言文本翻译成机器能够认识的语言，词向量工具就是为了解决这个翻译问题而诞生的
  *
  * MLlib中提供了词向量化的工具，其目的是在与不增加维数的前提下降大量的文本内容数字化
  *
  * 计算机在处理海量的文本信息的时候，一个重要的处理方法就是将文本信息向量化表示，即将每个文本包含的词语进行向量化存储
  * MLlib中为了能够处理海量的文本，采用的一种低维向量的方法来表示词组。这样做的最大好处就是在对于选定的词组在向量空间中能够更加紧密地靠近，从而对文本特征提取和
  * 转换提供好处。
  * 目前MLlib中词向量转换采用的是skip-gram模型来实现的，这个也是神经网络学习方法的一个特定学习方式
  *
  * */
object word2Vec {
  def main(args: Array[String]) {
    val conf = new SparkConf() //创建环境变量
      .setMaster("local") //设置本地化处理
      .setAppName("word2Vec") //设定名称
    val sc = new SparkContext(conf) //创建环境变量实例
    val data = sc.textFile("D:\\devs\\data\\spark\\D12\\word.txt").map(_.split(" ").toSeq) //读取数据文件
    val word2vec = new Word2Vec() //创建词向量实例
    val model = word2vec.fit(data) //训练模型
    println(model.getVectors) //打印向量模型
    val synonyms = model.findSynonyms("spark", 5) //寻找spar的相似词   //两个参数 分别为查找目标和查找数量，可以在其中设置查找目标
    for (synonym <- synonyms) { //打印找到的内容
      println(synonym)
    }
  }
}


