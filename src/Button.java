import com.jogamp.opengl.GL2;
import com.jogamp.opengl.util.gl2.GLUT;

/**
 * Created by Shane Birdsall on 12/03/2017.
 * This class represents the buttons used at the top of the fish tank
 */
class Button extends Shape {
    private final static GLUT glut = new GLUT();
    private final float BUTTON_WIDTH, BUTTON_HEIGHT;
    private boolean enabled;

    float getButtonWidth() {
        return BUTTON_WIDTH;
    }
    float getButtonHeight() {
        return BUTTON_HEIGHT;
    }

    Button(float w, float h) {
        super();
        BUTTON_WIDTH = w;
        BUTTON_HEIGHT = h;
    }

    boolean isEnabled() {
        return enabled;
    }

    void click() {
        enabled = !enabled;
    }

    @Override
    public void draw(GL2 gl, float x, float y) {
        gl.glBegin(GL2.GL_POLYGON);
            gl.glVertex2f(x, y);
            gl.glVertex2f(x+ BUTTON_WIDTH, y);
            gl.glVertex2f(x+ BUTTON_WIDTH, y+ BUTTON_HEIGHT);
            gl.glVertex2f(x, y+ BUTTON_HEIGHT);
        gl.glEnd();
    }
    void addText(GL2 gl, String text, float x, float y) {
        gl.glRasterPos2d(x, y);
        glut.glutBitmapString(GLUT.BITMAP_8_BY_13, text);
    }
}