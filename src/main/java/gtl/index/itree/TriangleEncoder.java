package gtl.index.itree;

import gtl.geom.Geom2DSuits;
import gtl.geom.IsoscelesTriangle;
import gtl.geom.Triangle;

public class TriangleEncoder implements java.io.Serializable{

    private static final long serialVersionUID = 1L;

    Triangle rootTriangle;
    public TriangleEncoder(Triangle triangle){
        rootTriangle = (Triangle)triangle.clone();
    }

    public void reset(Triangle triangle){
        rootTriangle = (Triangle)triangle.clone();
    }
    /**
     * parse the triangle code,0 represents the left sub-triangle,
     * 1 represents the right sub-triangle,
     * the first char of the identifier is always 1 except identifier is empty
     * @param identifier code of the triangle
     * @return the corresponding triangle
     */
    public Triangle parse(String identifier){
        Triangle r =null;
        if(identifier.isEmpty()) return r;
        int s = identifier.length();
        if(s==1) return rootTriangle;
        Triangle p =  rootTriangle;
        for(int i=1;i<s;++i){
            if(identifier.charAt(i)=='0')
                r = p.leftTriangle();
            else
                r = p.rightTriangle();
            p=  r;
        }
        return r;
    }

    /**
     * whether the rootTriangle contains sub
     * @param sub triangle
     * @return true-contains, false - does not contain
     */
    public boolean contains(Triangle sub){
        return Geom2DSuits.contains(rootTriangle,sub);
    }

    /**
     * calculate the minimum triangle which contains the sub-triangle,
     * and return its code string
     * @param subtriangle
     * @return calculate the minimum triangle which contains the sub-triangle,
     * and return its code string, if the string returned is empty,
     * it means that rootTriangle does not contain subtriangle
     */
    public String encode(Triangle subtriangle){
        IsoscelesTriangle p = (IsoscelesTriangle) rootTriangle;
        StringBuilder sb = new StringBuilder();
        if(contains(subtriangle)){
            sb.append('1');
            Triangle left = p.leftTriangle();
            Triangle right = p.rightTriangle();
            if(!Geom2DSuits.contains(left,subtriangle)){
                if(!Geom2DSuits.contains(right,subtriangle))
                    return sb.toString();
                else
                    p = (IsoscelesTriangle)right;
            }
            else{
                p=(IsoscelesTriangle) left;
            }
        }
        else
            return sb.toString();

        do{
            Triangle left = p.leftTriangle();
            if(Geom2DSuits.contains(left,subtriangle)){
                sb.append('0');
                p = (IsoscelesTriangle)left;
            }
            else{
                Triangle right = p.rightTriangle();
                if(Geom2DSuits.contains(right,subtriangle)) {
                    sb.append('1');
                    p = (IsoscelesTriangle)right;
                }
                else
                    return sb.toString();
            }
        }while (p!=null);
        return sb.toString();
    }
}
