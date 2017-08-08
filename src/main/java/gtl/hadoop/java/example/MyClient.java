package gtl.hadoop.java.example;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.ipc.RPC;

import java.io.IOException;
import java.net.InetSocketAddress;

public class MyClient {
    public static void main(String[] args) {
        InetSocketAddress inetSocketAddress = new InetSocketAddress(
                MyServer.ipAddress, MyServer.port);

        try {
            // 注意：这里传入的版本号需要与代理保持一致
            ProxyProtocol proxy = (ProxyProtocol) RPC.getProxy(
                    ProxyProtocol.class, ProxyProtocol.versionID, inetSocketAddress,
                    new Configuration());
            int result = proxy.add(10, 25);
            System.out.println("10+25=" + result);

            RPC.stopProxy(proxy);
        } catch (IOException e) {

            e.printStackTrace();
        }

        try {
            // 注意：这里传入的版本号需要与代理保持一致
            ProxyProtocol proxy = (ProxyProtocol) RPC.getProxy(
                    ProxyProtocol.class, ProxyProtocol.versionID, new InetSocketAddress("127.0.0.1",6433),
                    new Configuration());
            int result = proxy.add(10, 25);
            System.out.println("10+25=" + result);

            RPC.stopProxy(proxy);
        } catch (IOException e) {

            e.printStackTrace();
        }
    }
}
