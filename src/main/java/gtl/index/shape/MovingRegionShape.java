package gtl.index.shape;

/**
 * Created by ZhenwenHe on 2016/12/22.
 */
public class MovingRegionShape extends TimeRegionShape implements EvolvingShape {
    private static final long serialVersionUID = 1L;
    @Override
    public RegionShape getVMBR() {
        return null;
    }

    @Override
    public RegionShape getMBRAtTime(double t) {
        return null;
    }
}