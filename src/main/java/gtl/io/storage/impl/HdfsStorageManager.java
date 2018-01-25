package gtl.io.storage.impl;

import gtl.common.Identifier;
import gtl.io.storage.StorageManager;

import java.io.IOException;

/**
 * 实现HDFS存储管理功能
 */
public class HdfsStorageManager implements StorageManager {

    @Override
    public byte[] loadByteArray(Identifier page) throws IOException {
        return new byte[0];
    }

    @Override
    public void storeByteArray(Identifier page, byte[] data) throws IOException {

    }

    @Override
    public void deleteByteArray(Identifier page) throws IOException {

    }

    @Override
    public void flush() throws IOException {

    }

    @Override
    public void close() throws IOException {

    }
}
