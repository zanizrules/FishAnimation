import com.jogamp.opengl.GL2;

/**
 * Created by Shane Birdsall on 18/03/2017.
 *
 */
class Circle {
    float transparency;
    float radius;
    private ColourRGB outside, inside;

    Circle(float transparency, float radius, ColourRGB out, ColourRGB in) {
        this.transparency = transparency;
        this.radius = radius;
        inside = in;
        outside = out;
    }

    Circle(float transparency, float radius, ColourRGB out) {
        this(transparency, radius, out, out);
    }

    void draw(GL2 gl, float x, float y) {
        gl.glBegin(GL2.GL_TRIANGLE_FAN);
            gl.glColor4f(inside.red, inside.green, inside.blue, transparency);
            gl.glVertex2d(x, y);
            gl.glColor4f(outside.red, outside.green, outside.blue, transparency);
            for(int i = 1; i < 360; i++) {
                gl.glVertex2d(x + (radius * Math.cos(i * (2*Math.PI)/358)), y + (((radius * Math.sin(i * (2*Math.PI)/358)))));
            }
        gl.glEnd();
    }
}
