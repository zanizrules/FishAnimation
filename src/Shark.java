import com.jogamp.opengl.GL2;

/**
 * Created by Shane Birdsall on 19/03/2017.
 * The shark class contains all components used to draw the shark within the fish tank.
 * Point numbers written in this class refer to the points labelled on the diagram in my work log.
 */
class Shark {
    private static final ColourRGB DARK_GRAY = new ColourRGB(0.5f, 0.5f, 0.5f);
    private static final ColourRGB BLACK_GRAY = new ColourRGB(0.25f, 0.25f, 0.25f);
    private static final ColourRGB LIGHT_GRAY = new ColourRGB(0.8f, 0.8f, 0.8f);
    private static final ColourRGB TEETH = new ColourRGB(1f, 1f, 1f);
    static float UPPER_TEETH, LOWER_TEETH;
    private float x, y, sizeFactor, jawMovement;
    private Circle eye, pupil;
    private boolean jawClosed;

    Shark(float size, float x, float y) {
        this.x = x;
        this.y = y;
        sizeFactor = size;
        jawClosed = false;
        jawMovement = 0.1f;
        eye = new Circle(1.0f, size/3, Bubble.WHITE);
        pupil = new Circle(1.0f, size/5, new ColourRGB(0,0,0));
        UPPER_TEETH = y + (sizeFactor * 1.5f); // Point 3
        LOWER_TEETH = y + (sizeFactor*-1); // Point 5
    }

    void snap() { jawClosed = !jawClosed; }
    boolean isJawClosed() { return jawClosed; }

    void draw(GL2 gl) {
        gl.glColor3d(DARK_GRAY.RED, DARK_GRAY.GREEN, DARK_GRAY.BLUE);

        // Draw Head
        gl.glBegin(GL2.GL_TRIANGLE_FAN);
            gl.glVertex2d(x,y); // Origin Point

            gl.glColor3d(BLACK_GRAY.RED, BLACK_GRAY.GREEN, BLACK_GRAY.BLUE);
            gl.glVertex2d(x + (sizeFactor*1), y + (sizeFactor*3)); // Point 1

            gl.glColor3d(LIGHT_GRAY.RED, LIGHT_GRAY.GREEN, LIGHT_GRAY.BLUE);
            gl.glVertex2d(x + (sizeFactor*7.5), y + (sizeFactor*4) - (jawClosed?jawMovement:0)); // Point 2
            gl.glColor3d(DARK_GRAY.RED, DARK_GRAY.GREEN, DARK_GRAY.BLUE);

            gl.glVertex2d(x + (sizeFactor*6),y + (sizeFactor*1.5)- (jawClosed?jawMovement:0)); // Point 3

            gl.glColor3d(BLACK_GRAY.RED, BLACK_GRAY.GREEN, BLACK_GRAY.BLUE);
            gl.glVertex2d(x + (sizeFactor*2),y); // Point 4

            gl.glColor3d(LIGHT_GRAY.RED, LIGHT_GRAY.GREEN, LIGHT_GRAY.BLUE);
            gl.glVertex2d(x + (sizeFactor*6), y + (sizeFactor*-1) + (jawClosed?jawMovement:0)); // Point 5

            gl.glColor3d(BLACK_GRAY.RED, BLACK_GRAY.GREEN, BLACK_GRAY.BLUE);
            gl.glVertex2d(x + (sizeFactor*4), y + (sizeFactor*-2)); // Point 6
            gl.glVertex2d(x + (sizeFactor*2), y + (sizeFactor*-1.8)); // Point 7
        gl.glEnd();

        // Makes the blend between body and head a bit nicer
        gl.glLineWidth(5.0f);
        gl.glBegin(GL2.GL_LINE_STRIP);
            gl.glVertex2d(x + (sizeFactor*1), y + (sizeFactor*3)); // Point 1
            gl.glColor3d(DARK_GRAY.RED, DARK_GRAY.GREEN, DARK_GRAY.BLUE);
            gl.glVertex2d(x,y); // Origin Point
            gl.glColor3d(BLACK_GRAY.RED, BLACK_GRAY.GREEN, BLACK_GRAY.BLUE);
            gl.glVertex2d(x + (sizeFactor*2), y + (sizeFactor*-1.8)); // Point 7
        gl.glEnd();

        // Draw Body
        gl.glBegin(GL2.GL_TRIANGLE_FAN);
            gl.glColor3d(DARK_GRAY.RED, DARK_GRAY.GREEN, DARK_GRAY.BLUE);
            gl.glVertex2d(x,y); // Origin Point

            gl.glColor3d(BLACK_GRAY.RED, BLACK_GRAY.GREEN, BLACK_GRAY.BLUE);
            gl.glVertex2d(x + (sizeFactor*1), y + (sizeFactor*3)); // Point 1

            gl.glColor3d(BLACK_GRAY.RED, BLACK_GRAY.GREEN, BLACK_GRAY.BLUE);
            gl.glVertex2d(-1.0f, y + (sizeFactor*2.8)); // Point a

            gl.glColor3d(DARK_GRAY.RED, DARK_GRAY.GREEN, DARK_GRAY.BLUE);
            gl.glVertex2d(-1.0f, y + (sizeFactor*-2.2)); // Point b

            gl.glColor3d(BLACK_GRAY.RED, BLACK_GRAY.GREEN, BLACK_GRAY.BLUE);
            gl.glVertex2d(x + (sizeFactor*2), y + (sizeFactor*-1.8)); // Point 7
        gl.glEnd();

        // Draw Top Fin
        gl.glBegin(GL2.GL_POLYGON);
            gl.glVertex2d(x + (sizeFactor*1), y + (sizeFactor*3)); // Point 1
            gl.glColor3d(DARK_GRAY.RED, DARK_GRAY.GREEN, DARK_GRAY.BLUE);
            gl.glVertex2d(-1.0f, y + (sizeFactor*4.8)); // Point c
            gl.glColor3d(BLACK_GRAY.RED, BLACK_GRAY.GREEN, BLACK_GRAY.BLUE);
            gl.glVertex2d(-1.0f, y + (sizeFactor*2.8)); // Point a
        gl.glEnd();

        // Draw Bottom Fin
        gl.glBegin(GL2.GL_POLYGON);
            gl.glColor3d(BLACK_GRAY.RED, BLACK_GRAY.GREEN, BLACK_GRAY.BLUE);
            gl.glVertex2d(x + (sizeFactor*0.8f), y + (sizeFactor*-1.5)); // Point e
            gl.glColor3d(DARK_GRAY.RED, DARK_GRAY.GREEN, DARK_GRAY.BLUE);
            gl.glVertex2d(-1.0f, y + (sizeFactor*-3.5)); // Point d
            gl.glColor3d(BLACK_GRAY.RED, BLACK_GRAY.GREEN, BLACK_GRAY.BLUE);
            gl.glVertex2d(-1.0f, y + (sizeFactor*-1.2f)); // Point b
        gl.glEnd();

        // Line strip that makes the bottom fins connection look nicer
        gl.glBegin(GL2.GL_LINE_STRIP);
            gl.glColor3d(BLACK_GRAY.RED, BLACK_GRAY.GREEN, BLACK_GRAY.BLUE);
            gl.glVertex2d(x + (sizeFactor*0.8f), y + (sizeFactor*-1.5)); // Point e
            gl.glColor3d(DARK_GRAY.RED, DARK_GRAY.GREEN, DARK_GRAY.BLUE);
            gl.glVertex2d((x + (sizeFactor*0.8f)-1.0f)/2, y + (sizeFactor*-1.3)); // Mid Point (e, b)
            gl.glColor3d(BLACK_GRAY.RED, BLACK_GRAY.GREEN, BLACK_GRAY.BLUE);
            gl.glVertex2d(-1.0f, y + (sizeFactor*-1.2f)); // Point b
        gl.glEnd();
    }

    void drawEyeAndTeeth(GL2 gl) {
        eye.draw(gl, (x + ((sizeFactor*1.9f) + (sizeFactor*2.7f))/2), (y + ((sizeFactor*2.5f) + (sizeFactor*2.6f))/2)-0.4f*sizeFactor);
        pupil.draw(gl, (x + ((sizeFactor*1.9f) + (sizeFactor*2.7f))/2)+0.1f*sizeFactor, (y + ((sizeFactor*2.5f) + (sizeFactor*2.6f))/2)-0.4f*sizeFactor);

        // Draw Eyebrow
        gl.glLineWidth(6.5f);
        gl.glBegin(GL2.GL_LINE_STRIP);
            gl.glVertex2d(x + (sizeFactor * 1.9), y + (sizeFactor * 2.5)); // Point x
            gl.glVertex2d(x + (sizeFactor * 2.7), y + (sizeFactor * 2.4)); // Point y
        gl.glEnd();

        // Draw Teeth
        gl.glColor3d(TEETH.RED, TEETH.GREEN, TEETH.BLUE);
        if(jawClosed) { // Draw closed jaw
            gl.glBegin(GL2.GL_POLYGON);
                gl.glVertex2d(x + (sizeFactor * 6), y + (sizeFactor * 1.5) - jawMovement); // Point 3
                gl.glVertex2d(x + (sizeFactor*5.7), y + (sizeFactor*-1) + jawMovement);
                gl.glColor3d(BLACK_GRAY.RED, BLACK_GRAY.GREEN, BLACK_GRAY.BLUE);
                gl.glVertex2d(x + (sizeFactor * 2), y); // Point 4
            gl.glEnd();
        } else { // Draw open jaw
            gl.glBegin(GL2.GL_POLYGON);
                gl.glVertex2d(x + (sizeFactor * 6), y + (sizeFactor * 1.5)); // Point 3
                gl.glVertex2d(x + (sizeFactor * 5.8), y + (sizeFactor * (1.4)));
                gl.glVertex2d(x + (sizeFactor * 5.6), y + (sizeFactor * (0.8)));
                gl.glVertex2d(x + (sizeFactor * 5.4), y + sizeFactor);
                gl.glColor3d(BLACK_GRAY.RED, BLACK_GRAY.GREEN, BLACK_GRAY.BLUE);
                gl.glVertex2d(x + (sizeFactor * 2), y); // Point 4
            gl.glEnd();

            gl.glColor3d(TEETH.RED, TEETH.GREEN, TEETH.BLUE);
            gl.glBegin(GL2.GL_POLYGON);
                gl.glVertex2d(x + (sizeFactor*6), y + (sizeFactor*-1)); // Point 5
                gl.glVertex2d(x + (sizeFactor * 5.7), y + (sizeFactor * (-0.45)));
                gl.glVertex2d(x + (sizeFactor * 5.5), y - sizeFactor*1.1);
                gl.glColor3d(BLACK_GRAY.RED, BLACK_GRAY.GREEN, BLACK_GRAY.BLUE);
                gl.glVertex2d(x + (sizeFactor * 2), y); // Point 4
            gl.glEnd();
        }
    }
}
