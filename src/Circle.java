import com.jogamp.opengl.GL2;

/**
 * Created by Shane Birdsall on 18/03/2017.
 *
 */
class Circle {
    float transparency;
    float radius;
    float x,y;
    private ColourRGB outside, inside;

    Circle(float transparency, float radius, float x, float y, ColourRGB out, ColourRGB in) {
        this.transparency = transparency;
        this.radius = radius;
        this.x = x;
        this.y = y;
        inside = in;
        outside = out;
    }

    Circle(float transparency, float radius, float x, float y, ColourRGB out) {
        this(transparency, radius, x, y, out, out);
    }

    void draw(GL2 gl) {
        gl.glBegin(GL2.GL_TRIANGLE_FAN);
            gl.glColor4f(inside.red, inside.green, inside.blue, transparency);
            gl.glVertex2d(x, y);
            gl.glColor4f(outside.red, outside.green, outside.blue, transparency);
            for(int i = 1; i < 360; i++) {
                gl.glVertex2d(x + (radius * Math.cos(i * (2*Math.PI)/50)), y + (((radius * Math.sin(i * (2*Math.PI) / 50)))));
            }
        gl.glEnd();
    }
}
