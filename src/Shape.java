import com.jogamp.opengl.GL2;

/**
 * Created by Shane on 13/03/2017.
 * The shape class was made to make creating specific shape classes easier.
 * This class outlines what I believe each shape class would need 9 times out of 10.
 */
abstract class Shape {
    boolean filled;
    private Shape(boolean fill) { filled = fill; }
    Shape() { this(true); }
    abstract void draw(GL2 gl, float x, float y);
}
