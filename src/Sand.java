import com.jogamp.opengl.GL2;

/**
 * Created by Shane on 14/03/2017.
 * This class is used to draw the seabed in the fish tank.
 */
class Sand {
    private static final ColourRGB SAND = new ColourRGB(0.76f, 0.60f, 0.42f);
    private static final ColourRGB WHITE_SAND = new ColourRGB(0.85f, 0.75f, 0.63f);
    private static final float SAND_HEIGHT = -0.85f;
    private static final float[] HEIGHTS = {SAND_HEIGHT,SAND_HEIGHT +0.01f,SAND_HEIGHT-0.05f,SAND_HEIGHT-0.07f,
            SAND_HEIGHT+0.02f,SAND_HEIGHT-0.03f,SAND_HEIGHT+0.02f,SAND_HEIGHT+0.07f,SAND_HEIGHT+0.02f,SAND_HEIGHT};

    static void draw(GL2 gl) {
        gl.glBegin(GL2.GL_POLYGON);
            gl.glColor3d(SAND.red, SAND.green, SAND.blue);
            gl.glVertex2d(-1.0f, SAND_HEIGHT);
            gl.glVertex2d(-1.0f, -1.0f);
            gl.glVertex2d(1.0f, -1.0f);
            gl.glVertex2d(1.0f, SAND_HEIGHT);
            gl.glColor3d(WHITE_SAND.red, WHITE_SAND.green, WHITE_SAND.blue);
            for(int i = 0; i < 10; i++) {
                gl.glVertex2d(1.0f - (i*0.2f), HEIGHTS[i]);
            }
        gl.glEnd();
    }
}
