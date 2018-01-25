package gtl.index.itree;

import gtl.common.Variant;
import gtl.ipc.*;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.ipc.RPC;
import org.junit.Test;

import static org.junit.Assert.*;

public class MasterTest {
    @Test
    public void showUsage() throws Exception {
    }

    @Test
    public void executeCommand() throws Exception {
        String ipAddr = "127.0.0.1";
        int port = 8888;
        String dataFile = "hdfs://127.0.0.1:9000/data/test.timeline";

        Master master = new Master(ipAddr,port);
        RPC.Builder builder = new RPC.Builder(new Configuration());
        MasterProtocol mp = MasterProxy.startServer(builder,master);
        master.getMasterDescriptor().setMaster(mp);
         do{
             MasterDescriptor md  = mp.getMasterDescriptor();
             for(SlaveDescriptor sd: md.getSlaves()){
             //execute commands
             //1. test executeCommand(String)
             SlaveProtocol sp = sd.getSlave();
             String rd1 = sp.executeCommand("doNothing");
             System.out.println(rd1);
             //2. test executeCommand(byte[])
             byte[] s = new byte[2];
             s[0]=1;
             s[1]=1;
             byte [] rd2 = sp.executeCommand(s);
             System.out.println(rd2[0]+rd2[1]);
             //3. test executeCommand(ParameterDescriptor)
             ResultDescriptor rd3 = sp.executeCommand(new ParameterDescriptor(new Variant(1)));
             System.out.println(rd3.getResult().toString());
             //4. test executeCommand(CommandDescriptor)
             ResultDescriptor rd = sp.executeCommand(new CommandDescriptor("Command"));
             System.out.println(rd.getResult().toString());
             }
         }while (true);

    }

    @Test
    public void createDTITree() throws Exception {
    }

    @Test
    public void getRootTriangle() throws Exception {
    }

    @Test
    public void executePartition() throws Exception {
    }

    @Test
    public void executeSimilarityQuery() throws Exception {
    }

    @Test
    public void createTITrees() throws Exception {
    }

}