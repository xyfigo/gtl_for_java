package gtl.io;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class FileTest {
    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void getFileName() throws Exception {
        String fullPathName = "d:\\devs\\data\\spark\\iris.csv";
        boolean b= File.getFileName(fullPathName).compareTo("iris.csv")==0;
        Assert.assertTrue(b);
    }

    @Test
    public void getSuffixName() throws Exception {
        String fullPathName = "d:\\devs\\data\\spark\\iris.csv";
        boolean b= File.getSuffixName(fullPathName).compareTo("csv")==0;
        Assert.assertTrue(b);
    }

    @Test
    public void replaceSuffixName() throws Exception {
        String fullPathName = "d:\\devs\\data\\spark\\iris.csv";
        boolean b= File.replaceSuffixName(fullPathName,"svm").compareTo("d:\\devs\\data\\spark\\iris.svm")==0;
        Assert.assertTrue(b);
    }

    @Test
    public void getDirectory() throws Exception {
        String fullPathName = "d:\\devs\\data\\spark\\iris.csv";
        boolean b= File.getDirectory(fullPathName).compareTo("d:\\devs\\data\\spark")==0;
        Assert.assertTrue(b);
    }

}