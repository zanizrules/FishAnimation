import com.jogamp.opengl.GL2;

/**
 * Created by Shane on 13/03/2017.
 *
 */
public abstract class Shape {
    boolean filled;

    private Shape(boolean fill) {
        filled = fill;
    }
    Shape() {
        this(true);
    }

    abstract void draw(GL2 gl, float x, float y);

    public boolean isFilled() {
        return filled;
    }

}
