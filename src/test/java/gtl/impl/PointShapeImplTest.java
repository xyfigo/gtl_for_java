package gtl.impl;

import gtl.index.shape.PointShape;
import gtl.index.shape.ShapeSuits;
import junit.framework.TestCase;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * Created by ZhenwenHe on 2016/12/8.
 */
public class PointShapeImplTest extends TestCase {
    public void testClone() throws Exception {
        PointShape p1= ShapeSuits.createPoint(1.0,2.0,3.0);
        System.out.println(p1.toString());
        PointShape p2 = (PointShape) p1.clone();
        System.out.println(p2.toString());


        ByteArrayOutputStream baos= new ByteArrayOutputStream();
        p1.setZ(45.0);
        p1.write(baos);
        byte[] r = baos.toByteArray();

        System.out.println(r.length);

        p2.read(new ByteArrayInputStream(r));
        System.out.println(p2.toString());


        HashMap<Integer,Double> hm=new HashMap<Integer,Double>();
        hm.put(1,1.0);
        hm.put(2,2.0);
        hm.put(3,3.0);
        Double d=hm.get(2);
        System.out.println(d);
        d=hm.get(3);
        System.out.println(d);

        Set<Map.Entry<Integer,Double>> s= hm.entrySet();
        Iterator<Map.Entry<Integer,Double>> it = s.iterator();
        while(it.hasNext()){
            Map.Entry<Integer,Double> e = it.next();
            e.setValue(null);
        }

        d=hm.get(3);
        System.out.println(d);
        System.out.println(hm.size());
    }

}