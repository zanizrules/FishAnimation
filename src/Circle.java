import com.jogamp.opengl.*;
import com.jogamp.opengl.awt.GLCanvas;
import com.jogamp.opengl.util.Animator;

import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;


public class Circle implements GLEventListener {
	
	private static int winWidth = 1000;
	private static int winHeight = 1000;
	
	public static void main(String[] args) {
		// Construct a java.awt.Frame (application main window)
        Frame frame = new Frame("Draw circle");
		GLProfile profile = GLProfile.get(GLProfile.GL2);
        GLCapabilities capabilities = new GLCapabilities(profile);
        GLCanvas canvas = new GLCanvas(capabilities);
        Circle simple = new Circle();
        canvas.addGLEventListener(simple);
        frame.add(canvas);
        frame.setSize(winWidth, winHeight);
        final Animator animator = new Animator(canvas);
        
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
		 gl.glColor3f(0.5f, 0.5f, 0.5f);
		 
		 ShapeBuilder.DrawCircle(gl, 0, 0, 0.5f);
		  
		 gl.glFlush();
	}

	@Override
	public void dispose(GLAutoDrawable arg0) { }

	@Override
	public void init(GLAutoDrawable drawable) {
        GL2 gl = drawable.getGL().getGL2();
        gl.setSwapInterval(1);
        gl.glClearColor(1.0f, 1.0f, 1.0f, 1.0f);
        gl.glShadeModel(GL2.GL_SMOOTH); 
	}

	@Override
	public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
		height = (height == 0) ? 1 : height; // prevent divide by zero
		winWidth = width;
		winHeight = height;
	}
}