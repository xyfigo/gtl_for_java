package gtl.io;

import org.jetbrains.annotations.NotNull;

import java.net.URI;

public class File extends java.io.File {
    /**
     *
     * @param fullPathName 完全名称，例如： usr/he/datafile.csv
     * @return 返回不含路径的文件，datafile.csv
     */
    public static String getFileName(String fullPathName){
        if(fullPathName==null) return "";
        if(fullPathName.isEmpty()) return "";

        int i = fullPathName.lastIndexOf(java.io.File.separatorChar);
        if(i>=0)
            return fullPathName.substring(i+1);
        else
            return "";
    }
    /**
     *
     * @param fullPathName 完全名称，例如： usr/he/datafile.csv
     * @return 返回目录 /usr/he
     */
    public static String getDirectory(String fullPathName){
        if(fullPathName==null) return "";
        if(fullPathName.isEmpty()) return "";

        int i = fullPathName.lastIndexOf(java.io.File.separatorChar);
        if(i>=0)
            return fullPathName.substring(0,i);
        else
            return "";
    }
    /**返回文件后缀名
     *
     * @param fullPathName 完全名称，例如： usr/he/datafile.csv
     * @return 返回不含路径的文件，csv
     */
    public static String getSuffixName(String fullPathName){
        if(fullPathName==null) return "";
        if(fullPathName.isEmpty()) return "";

        int i = fullPathName.lastIndexOf('.');
        if(i>=0)
            return fullPathName.substring(i+1);
        else
            return "";
    }

    /**
     *
     * @param fullPathName 完全名称，例如： usr/he/datafile.csv
     * @param newSuffix 新的文件名后缀，svm
     * @return usr/he/datafile.svm
     */
    public static String replaceSuffixName(String fullPathName,String newSuffix){
        if(fullPathName==null) return "";
        if(fullPathName.isEmpty()) return "";

        int i = fullPathName.lastIndexOf('.');
        if(i>=0)
            return fullPathName.substring(0,i+1)+newSuffix;
        else
            return "";
    }

    /**
     * Creates a new <code>File</code> instance by converting the given
     * pathname string into an abstract pathname.  If the given string is
     * the empty string, then the result is the empty abstract pathname.
     *
     * @param pathname A pathname string
     * @throws NullPointerException If the <code>pathname</code> argument is <code>null</code>
     */
    public File(@NotNull String pathname) {
        super(pathname);
    }

    /**
     * Creates a new <code>File</code> instance from a parent pathname string
     * and a child pathname string.
     * <p>
     * <p> If <code>parent</code> is <code>null</code> then the new
     * <code>File</code> instance is created as if by invoking the
     * single-argument <code>File</code> constructor on the given
     * <code>child</code> pathname string.
     * <p>
     * <p> Otherwise the <code>parent</code> pathname string is taken to denote
     * a directory, and the <code>child</code> pathname string is taken to
     * denote either a directory or a file.  If the <code>child</code> pathname
     * string is absolute then it is converted into a relative pathname in a
     * system-dependent way.  If <code>parent</code> is the empty string then
     * the new <code>File</code> instance is created by converting
     * <code>child</code> into an abstract pathname and resolving the result
     * against a system-dependent default directory.  Otherwise each pathname
     * string is converted into an abstract pathname and the child abstract
     * pathname is resolved against the parent.
     *
     * @param parent The parent pathname string
     * @param child  The child pathname string
     * @throws NullPointerException If <code>child</code> is <code>null</code>
     */
    public File(String parent, @NotNull String child) {
        super(parent, child);
    }

    /**
     * Creates a new <code>File</code> instance from a parent abstract
     * pathname and a child pathname string.
     * <p>
     * <p> If <code>parent</code> is <code>null</code> then the new
     * <code>File</code> instance is created as if by invoking the
     * single-argument <code>File</code> constructor on the given
     * <code>child</code> pathname string.
     * <p>
     * <p> Otherwise the <code>parent</code> abstract pathname is taken to
     * denote a directory, and the <code>child</code> pathname string is taken
     * to denote either a directory or a file.  If the <code>child</code>
     * pathname string is absolute then it is converted into a relative
     * pathname in a system-dependent way.  If <code>parent</code> is the empty
     * abstract pathname then the new <code>File</code> instance is created by
     * converting <code>child</code> into an abstract pathname and resolving
     * the result against a system-dependent default directory.  Otherwise each
     * pathname string is converted into an abstract pathname and the child
     * abstract pathname is resolved against the parent.
     *
     * @param parent The parent abstract pathname
     * @param child  The child pathname string
     * @throws NullPointerException If <code>child</code> is <code>null</code>
     */
    public File(java.io.File parent, @NotNull String child) {
        super(parent, child);
    }

    /**
     * Creates a new <tt>File</tt> instance by converting the given
     * <tt>file:</tt> URI into an abstract pathname.
     * <p>
     * <p> The exact form of a <tt>file:</tt> URI is system-dependent, hence
     * the transformation performed by this constructor is also
     * system-dependent.
     * <p>
     * <p> For a given abstract pathname <i>f</i> it is guaranteed that
     * <p>
     * <blockquote><tt>
     * new File(</tt><i>&nbsp;f</i><tt>.{@link #toURI() toURI}()).equals(</tt><i>&nbsp;f</i><tt>.{@link #getAbsoluteFile() getAbsoluteFile}())
     * </tt></blockquote>
     * <p>
     * so long as the original abstract pathname, the URI, and the new abstract
     * pathname are all created in (possibly different invocations of) the same
     * Java virtual machine.  This relationship typically does not hold,
     * however, when a <tt>file:</tt> URI that is created in a virtual machine
     * on one operating system is converted into an abstract pathname in a
     * virtual machine on a different operating system.
     *
     * @param uri An absolute, hierarchical URI with a scheme equal to
     *            <tt>"file"</tt>, a non-empty path component, and undefined
     *            authority, query, and fragment components
     * @throws NullPointerException     If <tt>uri</tt> is <tt>null</tt>
     * @throws IllegalArgumentException If the preconditions on the parameter do not hold
     * @see #toURI()
     * @see URI
     * @since 1.4
     */
    public File(@NotNull URI uri) {
        super(uri);
    }



}
