import com.jogamp.opengl.GL2;

/**
 * Created by Shane Birdsall on 18/03/2017.
 * The triangle class was made to help in creating custom triangle objects.
 * For example this class helped make fish fins.
 */
class Triangle {
    private Point pointA, pointB, pointC;
    private ColourRGB innerCol, outerCol;
    private float transparency;

    Triangle(Point a, Point b, Point c, ColourRGB inCol, ColourRGB outCol, float trans) {
        this.pointA = a;
        this.pointB = b;
        this.pointC = c;
        innerCol = inCol;
        outerCol = outCol;
        transparency = trans;
    }

    void moveBC(boolean vertical, float distance) {
        if(vertical) {
            pointB.y += distance;
            pointC.y += distance;
        } else { // Move horizontally
            pointB.x += distance;
            pointC.x += distance;
        }
    }

    void updatePositions(Point movement) {
        pointA.x += movement.x;
        pointA.y += movement.y;
        pointB.x += movement.x;
        pointB.y += movement.y;
        pointC.x += movement.x;
        pointC.y += movement.y;
    }

    void draw(GL2 gl) {
        gl.glBegin(GL2.GL_TRIANGLE_STRIP);
        gl.glColor4d(innerCol.red, innerCol.green, innerCol.blue, 1.0f);
            gl.glVertex2d(pointA.x, pointA.y);
            gl.glColor4d(outerCol.red, outerCol.green, outerCol.blue, transparency);
            gl.glVertex2d(pointB.x, pointB.y);
            gl.glVertex2d(pointC.x, pointC.y);
        gl.glEnd();
    }
}
