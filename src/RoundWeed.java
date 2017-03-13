import com.jogamp.opengl.GL2;

import java.util.Random;

/**
 * Created by Shane on 13/03/2017.
 *
 */
public class RoundWeed extends Shape {
    private Random rand = new Random();
    private float[] randomRadius;
    private ColourRGB colour;

    RoundWeed(float radius, float variance, ColourRGB rgb) {
        super();
        colour = rgb;
        initialiseArray(radius, variance);
    }
    private void initialiseArray(float radius, float variance) {
        randomRadius = new float[360];
        for(int i = 0; i < 360; i++) {
            randomRadius[i] = rand.nextFloat() * (2*variance) + (radius-variance);
        }
    }

    @Override
    public void draw(GL2 gl, float x, float y) {
        gl.glBegin(filled ? GL2.GL_TRIANGLE_FAN:GL2.GL_LINE_LOOP);
            gl.glColor3f(1.0f, 1.0f, 1.0f); // White
            gl.glVertex2d(x, y);
            gl.glColor3f(colour.red, colour.green, colour.blue); // Custom defined colour
            for(int i = 1; i < 360; i++) {
                gl.glVertex2d(x + (randomRadius[i] * Math.cos(i * (2*Math.PI)/50)), y + (((randomRadius[i] * Math.sin(i * (2*Math.PI) / 50)))));
            }
        gl.glEnd();
    }
}
