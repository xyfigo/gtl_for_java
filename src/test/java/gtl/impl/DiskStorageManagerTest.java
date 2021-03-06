package gtl.impl;

import gtl.common.CommonSuits;
import gtl.common.Identifier;
import gtl.index.IndexSuits;
import gtl.io.storage.StorageManager;
import gtl.io.storage.StorageSuits;
import junit.framework.TestCase;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by ZhenwenHe on 2016/12/10.
 */
public class DiskStorageManagerTest extends TestCase {



    public void setUp() throws Exception {
        super.setUp();

    }

    public void tearDown() throws Exception {

    }

    public void testLoadByteArray() throws Exception {

    }

    public void testStoreByteArray() throws Exception {
        ArrayList<Identifier> ids=new ArrayList<Identifier>();
        int pageSize =1024*8;//8k
        StorageManager dsm = StorageSuits.createDiskStorageManager(IndexSuits.DATA_DIR+ "test",pageSize,true);
        int dataSize=(int)(pageSize*2.6);
        byte [] data =new byte[dataSize];
        for(int i=0;i<10;i++){
            for(int j=0;j<dataSize;j++){
                data[j]=(byte)i;
            }
            Identifier pi = CommonSuits.createIdentifier(StorageManager.NEW_PAGE);
            dsm.storeByteArray(pi,data);
            ids.add((Identifier) pi.clone());
        }
        dsm.close();
        dsm = StorageSuits.createDiskStorageManager(IndexSuits.DATA_DIR+"test",pageSize,false);
        Iterator<Identifier> it = ids.iterator();
        int k=0;
        while(it.hasNext()){
            data=null;
            Identifier id=it.next();
            System.out.println(id);
            data=dsm.loadByteArray(id);
            assertEquals(data.length, dataSize);
            System.out.println(data.length);
            System.out.println(data[0]);
            assertEquals(k, data[0]);
            System.out.println(data[data.length-1]);
            assertEquals(k, data[data.length - 1]);
            k++;
        }
        dsm.close();
    }

    public void testDeleteByteArray() throws Exception {

    }

    public void testFlush() throws Exception {

    }

}