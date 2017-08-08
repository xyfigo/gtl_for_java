package gtl.util;

/**
 * Created by hadoop on 17-3-20.
 */

import java.io.*;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;


public class StringUtil {
    public final static String NEWLINE = System.getProperty("line.separator");
    private static NumberFormat SIMPLE_ORDINATE_FORMAT = new DecimalFormat("0.#");

    /**
     * Mimics the the Java SE {@link String#split(String)} method.
     *
     * @param s         the string to split.
     * @param separator the separator to use.
     * @return the array of split strings.
     */
    public static String[] split(String s, String separator) {
        int separatorlen = separator.length();
        ArrayList tokenList = new ArrayList();
        String tmpString = "" + s;
        int pos = tmpString.indexOf(separator);
        while (pos >= 0) {
            String token = tmpString.substring(0, pos);
            tokenList.add(token);
            tmpString = tmpString.substring(pos + separatorlen);
            pos = tmpString.indexOf(separator);
        }
        if (tmpString.length() > 0)
            tokenList.add(tmpString);
        String[] res = new String[tokenList.size()];
        for (int i = 0; i < res.length; i++) {
            res[i] = (String) tokenList.get(i);
        }
        return res;
    }

    /**
     * Returns an throwable's stack trace
     */
    public static String getStackTrace(Throwable t) {
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        PrintStream ps = new PrintStream(os);
        t.printStackTrace(ps);
        return os.toString();
    }

    public static String getStackTrace(Throwable t, int depth) {
        String stackTrace = "";
        StringReader stringReader = new StringReader(getStackTrace(t));
        LineNumberReader lineNumberReader = new LineNumberReader(stringReader);
        for (int i = 0; i < depth; i++) {
            try {
                stackTrace += lineNumberReader.readLine() + NEWLINE;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return stackTrace;
    }

    public static String toString(double d) {
        return SIMPLE_ORDINATE_FORMAT.format(d);
    }

    public static String spaces(int n) {
        return chars(' ', n);
    }

    public static String chars(char c, int n) {
        char[] ch = new char[n];
        for (int i = 0; i < n; i++) {
            ch[i] = c;
        }
        return new String(ch);
    }

    public static String loadString(DataInput in) throws IOException{
        try {
            int bytesLength = in.readInt();
            if(bytesLength==0) return new String("");
            byte [] bs = new byte[bytesLength];
            in.readFully(bs,0,bytesLength);
            return new String(bs,0,bytesLength);
        }
        catch (IOException e){
            e.printStackTrace();
            return new String("");
        }
    }

    public static int storeString(String s, DataOutput out) throws IOException{
        try {
            byte [] bs = s.getBytes();
            out.writeInt(bs.length);
            if(bs.length>0)
                out.write(bs);
            return bs.length;
        }
        catch (IOException e){
            e.printStackTrace();
            return 0;
        }
    }

    public static long getByteArraySize(String s){
        return s.getBytes().length;
    }

}
