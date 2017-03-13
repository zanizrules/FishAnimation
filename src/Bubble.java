import com.jogamp.opengl.GL2;

/**
 * Created by Shane on 14/03/2017.
 *
 */
class Bubble {
    private static final ColourRGB BABY_BLUE = new ColourRGB(0.54f, 0.81f, 0.94f);
    private float transparency;
    private float radius;
    private float speed;
    private float x,y;

    Bubble(float transparency, float radius, float x, float speed) {
        super();
        this.transparency = transparency;
        this.radius = radius;
        this.speed = speed;
        y = -1.0f;
        this.x = x;
    }

    void draw(GL2 gl) {
        gl.glBegin(GL2.GL_TRIANGLE_FAN);
            gl.glColor4f(1.0f, 1.0f, 1.0f, transparency); // White
            gl.glVertex2d(x, y);
            gl.glColor4f(BABY_BLUE.red, BABY_BLUE.green, BABY_BLUE.blue, transparency);
            for(int i = 1; i < 360; i++) {
                gl.glVertex2d(x + (radius * Math.cos(i * (2*Math.PI)/50)), y + (((radius * Math.sin(i * (2*Math.PI) / 50)))));
            }
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
