package gtl.ipc;

import gtl.common.Variant;
import gtl.io.Serializable;
import org.apache.hadoop.io.Writable;

import java.io.*;

public class ParameterDescriptor<T extends Serializable> extends  DataDescriptor<T>{
    private static final long serialVersionUID = 1L;


    public ParameterDescriptor(T parameter) {
        super(parameter);
    }

    public ParameterDescriptor( ) {
        super();
    }

    public Object getParameter() {
        return getData();
    }

    public void setParameter(T  parameter) {
        setData(parameter);
    }

    public void setParameter(Variant parameter) {
        setData((T)parameter);
    }

    public static ParameterDescriptor read(DataInput in) throws IOException {
        ParameterDescriptor pd = new ParameterDescriptor();
        pd.readFields(in);
        return  pd;
    }

    @Override
    public Object clone() {
        return new ParameterDescriptor((T)(getData().clone()));
    }
}
