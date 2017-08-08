package gtl.hadoop.java.example;

import org.apache.hadoop.ipc.VersionedProtocol;

public interface ProxyProtocol extends VersionedProtocol {
    static final long versionID = 23234L; //版本号，默认情况下，不同版本号的RPC Client和Server之间不能相互通信
    int add(int number1,int number2);
    int register(String ip, int port);
}
