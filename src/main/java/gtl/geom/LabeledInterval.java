package gtl.geom;

import gtl.common.CommonSuits;
import gtl.common.Identifier;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class LabeledInterval extends IntervalImpl{
    private static final long serialVersionUID = 1L;
    Identifier   pid;//parent ID
    Identifier   order;// order in the parent object
    String       label;//label string

    public LabeledInterval(IntervalType type, double low, double high,String label,long pid,long order) {
        super(type,low,high);
        this.label=label;
        this.pid= CommonSuits.createIdentifier(pid);
        this.order=CommonSuits.createIdentifier(order);
    }

    public LabeledInterval(IntervalType type, double low, double high,String label,Identifier pid,Identifier order) {
        super(type,low,high);
        this.label=label;
        this.pid= CommonSuits.createIdentifier(pid.longValue());
        this.order=CommonSuits.createIdentifier(order.longValue());
    }

    public LabeledInterval(double low, double high, String label,long pid,long order) {
        super(low,high);
        this.label=label;
        this.pid= CommonSuits.createIdentifier(pid);
        this.order=CommonSuits.createIdentifier(order);
    }

    public LabeledInterval(Interval i, String label,long pid,long order) {
        super(i.getType(),i.getLowerBound(),i.getUpperBound());
        this.label=label;
        this.pid= CommonSuits.createIdentifier(pid);
        this.order=CommonSuits.createIdentifier(order);
    }

    public LabeledInterval() {
        super();
        this.label=new String();
        this.pid= CommonSuits.createIdentifier(-1L);
        this.order=CommonSuits.createIdentifier(-1L);
    }

    public String getLabel(){
        return label;
    }

    public Identifier getOrder() {
        return order;
    }

    public void setOrder(Identifier i) {
        order.reset(i.longValue());
    }

    public void setOrder(long i) {
        order.reset(i);
    }

    public Identifier getParentID() {
        return pid;
    }

    public void setParentID(Identifier v) {
        pid.reset(v.longValue());
    }

    public void setParentID(long v) {
        pid.reset(v);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof LabeledInterval)) return false;
        if (!super.equals(o)) return false;

        LabeledInterval that = (LabeledInterval) o;

        if (!pid.equals(that.pid)) return false;
        if (!order.equals(that.order)) return false;
        return getLabel().equals(that.getLabel());
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + pid.hashCode();
        result = 31 * result + order.hashCode();
        result = 31 * result + getLabel().hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "LabeledInterval{" +
                "pid=" + pid +
                ", order=" + order +
                ", label='" + label + '\'' + super.toString()+
                '}';
    }

    @Override
    public Object clone() {
        LabeledInterval li= new LabeledInterval(getType(),getLowerBound(),getUpperBound(),label,pid,order);
        return (Object)li;
    }

    @Override
    public void copyFrom(Object i) {
        if (i instanceof LabeledInterval) {
            reset(((Interval) i).getType(), ((Interval) i).getLowerBound(), ((Interval) i).getUpperBound());
            this.label=((LabeledInterval)i).label;
            this.pid=((LabeledInterval)i).pid;
            this.order=((LabeledInterval)i).order;
        }
        if (i instanceof Interval) {
            reset(((Interval) i).getType(), ((Interval) i).getLowerBound(), ((Interval) i).getUpperBound());
        }
    }

    @Override
    public boolean load(DataInput dis) throws IOException {
        super.load(dis);
        int len = dis.readInt();
        byte [] cc = new byte [len];
        dis.readFully(cc);
        this.label = new String(cc,0,cc.length);
        this.pid.load(dis);
        this.order.load(dis);
        return true;
    }

    @Override
    public boolean store(DataOutput dos) throws IOException {
        super.store(dos);
        byte[] bs = label.getBytes();
        dos.writeInt(bs.length);
        dos.write(bs);
        //dos.writeLong(this.pid.longValue());
        //dos.writeLong(this.order.longValue());
        this.pid.store(dos);
        this.order.store(dos);
        return true;
    }

    @Override
    public long getByteArraySize() {
        return 8 * 2 + 4+label.getBytes().length+4+ pid.getByteArraySize()+order.getByteArraySize();
    }

}
