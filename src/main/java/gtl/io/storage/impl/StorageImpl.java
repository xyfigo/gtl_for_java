package gtl.io.storage.impl;

import gtl.io.storage.BufferedStorageManager;
import gtl.io.storage.StorageManager;

import java.io.IOException;

/**
 * Created by ZhenwenHe on 2017/3/15.
 */
public class StorageImpl implements java.io.Serializable{
    private static final long serialVersionUID = 1L;

    public static BufferedStorageManager createBufferedStorageManager(StorageManager storageManager, int capacity, boolean writeThrough) {
        return new BufferedStorageManagerImpl(storageManager, capacity, writeThrough);
    }

    public static StorageManager createMemoryStorageManager() throws IOException {
        return new MemoryStorageManager();
    }

    public static StorageManager createDiskStorageManager(String baseName, int pageSize, boolean overWrite) throws IOException {
        return new DiskStorageManager(baseName, pageSize, overWrite);
    }

    public static StorageManager createHdfsStorageManager(String baseName, int pageSize, boolean overWrite) throws IOException {
        return new DiskStorageManager(baseName, pageSize, overWrite);
    }
}
