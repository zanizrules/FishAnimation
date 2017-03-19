import com.jogamp.opengl.GL2;

import java.util.LinkedList;
import java.util.Random;

/**
 * Created by Shane Birdsall on 19/03/2017.
 *
 */
class Plankton {
    static final int MAX_PLANKTON = 100;
    private static final ColourRGB LIME = new ColourRGB(0.08f, 1, 0.08f);
    private static Random rand;
    private static final float SIZE = 2.5f;
    private float x, y;

    Plankton() {
        if(rand == null) {
            rand = new Random();
        }
        // Random point in top of tank
        x = rand.nextFloat() * (2.0f) + -1.0f;
        y = rand.nextFloat() * ((Water.TIDE_HEIGHT -0.01f) - (2*Water.TIDE_HEIGHT)/3) + (2*Water.TIDE_HEIGHT)/3;
    }

    void draw(GL2 gl, float nightTransparency) {
        gl.glPointSize(SIZE);
        gl.glColor4d(LIME.red, LIME.green, LIME.blue, nightTransparency);
        gl.glBegin(GL2.GL_POINTS);
            gl.glVertex2d(x, y);
        gl.glEnd();
    }
}
