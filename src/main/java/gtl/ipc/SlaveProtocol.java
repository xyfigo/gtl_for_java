package gtl.ipc;

import org.apache.hadoop.ipc.VersionedProtocol;

public interface SlaveProtocol  extends VersionedProtocol {
    static final long versionID = 1L; //版本号，默认情况下，不同版本号的RPC Client和Server之间不能相互通信


    SlaveDescriptor getSlaveDescriptor();
    ResultDescriptor executeCommand(CommandDescriptor cd);
    String executeCommand(String cd);
    byte[] executeCommand(byte[] cd);
    ResultDescriptor executeCommand(ParameterDescriptor cd);
}
