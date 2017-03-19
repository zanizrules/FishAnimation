import com.jogamp.opengl.GL2;

/**
 * Created by Shane Birdsall on 19/03/2017.
 *
 */
class TimeMask {
    private static final ColourRGB BLACK = new ColourRGB(0, 0, 0);
    private float transparency;
    private float increaseBy;

    TimeMask() {
        transparency = 0;
        increaseBy = 0.0005f;
    }
    void reset() {
        transparency = 0;
    }
    float getTransparency() {
        return transparency;
    }

    void draw(GL2 gl) {
        if(transparency > 0.95f || transparency < 0) {
            increaseBy *= -1;
        }
        transparency += increaseBy;

        gl.glColor4d(BLACK.red, BLACK.green, BLACK.blue, transparency);
        gl.glBegin(GL2.GL_POLYGON);
            gl.glVertex2d(-1.0f, -1.0f);
            gl.glVertex2d(1.0f, -1.0f);
            gl.glVertex2d(1.0f, 1.0f);
            gl.glVertex2d(-1.0f, 1.0f);
        gl.glEnd();
    }
}
