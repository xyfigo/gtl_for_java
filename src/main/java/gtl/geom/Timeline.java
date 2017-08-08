package gtl.geom;

import gtl.common.Identifier;

import java.util.List;

public interface Timeline extends gtl.io.Serializable{
    Identifier getIdentifier();
    List<Interval> getIntervals();
    List<String> getLabels();
    List<LabeledInterval> getLabeledIntervals();
    LabeledInterval[] getLabelIntervalArray();
    void addLabelInterval(LabeledInterval lb);
    //转化成以逗号分隔的字符串
    String toString();
    //解析以逗号分隔的字符串，并填充Timeline,返回自身
    Timeline parse(String s);
}
