package gtl.ipc;

import gtl.common.Variant;
import gtl.io.Serializable;
import org.apache.hadoop.io.Writable;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class ResultDescriptor<T extends Serializable> extends DataDescriptor<T> {
    private static final long serialVersionUID = 1L;

    public ResultDescriptor (Variant v){
        super((T)v);
    }

    public ResultDescriptor (T v){
        super(v);
    }

    public ResultDescriptor() {
        super();
    }

    public static ResultDescriptor read(DataInput in) throws IOException {
        ResultDescriptor pd = new ResultDescriptor();
        pd.readFields(in);
        return  pd;
    }

    @Override
    public Object clone() {
        return new ResultDescriptor((Serializable)(getData().clone()));
    }

    public Object getResult(){
        return (Object) this.data;
    }

    public void setResult(Object  i){
        this.data = (T)i;
    }
}
