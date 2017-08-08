package gtl.hadoop.java.example;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hdfs.server.datanode.DataNode;
import org.apache.hadoop.hdfs.server.namenode.NameNode;
import org.apache.hadoop.hdfs.server.protocol.DatanodeProtocol;
import org.apache.hadoop.ipc.RPC;
import org.apache.hadoop.ipc.RPC.Server;


import java.io.IOException;

public class MyServer  {
    public static int port = 6432;
    public static String ipAddress = "localhost";

    public static void main(String[] args) throws Exception {

        ProxyProtocolImpl ppi = new ProxyProtocolImpl();
        RPC.Builder builder = new RPC.Builder(new Configuration());

        Server server1 =builder.setBindAddress(ipAddress)
                .setPort(port)
                .setProtocol(ProxyProtocol.class)
                .setNumHandlers(5)
                .setInstance(ppi)
                .build();

        server1.start();
        System.out.println("Server 1 Starting...");

        Server server2 =builder.setBindAddress(ipAddress)
                .setPort(6433)
                .setProtocol(ProxyProtocol.class)
                .setNumHandlers(5)
                .setInstance(ppi)
                .build();

        server2.start();
        System.out.println("Server 2 Starting...");
    }
}
