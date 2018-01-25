package gtl.geom;

import gtl.common.CommonSuits;
import gtl.common.Identifier;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

class TimelineImpl implements Timeline {
    private static final long serialVersionUID = 1L;

    Identifier identifier;
    List<LabeledInterval> labeledIntervals;

    private static final Pattern COMMA_SPLITTER = Pattern.compile(",");

    public TimelineImpl(Identifier identifier, List<LabeledInterval> labeledIntervals) {
        this.identifier = identifier;
        this.labeledIntervals = new ArrayList<LabeledInterval>();
        this.labeledIntervals.addAll(labeledIntervals);
        long i=0;
        for(LabeledInterval li:this.labeledIntervals){
            li.setParentID(identifier.longValue());
            li.setOrder(i);
            i++;
        }
    }

    public TimelineImpl( ) {
        this.identifier = CommonSuits.createIdentifier(0);
        this.labeledIntervals = new ArrayList<LabeledInterval>();
    }

    public TimelineImpl(Identifier identifier, List<Interval> li, List<String> ls) {
        this.identifier = identifier;
        this.labeledIntervals = new ArrayList<LabeledInterval>();
        int c = li.size()>ls.size()? ls.size():li.size();
        for(int i=0;i<c;++i)
            this.labeledIntervals.add( new LabeledInterval(li.get(i),ls.get(i),identifier.longValue(),(long)i));
    }

    @Override
    public Identifier getIdentifier() {
        return identifier;
    }

    @Override
    public List<Interval> getIntervals() {
        List<Interval> li =  new ArrayList<Interval>();
        li.addAll(this.labeledIntervals);
        return li;
    }

    @Override
    public List<String> getLabels() {
        List<String> ls =  new ArrayList<String>();
        for(LabeledInterval li : labeledIntervals)
            ls.add(li.getLabel());
        return ls;
    }

    @Override
    public List<LabeledInterval> getLabeledIntervals() {
        return labeledIntervals;
    }

    @Override
    public LabeledInterval[] getLabelIntervalArray(){
        LabeledInterval[] la = new LabeledInterval[labeledIntervals.size()];
        return labeledIntervals.toArray(la);
    }

    @Override
    public void addLabelInterval(LabeledInterval lb){
        lb.setParentID(this.identifier.longValue());
        lb.setOrder(labeledIntervals.size());
        labeledIntervals.add(lb);
    }

    @Override
    public Object clone() {
        TimelineImpl ti =  new TimelineImpl();
        ti.identifier = (Identifier) this.identifier.clone();
        for(LabeledInterval li : labeledIntervals)
            ti.labeledIntervals.add((LabeledInterval) li.clone());
        return ti;
    }

    @Override
    public void copyFrom(Object i) {
        if(i instanceof  Timeline)
        {
            Timeline ti =   (Timeline)(i);
            this.identifier = ti.getIdentifier();
            List<LabeledInterval> olabeledIntervals = ti.getLabeledIntervals();
            this.labeledIntervals.clear();
            for(LabeledInterval li : olabeledIntervals)
                this.labeledIntervals.add((LabeledInterval) li.clone());
        }
    }

    @Override
    public boolean load(DataInput in) throws IOException {
        try{
            this.identifier.load(in);
            int c = in.readInt();
            this.labeledIntervals.clear();
            for(int i=0;i<c;++c){
                LabeledInterval li = new LabeledInterval();
                li.load(in);
                this.labeledIntervals.add(li);
            }
        }
        catch (IOException e){
            e.printStackTrace();
        }

        return true;
    }

    @Override
    public boolean store(DataOutput out) throws IOException {
        try{
            this.identifier.store(out);
            int c = this.labeledIntervals.size();
            out.writeInt(c);
            for(LabeledInterval i: this.labeledIntervals){
                i.store(out);
            }
        }
        catch (IOException e){
            e.printStackTrace();
        }
        return true;
    }

    @Override
    public long getByteArraySize() {
        long len = 0;
        for(LabeledInterval li : labeledIntervals)
            len += li.getByteArraySize();
        len += identifier.getByteArraySize();
        return len;
    }

    //转化成字符串
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(Long.valueOf(getIdentifier().longValue()).toString());
        sb.append(',');
        for(LabeledInterval li : labeledIntervals)
            sb.append(li.label)
                    .append(',')
                    .append(Integer.valueOf(li.getType().ordinal()).toString())
                    .append(',')
                    .append(Double.valueOf(li.getLowerBound()).toString())
                    .append(',')
                    .append(Double.valueOf(li.getUpperBound()).toString())
                    .append(',')
                    .append(Long.valueOf(li.getParentID().longValue()).toString())
                    .append(',')
                    .append(Long.valueOf(li.getOrder().longValue()).toString())
                    .append(',');
        sb.deleteCharAt(sb.length()-1);//删除最后多余的逗号
        return sb.toString();
    }
    //解析字符串，并填充Timeline
    public Timeline parse(String s){
        String[] ss = COMMA_SPLITTER.split(s);
        if(ss.length<5) return this;
        int i=0;
        identifier.reset(Long.valueOf(ss[i]).longValue());
        labeledIntervals.clear();
        ++i;
        while(i<ss.length){
            String label = ss[i];
            ++i;
            int type = Integer.valueOf(ss[i]).intValue();
            ++i;
            double low = Double.valueOf(ss[i]).doubleValue();
            ++i;
            double high=Double.valueOf(ss[i]).doubleValue();
            ++i;
            long pid=Long.valueOf(ss[i]).longValue();
            ++i;
            long order=Long.valueOf(ss[i]).longValue();
            ++i;
            labeledIntervals.add(GeomSuits.createLabeledInterval(IntervalType.values()[type],low,high,label,pid,order));
        }
        return (Timeline)this;
    }
}
