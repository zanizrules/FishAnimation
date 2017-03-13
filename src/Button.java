import com.jogamp.opengl.GL2;
import com.jogamp.opengl.util.gl2.GLUT;

/**
 * Created by Shane Birdsall on 12/03/2017.
 *
 */
public class Button extends Shape {
    private GLUT glut = new GLUT();
    private float width;

    private float height;

    float getWidth() {
        return width;
    }
    float getHeight() {
        return height;
    }

    Button(float w, float h) {
        super();
        width = w;
        height = h;
    }

    @Override
    public void draw(GL2 gl, float x, float y) {
        gl.glBegin(GL2.GL_POLYGON);
            gl.glVertex2f(x, y);
            gl.glVertex2f(x+width, y);
            gl.glVertex2f(x+width, y+height);
            gl.glVertex2f(x, y+height);
        gl.glEnd();
    }
    void addText(GL2 gl, String text, float x, float y) {
        gl.glRasterPos2d(x, y);
        glut.glutBitmapString(GLUT.BITMAP_8_BY_13, text);
    }
}