package gtl.ipc;

import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInput;
import java.io.DataInputStream;

import static org.junit.Assert.*;

public class CommandDescriptorTest {
    @Test
    public void getName() throws Exception {
    }

    @Test
    public void setName() throws Exception {
    }

    @Test
    public void getParameterDescriptors() throws Exception {
    }

    @Test
    public void setParameterDescriptors() throws Exception {
    }

    @Test
    public void addParameterDescriptor() throws Exception {
    }

    @Test
    public void equals() throws Exception {
    }


    @Test
    public void copyFrom() throws Exception {
    }

    @Test
    public void load() throws Exception {
    }

    @Test
    public void store() throws Exception {
    }

    @Test
    public void getByteArraySize() throws Exception {
    }

    @Test
    public void write() throws Exception {
    }

    @Test
    public void readFields() throws Exception {
    }

    @Test
    public void read() throws Exception {

        CommandDescriptor cd = new CommandDescriptor("doNothing");
        byte[] bs = cd.storeToByteArray();


        CommandDescriptor cd2 = new CommandDescriptor("nothing");
        cd2.loadFromByteArray(bs);


        DataInputStream dis = new DataInputStream(new ByteArrayInputStream(bs));
        CommandDescriptor cd3 =  CommandDescriptor.read((DataInput)dis);

    }

}