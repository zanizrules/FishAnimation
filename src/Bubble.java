import com.jogamp.opengl.GL2;

/**
 * Created by Shane on 14/03/2017.
 * This class represents the bubbles that float up from the round weed.
 */
class Bubble extends Circle {
    private static final ColourRGB BABY_BLUE = new ColourRGB(0.54f, 0.81f, 0.94f);
    static final ColourRGB WHITE = new ColourRGB(1, 1, 1);
    private final float speed;
    float x, y;

    Bubble(float transparency, float radius, float x, float y, float speed) {
        super(transparency, radius, BABY_BLUE, WHITE);
        this.x = x;
        this.y = y;
        this.speed = speed;
    }

    void draw(GL2 gl) {
        gl.glBegin(GL2.GL_TRIANGLE_FAN);
            super.draw(gl, x, y);
            this.y += speed;
            if(transparency > 0) {
                this.transparency -= speed/2;
            } else transparency = 0;
            if(radius > 0) {
                this.radius -= speed/50;
            } else radius = 0;
        gl.glEnd();
    }
}
