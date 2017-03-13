/**
 * Created by Shane Birdsall on 12/03/2017.
 *
 */
import com.jogamp.opengl.*;
import com.jogamp.opengl.awt.GLCanvas;
import com.jogamp.opengl.util.FPSAnimator;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import static com.jogamp.opengl.GL2GL3.GL_POLYGON_SMOOTH;

public class MyFish implements GLEventListener, MouseListener {

    private static int winSize;

    // Colours
    private static final ColourRGB RED = new ColourRGB(0.55f, 0.0f, 0.0f);
    private static final ColourRGB GREEN = new ColourRGB(0.0f, 0.39f, 0.0f);

    // Button toggle booleans
    private boolean bubblesEnabled;

    // Fish tank objects/drawings
    private RoundWeed redWeed;
    private RoundWeed greenWeed;
    private Button bubbleBtn, buttons;
    private Water water;
    private Bubble bub;

    private MyFish() {
        super();
        bubbleBtn = new Button(0.25f,0.075f);
        buttons = new Button(0.25f,0.075f);
        redWeed = new RoundWeed(0.15f, 0.03f, RED);
        greenWeed = new RoundWeed(0.15f, 0.03f, GREEN);
        water = new Water();
        bub = new Bubble(0.5f, 0.05f, -0.8f,0.001f);
    }

    public static void main(String[] args) {

        // Get appropriate frame size based on users resolution
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        winSize = (int) screenSize.getWidth()/2;

        // Construct a java.awt.Frame (application main window)
        Frame frame = new Frame("My Fish");
        frame.setResizable(false);
        GLProfile profile = GLProfile.get(GLProfile.GL2);
        GLCapabilities capabilities = new GLCapabilities(profile);
        GLCanvas canvas = new GLCanvas(capabilities);

        MyFish fishAnimation = new MyFish();

        canvas.addGLEventListener(fishAnimation);
        canvas.addMouseListener(fishAnimation);

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

        // Draw buttons
        for(int i = 0; i < 4; i++) {
            gl.glColor3f(.0f, .0f, .0f); // Black
            // TODO: make it so buttons store x and y in the class

            buttons.draw(gl,-0.9425f+(i*(buttons.getWidth()+0.05f)), 0.8925f);
            if(bubblesEnabled) { // TODO: make this a bit better
                gl.glColor3f(0.7f, 0.38f, 0.0f); // Orange/Salmon
            } else {
                gl.glColor3f(1.0f, 0.5f, 0.0f); // Orange/Salmon
            }

            buttons.draw(gl, -0.95f+(i*(buttons.getWidth()+0.05f)), 0.9f);
        }

        // Button text
        gl.glColor3f(0.0f, 0.0f, 0.0f); // Black
        buttons.addText(gl, "Bubbles", -0.92f, 0.925f);
        buttons.addText(gl, "Time", -0.62f, 0.925f);
        buttons.addText(gl, "Plankton", -0.32f, 0.925f);
        buttons.addText(gl, "Diagnostics", -0.02f, 0.925f);


        //Draw Sand
        Sand.draw(gl);

        if(bubblesEnabled) {
            bub.draw(gl);
        }


        // Draw Round Weeds
        redWeed.draw(gl, -0.88f, -0.95f);
        greenWeed.draw(gl, -0.7f,-1.0f);



        // Draw water
        gl.glEnable(GL_POLYGON_SMOOTH);
        water.draw(gl);
        gl.glDisable(GL_POLYGON_SMOOTH);

        // bubbles test



        gl.glEnd();



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

    @Override
    public void mouseClicked(MouseEvent mouseEvent) {}
    @Override
    public void mousePressed(MouseEvent mouseEvent) {}

    @Override
    public void mouseReleased(MouseEvent e) {
        float openglX = 2.0f * ((float)e.getX() / winSize) - 1.0f;
        float openglY = 2.0f * ((winSize - (float)e.getY()) / winSize) - 1.0f;

        System.out.println("CLICK: " + openglX + "," + openglY);

        if(openglX >= -0.9425 && openglX <= (-0.9425 + bubbleBtn.getWidth()) && openglY >= 0.8925f && openglY <= 0.8925 + bubbleBtn.getHeight()) {
            bubblesEnabled = true;
        }
    }

    @Override
    public void mouseEntered(MouseEvent mouseEvent) {}
    @Override
    public void mouseExited(MouseEvent mouseEvent) {}
}