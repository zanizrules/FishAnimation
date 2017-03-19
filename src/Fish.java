import com.jogamp.opengl.GL2;

/**
 * Created by Shane Birdsall on 18/03/2017.
 *
 */
class Fish {
    private float x, y, xRad, yRad, tailFinMovement, sideFinMovement;
    private Oval body;
    private Circle eye, pupil;

    private Triangle tailFin, sideFin;
    private int count;

    private static final ColourRGB ORANGE = new ColourRGB(1.0f, 0.65f, 0);

    Fish(float xRad, float yRad, float x, float y) {
        count = 0;
        this.x = x;
        this.y = y;
        this.xRad = xRad;
        this.yRad = yRad;
        tailFinMovement = 0;
        sideFinMovement = 0;
        tailFinMovement = - 0.002f;
        sideFinMovement = - 0.0035f;
        body = new Oval(1.0f, xRad, yRad, ORANGE);
        eye = new Circle(1.0f, yRad/3, x-(xRad/1.4f), y+(yRad/6), Bubble.WHITE);
        pupil = new Circle(1.0f, yRad/5, x-(xRad/1.4f), y+(yRad/6), new ColourRGB(0,0,0));
        tailFin = new Triangle(new Point(x+(5*xRad)/6, y), new Point(x+xRad+(xRad/3), y+(yRad)), new Point(x+xRad+(xRad/3), y-(yRad)), ORANGE, ORANGE, 0.6f);
        sideFin = new Triangle(new Point(x-xRad/3.5f, y-yRad/7), new Point((x-xRad/3.5f)+(xRad/3), (y-yRad/7)+(2*yRad)/3), new Point((x-xRad/3.5f)+(xRad/3), (y-yRad/7)-(2*yRad)/3), ORANGE, Bubble.WHITE, 0.7f);
    }

    void draw(GL2 gl) {

        // Draw parts of fish
        tailFin.draw(gl);
        body.draw(gl, x, y);
        sideFin.draw(gl);

        // Draw Mouth
        gl.glLineWidth(yRad*50);
        gl.glColor3d(0,0,0);
        gl.glBegin(GL2.GL_LINE_STRIP);
            gl.glVertex2d(x-xRad+(xRad/20),y-(yRad/3.5f));
            gl.glVertex2d(x-xRad+(xRad/5),y-(yRad/3.5f));
        gl.glEnd();

        // Move fins
        tailFin.moveBC(false, tailFinMovement);
        sideFin.moveBC(false, sideFinMovement);
        count++;
        if(count > 7) {
            tailFinMovement *= -1;
            sideFinMovement *= -1;
            count = 0;
        }
    }
    void drawEye(GL2 gl) {
        eye.draw(gl);
        pupil.draw(gl);
    }
}
