package gtl.hadoop.java.example;

import org.apache.hadoop.ipc.ProtocolSignature;

import java.io.IOException;

public class ProxyProtocolImpl implements ProxyProtocol {
    @Override
    public int add(int number1, int number2) {
        System.out.println("我被调用了!");
        int result = number1+number2;
        return result;
    }

    @Override
    public int register(String ip, int port) {
        return 0;
    }

    @Override
    public long getProtocolVersion(String s, long l) throws IOException {
        System.out.println("MyProxy.ProtocolVersion=" + ProxyProtocol.versionID);
        // 注意：这里返回的版本号与客户端提供的版本号需保持一致
        return ProxyProtocol.versionID;
    }

    @Override
    public ProtocolSignature getProtocolSignature(String s, long l, int i) throws IOException {
        return new ProtocolSignature(versionID,null);
    }
}
