import com.jogamp.opengl.GL2;

/**
 * Created by Shane Birdsall on 18/03/2017.
 * The Fish class contains the components used to draw my fish for the fish tank.
 */
class Fish {
    private static final ColourRGB ORANGE = new ColourRGB(1.0f, 0.65f, 0);
    private static final ColourRGB BLACK = new ColourRGB(0, 0, 0);
    private static final float SAND_BOUND = -0.75f, EATEN_BOUND = -0.3f;
    private float x, y, xRad, yRad, tailFinMovement, sideFinMovement, currentAngle;
    private Direction fishDirection;
    private FishState fishState;
    private boolean moving, dead;
    private Oval body;
    private Circle eye, pupil;
    private Triangle tailFin, sideFin;
    private int finMovementCount;

    Fish(float xRad, float yRad, float x, float y) {
        fishDirection = Direction.LEFT;
        fishState = FishState.NORMAL;
        finMovementCount = 0;
        this.x = x;
        this.y = y;
        this.xRad = xRad;
        this.yRad = yRad;
        tailFinMovement = - 0.002f;
        sideFinMovement = - 0.0035f;
        body = new Oval(1.0f, xRad, yRad, ORANGE);
        eye = new Circle(1.0f, yRad/3, Bubble.WHITE);
        pupil = new Circle(1.0f, yRad/5, new ColourRGB(0,0,0));
        createFins();
    }

    private void createFins() {
        tailFin = new Triangle(new Point(x+(5*xRad)/6, y), new Point(x+xRad+(xRad/3), y+(yRad)),
                new Point(x+xRad+(xRad/3), y-(yRad)), ORANGE, ORANGE, 0.6f);
        sideFin = new Triangle(new Point(x-xRad/3.5f, y-yRad/7),
                new Point((x-xRad/3.5f)+(xRad/3), (y-yRad/7)+(2*yRad)/3),
                new Point((x-xRad/3.5f)+(xRad/3), (y-yRad/7)-(2*yRad)/3), ORANGE, Bubble.WHITE, 0.7f);
    }

    void reset() { // Reset fish to original values
        currentAngle = 0;
        fishDirection = Direction.LEFT;
        fishState = FishState.NORMAL;
        this.x = 0;
        this.y = 0;
        moving = false;
        dead = false;
        createFins();
    }

    void changeMovement(Direction direction) { // Change fishes direction
        fishDirection = direction;
        moving = !moving;
    }

    void updateState(FishState state) { fishState = state; }
    boolean isMoving() { return moving; }
    boolean isEaten() { return (y < Shark.UPPER_TEETH && y > Shark.LOWER_TEETH && x < EATEN_BOUND); }
    void kill() { dead = true; }
    boolean isAlive() { return dead; }

    void draw(GL2 gl) {
        if(!dead) {
            gl.glPushMatrix(); // Required to rotate only the fish
            currentAngle += fishState.angle;

            if(moving) { // Animate the fish moving
                Point temp = new Point(0, 0);
                if(addToX(fishDirection.speed * (float) Math.cos(Math.toRadians(currentAngle)))) {
                    temp.x = fishDirection.speed * (float) Math.cos(Math.toRadians(currentAngle));
                }
                if(addToY(fishDirection.speed  * (float) Math.sin(Math.toRadians(currentAngle)))) {
                    temp.y = fishDirection.speed  * (float) Math.sin(Math.toRadians(currentAngle));
                }
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

            // Move fins
            tailFin.moveBC(false, tailFinMovement);
            sideFin.moveBC(false, sideFinMovement);
            finMovementCount++;
            if(finMovementCount > 7) { // Switch which way the fins are moving
                tailFinMovement *= -1;
                sideFinMovement *= -1;
                finMovementCount = 0;
            }
            gl.glPopMatrix(); // Required to rotate only the fish
        }

    }

    private boolean addToY(float y) {
        if ((this.y + yRad)+ y <= Water.TIDE_HEIGHT && (this.y + yRad)+ y >= SAND_BOUND) {
            this.y += y;
            return true;
        } else return false;
    }

    private boolean addToX(float x) {
        if ((this.x - xRad)+ x >= -1f  && (this.x + xRad)+ x <= 1f) {
            this.x += x;
            return true;
        } else return false;
    }

    void drawEye(GL2 gl) {
        if(!dead) {
            gl.glPushMatrix(); // Required to rotate only the fish
                // Rotate
                gl.glTranslatef(x, y, 0);
                gl.glRotatef(currentAngle, 0, 0, 1.0f);
                gl.glTranslatef(-x, -y, 0);
                // Draw
                eye.draw(gl, x-(xRad/1.4f), y+(yRad/6));
                pupil.draw(gl, x-(xRad/1.4f), y+(yRad/6));
            gl.glPopMatrix(); // Required to rotate only the fish
        }
    }
}
