import com.jogamp.opengl.GL2;
import com.sun.org.apache.xerces.internal.impl.dv.xs.YearDV;

/**
 * Created by Shane Birdsall on 18/03/2017.
 *
 */
class Fish {
    private float x, y, xRad, yRad, tailFinMovement, sideFinMovement, currentAngle;
    private Direction fishDirection;
    private FishState fishState;
    private boolean moving;
    private Oval body;
    private Circle eye, pupil;
    private Triangle tailFin, sideFin;
    private int count;

    private static final ColourRGB ORANGE = new ColourRGB(1.0f, 0.65f, 0);
    private static final ColourRGB BLACK = new ColourRGB(0, 0, 0);

    Fish(float xRad, float yRad, float x, float y) {
        fishDirection = Direction.LEFT;
        fishState = FishState.NORMAL;
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
        eye = new Circle(1.0f, yRad/3, Bubble.WHITE);
        pupil = new Circle(1.0f, yRad/5, new ColourRGB(0,0,0));
        tailFin = new Triangle(new Point(x+(5*xRad)/6, y), new Point(x+xRad+(xRad/3), y+(yRad)), new Point(x+xRad+(xRad/3), y-(yRad)), ORANGE, ORANGE, 0.6f);
        sideFin = new Triangle(new Point(x-xRad/3.5f, y-yRad/7), new Point((x-xRad/3.5f)+(xRad/3), (y-yRad/7)+(2*yRad)/3), new Point((x-xRad/3.5f)+(xRad/3), (y-yRad/7)-(2*yRad)/3), ORANGE, Bubble.WHITE, 0.7f);
    }

    void changeMovement(Direction direction) {
        fishDirection = direction;
        moving = !moving;
        System.out.println("Moving: " + moving);
    }
    void updateState(FishState state) {
        fishState = state;
        if(state != FishState.NORMAL) {
            moving = false;
        }
    }

    boolean isMoving() {
        return moving;
    }

    void draw(GL2 gl) {
        if(!fishState.equals(FishState.NORMAL)) {
            currentAngle += fishState.angle;
        }

        gl.glPushMatrix();

        if(moving) {
            Point temp = new Point(0, 0);

            x += fishDirection.speed;
            temp.x = fishDirection.speed;
            y += (Math.tan(Math.toRadians(currentAngle))*fishDirection.speed); // TODO: can use mod but thats not really a fix.
            temp.y = (float) ((Math.tan(Math.toRadians(currentAngle))*fishDirection.speed));

            System.out.println("??: " + temp.y);

            sideFin.updatePositions(temp);
            tailFin.updatePositions(temp);
        }

        // Rotate Fish
        gl.glTranslatef(x, y, 0);
        gl.glRotatef(currentAngle, 0, 0, 1.0f); // Rotates around Origin if not using glTranslatef
        gl.glTranslatef(-x, -y, 0);

        // Draw parts of fish
        tailFin.draw(gl);
        body.draw(gl, x, y);
        sideFin.draw(gl);

        // Draw Mouth
        gl.glLineWidth(yRad*50);
        gl.glColor3d(BLACK.red,BLACK.green,BLACK.blue);
        gl.glBegin(GL2.GL_LINE_STRIP);
            gl.glVertex2d(x-xRad+(xRad/20),y-(yRad/3.5f));
            gl.glVertex2d(x-xRad+(xRad/5),y-(yRad/3.5f));
        gl.glEnd();
        gl.glFlush();

        // Move fins
        tailFin.moveBC(false, tailFinMovement);
        sideFin.moveBC(false, sideFinMovement);
        count++;
        if(count > 7) {
            tailFinMovement *= -1;
            sideFinMovement *= -1;
            count = 0;
        }
        gl.glPopMatrix();
    }
    void drawEye(GL2 gl) {
        gl.glPushMatrix();
        gl.glTranslatef(x, y, 0);
        gl.glRotatef(currentAngle, 0, 0, 1.0f);
        gl.glTranslatef(-x, -y, 0);
        eye.draw(gl, x-(xRad/1.4f), y+(yRad/6));
        pupil.draw(gl, x-(xRad/1.4f), y+(yRad/6));
        gl.glPopMatrix();
    }
}
