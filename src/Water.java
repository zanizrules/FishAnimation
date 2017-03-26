import com.jogamp.opengl.GL2;

import static com.jogamp.opengl.GL.GL_SRC_ALPHA;

/**
 * Created by Shane on 13/03/2017.
 * This class is used to draw the water in the tank and a animated waterline using the
 * rendering effects of the GL_POLYGON.
 */
class Water {
    private static final ColourRGB WATER = new ColourRGB(0.0f, 0.412f, 0.58f, 0.3f);
    private static final ColourRGB WATER_2 = new ColourRGB(0.0f, 0.2f, 0.6f, 0.3f);
    static final float TIDE_HEIGHT = 0.8f;
    private float[] waterLine;
    private float[] targetLine, currentLine;
    private float waterLevelChange;
    private final int wavePointAmount = 11;

    Water() {
        super();
        waterLevelChange = 0.0001f;
        waterLine = new float[wavePointAmount];
        currentLine = new float[wavePointAmount];
        targetLine = new float[wavePointAmount];
        for(int i = 0; i < wavePointAmount; i++) {
            currentLine[i] = waterLine[i] = TIDE_HEIGHT + (float) ((Math.sin((wavePointAmount)*(1.0f - (i*0.2f))))/48) +0.01f;
            targetLine[i] = TIDE_HEIGHT ;
        }
    }

    void draw(GL2 gl) {
        gl.glBlendFunc(GL_SRC_ALPHA, GL2.GL_ONE_MINUS_SRC_ALPHA);

        gl.glLineWidth(8.0f);
        gl.glBegin(GL2.GL_LINE_STRIP);
            gl.glColor4f(WATER.RED, WATER.GREEN, WATER.BLUE, WATER.ALPHA);
            gl.glVertex2d(-1.0f, TIDE_HEIGHT);
            gl.glColor4f(WATER_2.RED, WATER_2.GREEN, WATER_2.BLUE, WATER_2.ALPHA);
            gl.glVertex2d(0.0f, TIDE_HEIGHT);
            gl.glColor4f(WATER.RED, WATER.GREEN, WATER.BLUE, WATER.ALPHA);
            gl.glVertex2d(1.0f, TIDE_HEIGHT);
        gl.glEnd();

        gl.glColor4f(WATER.RED, WATER.GREEN, WATER.BLUE, WATER.ALPHA);

        gl.glBegin(GL2.GL_POLYGON);
            gl.glVertex2d(-1.0f, TIDE_HEIGHT);
            gl.glVertex2d(-1.0f, -1.0f);
            gl.glVertex2d(1.0f, -1.0f);
            gl.glVertex2d(1.0f, TIDE_HEIGHT);

            // Moving water line start
            for(int i = 0; i < wavePointAmount; i++) {
                if(Float.compare(Math.abs(currentLine[i] - targetLine[i]), 0.0005f) <= 0) { // When wave reaches target
                    float temp = waterLine[i];   // Switch target line with standard water line
                    waterLine[i] = targetLine[i];
                    targetLine[i] = temp;
                }
                if(Float.compare(currentLine[i], targetLine[i]) >= 0) {
                    waterLevelChange = -0.0002f;
                } else if(Float.compare(currentLine[i], targetLine[i]) <= 0) {
                    waterLevelChange = 0.0002f;
                }
                currentLine[i] += waterLevelChange;
                gl.glVertex2d(1.0f - (i*0.2f), currentLine[i]);
            }
        gl.glEnd();
    }
}
