import com.jogamp.opengl.GL2;

/**
 * Created by Shane Birdsall on 17/03/2017.
 *
 */
class Oval extends Shape {
    private float transparency;
    private ColourRGB colour;
    private float xRadius, yRadius;
    private float x,y;

    Oval(float trans, float xRad, float yRad, ColourRGB col) {
        transparency = trans;
        xRadius = xRad;
        yRadius = yRad;
        colour = col;
    }

    @Override
    void draw(GL2 gl, float x, float y) {
        gl.glBegin(GL2.GL_TRIANGLE_FAN);
            gl.glColor4f(colour.red, colour.green, colour.blue, transparency);
            gl.glVertex2d(x, y);
            for(int i = 1; i < 360; i++) {
                gl.glVertex2d(x + (xRadius * Math.cos(i * (2*Math.PI)/50)), y + (((yRadius * Math.sin(i * (2*Math.PI)/50)))));
            }
        gl.glEnd();
    }
}
