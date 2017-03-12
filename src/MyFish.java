/**
 * Created by Shane Birdsall on 12/03/2017.
 *
 */
import com.jogamp.opengl.*;
import com.jogamp.opengl.awt.GLCanvas;
import com.jogamp.opengl.util.Animator;
import com.jogamp.opengl.util.FPSAnimator;
import com.jogamp.opengl.util.gl2.GLUT;

import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;


public class MyFish implements GLEventListener {

    private static int winSize;

    public static void main(String[] args) {

        // Get appropriate frame size based on users resolution
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        winSize = (int) screenSize.getWidth()/2;

        // Construct a java.awt.Frame (application main window)
        Frame frame = new Frame("My Fish");
        GLProfile profile = GLProfile.get(GLProfile.GL2);
        GLCapabilities capabilities = new GLCapabilities(profile);
        GLCanvas canvas = new GLCanvas(capabilities);

        MyFish fishAnimation = new MyFish();

        canvas.addGLEventListener(fishAnimation);
        frame.add(canvas);
        frame.setSize(winSize, winSize);

        final FPSAnimator animator = new FPSAnimator(canvas, 60);
        animator.start(); // Starts the animator which calls the display method

        frame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                // Run this on another thread than the AWT event queue to
                // make sure the call to Animator.stop() completes before
                // exiting
                new Thread(() -> {
                    animator.stop();
                    System.exit(0);
                }).start();
            }
        });
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        canvas.requestFocusInWindow();
    }

    @Override
    public void display(GLAutoDrawable drawable) {
        GL2 gl = drawable.getGL().getGL2();
        gl.glClear(GL2.GL_COLOR_BUFFER_BIT);

        gl.glPointSize(8.0f);

        Button b = new Button(gl,0.25f,0.075f,true);
        GLUT GLUT = new GLUT();
        for(int i = 0; i < 4; i++) {
            gl.glColor3f(.0f, .0f, .0f);
            b.Draw(-0.9425f+(i*(b.getWidth()+0.05f)), 0.8925f);

            gl.glColor3f(1.0f, 0.5f, 0.5f);
            b.Draw(-0.95f+(i*(b.getWidth()+0.05f)), 0.9f);
        }

        gl.glColor3f(0.0f, 0.0f, 0.0f);
        gl.glRasterPos2d(-0.92f, 0.925f);
        GLUT.glutBitmapString(GLUT.BITMAP_8_BY_13, "Bubbles");
        gl.glRasterPos2d(-0.62f, 0.925f);
        GLUT.glutBitmapString(GLUT.BITMAP_8_BY_13, "Time");
        gl.glRasterPos2d(-0.32f, 0.925f);
        GLUT.glutBitmapString(GLUT.BITMAP_8_BY_13, "Plankton");
        gl.glRasterPos2d(-0.02f, 0.925f);
        GLUT.glutBitmapString(GLUT.BITMAP_8_BY_13, "Diagnostics");


        gl.glFlush();
    }

    @Override
    public void dispose(GLAutoDrawable arg0) {}

    @Override
    public void init(GLAutoDrawable drawable) {
        GL2 gl = drawable.getGL().getGL2();
        gl.setSwapInterval(1);
        gl.glClearColor(1.0f, 1.0f, 1.0f, 1.0f);
        gl.glShadeModel(GL2.GL_SMOOTH);
    }

    @Override
    public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
        winSize = width;
    }
}