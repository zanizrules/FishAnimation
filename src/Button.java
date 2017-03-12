import com.jogamp.opengl.GL2;

/**
 * Created by Shane Birdsall on 12/03/2017.
 *
 */
public class Button implements Shape {
    private GL2 gl;

    public float getWidth() {
        return width;
    }

    public float getHeight() {
        return height;
    }

    private float width, height;
    private boolean filled;

    Button(GL2 graphic, float w, float h, boolean fill) {
        gl = graphic;
        width = w;
        height = h;
        filled = fill;
    }

    @Override
    public void Draw(float x, float y) {
        gl.glBegin(GL2.GL_POLYGON);
            gl.glVertex2f(x, y);
            gl.glVertex2f(x+width, y);
            gl.glVertex2f(x+width, y+height);
            gl.glVertex2f(x, y+height);
        gl.glEnd();
    }
}