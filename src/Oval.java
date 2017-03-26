import com.jogamp.opengl.GL2;

/**
 * Created by Shane Birdsall on 17/03/2017.
 * The oval class is used to make drawing custom oval shapes easier.
 */
class Oval extends Shape {
    private float transparency;
    private final ColourRGB OVAL_COLOUR;
    private float xRadius, yRadius;

    Oval(float trans, float xRad, float yRad, ColourRGB col) {
        transparency = trans;
        xRadius = xRad;
        yRadius = yRad;
        OVAL_COLOUR = col;
    }

    @Override
    void draw(GL2 gl, float x, float y) {
        gl.glBegin(GL2.GL_TRIANGLE_FAN);
            gl.glColor4f(OVAL_COLOUR.RED, OVAL_COLOUR.GREEN, OVAL_COLOUR.BLUE, transparency);
            gl.glVertex2d(x, y);
            for(int i = 1; i < 360; i++) {
                gl.glVertex2d(x + (xRadius * Math.cos(i * (2*Math.PI)/50)), y + (((yRadius * Math.sin(i * (2*Math.PI)/50)))));
            }
        gl.glEnd();
    }
}
