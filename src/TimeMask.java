import com.jogamp.opengl.GL2;

/**
 * Created by Shane Birdsall on 19/03/2017.
 * This class is used in creating day/night cycles within the fish tank.
 */
class TimeMask {
    private static final ColourRGB BLACK = new ColourRGB(0, 0, 0);
    private float transparency;
    private float increaseTransparencyBy;

    TimeMask() {
        transparency = 0;
        increaseTransparencyBy = 0.0005f;
    }
    void reset() { transparency = 0; }
    float getTransparency() { return transparency; }

    void draw(GL2 gl) {
        if(transparency > 1.2f || transparency < 0) { // 1.2f allows for a longer time to be spent at pitch black
            increaseTransparencyBy *= -1;
        }
        transparency += increaseTransparencyBy;

        gl.glColor4d(BLACK.red, BLACK.green, BLACK.blue, transparency);
        gl.glBegin(GL2.GL_POLYGON); // Fill whole screen
            gl.glVertex2d(-1.0f, -1.0f);
            gl.glVertex2d(1.0f, -1.0f);
            gl.glVertex2d(1.0f, 1.0f);
            gl.glVertex2d(-1.0f, 1.0f);
        gl.glEnd();
    }
}
