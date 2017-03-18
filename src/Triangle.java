import com.jogamp.opengl.GL2;

/**
 * Created by Shane Birdsall on 18/03/2017.
 *
 */
class Triangle {
    private Point a, b, c;
    private ColourRGB innerCol, outerCol;
    private float transperancy;

    Triangle(Point a, Point b, Point c, ColourRGB inCol, ColourRGB outCol, float trans) {
        this.a = a;
        this.b = b;
        this.c = c;
        innerCol = inCol;
        outerCol = outCol;
        transperancy = trans;
    }

    void moveBC(boolean vertical, float distance) {
        if(vertical) {
            b.y += distance;
            c.y += distance;
        } else {
            b.x += distance;
            c.x += distance;
        }
    }

    void draw(GL2 gl) {
        gl.glBegin(GL2.GL_TRIANGLE_STRIP);
        gl.glColor4d(innerCol.red, innerCol.green, innerCol.blue, 1.0f);
            gl.glVertex2d(a.x, a.y);
            gl.glColor4d(outerCol.red, outerCol.green, outerCol.blue, transperancy);
            gl.glVertex2d(b.x, b.y);
            gl.glVertex2d(c.x, c.y);
        gl.glEnd();
    }
}