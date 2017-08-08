package gtl.io;

import java.util.Comparator;

public class DoubleComparator implements Comparator<Double>, java.io.Serializable {
    private static final long serialVersionUID = 1L;

    @Override
    public int compare(Double o1, Double o2) {
        return Double.compare(o1.doubleValue(),o2.doubleValue());
    }

}
