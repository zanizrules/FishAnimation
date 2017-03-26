import com.jogamp.opengl.GL2;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * Created by Shane on 26/03/2017.
 * This class is responsible for the blood particles that are animated when the shark eats the fish.
 */
public class BloodParticleSystem {
    private static final ColourRGB BLOOD = new ColourRGB(0.6f, 0, 0);
    private static final int NUMBER_OF_PARTICLES = 300;
    private float xOrigin, yOrigin;
    private Queue<BloodParticle> particles;

    BloodParticleSystem(float x, float y) {
        particles = new ConcurrentLinkedQueue<>();
        xOrigin = x;
        yOrigin = y;
    }

    private void addParticle(double dx, double dy) {
        particles.add(new BloodParticle(xOrigin, yOrigin, dx, dy, 1.0f));
    }

    void createBloodParticles() {
        for (int i = 0; i < NUMBER_OF_PARTICLES; i++) {
            addParticle(Math.random() - 0.5, Math.random() - 0.5);
        }
    }

    void animate(double time) {
        for (BloodParticle p : particles) {
            p.transparency -= 0.0175f;

            // move the particles
            p.x += p.dx * time;
            p.y += p.dy * time;

            // if particle hits boundary or is fully transparent remove it
            if (Math.abs(p.x) > 1.0 || Math.abs(p.y) > 1.0 || p.transparency < 0) {
                particles.remove(p);
            }
        }
    }

    public void draw(GL2 gl) {
        gl.glPointSize(3f);
        gl.glBegin(GL2.GL_POINTS);
            for (BloodParticle p : particles) {
                gl.glColor4d(BLOOD.red/2,  BLOOD.green, BLOOD.blue, p.transparency/2);
                gl.glVertex2d(p.x, p.y);
                gl.glColor4d(BLOOD.red,  BLOOD.green, BLOOD.blue, p.transparency);
                gl.glVertex2d(p.x+0.001f, p.y+0.001f);
            }
        gl.glEnd();
    }

    public class BloodParticle {
        double x, y, dx, dy;
        float transparency;

        private BloodParticle(double x, double y, double dx, double dy, float alpha) {
            transparency = alpha;
            this.x = x;
            this.y = y;
            this.dx = dx;
            this.dy = dy;
        }
    }
}