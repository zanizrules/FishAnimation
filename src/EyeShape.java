import com.jogamp.opengl.GL2;

/**
 * Created by Shane Birdsall on 17/03/2017.
 *
 */
public class EyeShape extends Shape {
    private float transparency;
    private ColourRGB colour;
    private float xRadius, yRadius;

    EyeShape(float trans, float xRad, float yRad, ColourRGB rgb) {
        transparency = trans;
        xRadius = xRad;
        yRadius = yRad;
        colour = rgb;
    }


    @Override
    void draw(GL2 gl, float x, float y) {
        gl.glBegin(GL2.GL_TRIANGLE_FAN);
            gl.glColor4f(1.0f, 1.0f, 1.0f, transparency); // White
            gl.glVertex2d(x, y);
            gl.glColor4f(colour.red, colour.green, colour.blue, transparency);
            for(int i = 1; i < 360; i++) {
                gl.glVertex2d(x - xRadius + (i/360), y + yRadius - 180/i);
            }
//            for(int i = 1; i < 90; i++) {
//                gl.glVertex2d(x + (length/2), y + radius/i);
//            }
//            for(int i = 1; i < 90; i++) {
//                gl.glVertex2d(x + (radius/i), y + radius/i);
//            }

        gl.glEnd();
    }
}
