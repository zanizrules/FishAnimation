import com.jogamp.opengl.GL2;

import static com.jogamp.opengl.GL.GL_BLEND;
import static com.jogamp.opengl.GL.GL_SRC_ALPHA;

/**
 * Created by Shane on 13/03/2017.
 *
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
        gl.glEnable(GL_BLEND);
        gl.glBlendFunc(GL_SRC_ALPHA, GL2.GL_ONE_MINUS_SRC_ALPHA);

        gl.glLineWidth(8.0f);
        gl.glBegin(GL2.GL_LINE_STRIP);
            gl.glColor4f(WATER.red, WATER.green, WATER.blue, WATER.alpha);
            gl.glVertex2d(-1.0f, TIDE_HEIGHT);
            gl.glColor4f(WATER_2.red, WATER_2.green, WATER_2.blue, WATER_2.alpha);
            gl.glVertex2d(0.0f, TIDE_HEIGHT);
            gl.glColor4f(WATER.red, WATER.green, WATER.blue, WATER.alpha);
            gl.glVertex2d(1.0f, TIDE_HEIGHT);
        gl.glEnd();

        gl.glColor4f(WATER.red, WATER.green, WATER.blue, WATER.alpha);

        gl.glBegin(GL2.GL_POLYGON);
            gl.glVertex2d(-1.0f, TIDE_HEIGHT);
            gl.glVertex2d(-1.0f, -1.0f);
            gl.glVertex2d(1.0f, -1.0f);
            gl.glVertex2d(1.0f, TIDE_HEIGHT);

        // Moving water line start
            for(int i = 0; i < wavePointAmount; i++) {
                if(Float.compare(Math.abs(currentLine[i] - targetLine[i]), 0.0005f) <= 0) { // When wave reaches target aprox
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
