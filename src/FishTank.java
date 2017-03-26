import com.jogamp.opengl.*;
import com.jogamp.opengl.awt.GLCanvas;
import com.jogamp.opengl.util.FPSAnimator;

import java.awt.*;
import java.awt.event.*;
import java.util.Queue;
import java.util.Random;
import java.util.concurrent.ConcurrentLinkedQueue;

import static com.jogamp.opengl.GL.GL_BLEND;
import static com.jogamp.opengl.GL2GL3.GL_POLYGON_SMOOTH;

/**
 * Created by Shane Birdsall on 12/03/2017.
 * The FishTank class is responsible for animating all of the objects withing the fish tank.
 */
class FishTank implements GLEventListener, MouseListener, KeyListener, Runnable {
    private static int winSize;
    private static final int MAX_BUBBLE_AMOUNT = 20;
    private static final int NUM_OF_BUTTONS = 5;
    private static final Random rand = new Random();

    // Colours
    private static final ColourRGB RED = new ColourRGB(0.55f, 0.0f, 0.0f);
    private static final ColourRGB GREEN = new ColourRGB(0.0f, 0.39f, 0.0f);
    private static final ColourRGB BUTTON_SHADOW = new ColourRGB(0.2f, 0.2f, 0.2f);
    private static final ColourRGB BUTTON_COLOUR = new ColourRGB(0.83f, 0.83f, 0.83f);
    private static final ColourRGB BUTTON_CLICKED = new ColourRGB(0.70f, 0.70f, 0.70f);

    // Buttons
    private Button[] buttons;

    private double prevTick; // Used for blood particle system

    // Fish tank objects/drawings
    private TimeMask timeMask;
    private RoundWeed redWeed;
    private RoundWeed greenWeed;
    private Water water;
    private Fish fish;
    private Shark shark;
    private Queue<Bubble> bubbles;
    private Plankton[] plankton;
    private BloodParticleSystem particleSystem;

    private FishTank() {
        super();
        ButtonID.init();
        buttons = new Button[NUM_OF_BUTTONS];
        for(int i = 0; i < NUM_OF_BUTTONS; i++) {
            buttons[i] = new Button(0.25f, 0.075f);
        }

        redWeed = new RoundWeed(0.15f, 0.03f, RED);
        greenWeed = new RoundWeed(0.15f, 0.03f, GREEN);
        water = new Water();
        fish = new Fish(0.125f, 0.05f, 0, 0);
        shark = new Shark(0.1f, -0.95f, 0);
        bubbles = new ConcurrentLinkedQueue<>();
        plankton = new Plankton[Plankton.MAX_PLANKTON];
        for(int i = 0; i < Plankton.MAX_PLANKTON; i++) {
            plankton[i] = new Plankton();
        }

        timeMask = new TimeMask();
        particleSystem = new BloodParticleSystem(-0.35f, 0.05f);
    }

    public static void main(String[] args) {
        // Get appropriate frame size based on users resolution
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        winSize = (int) screenSize.getWidth()/2;

        // Construct a java.awt.Frame (application main window)
        Frame frame = new Frame("Shane's Fish Animation Game");
        frame.setResizable(false);
        GLProfile profile = GLProfile.get(GLProfile.GL2);
        GLCapabilities capabilities = new GLCapabilities(profile);
        GLCanvas canvas = new GLCanvas(capabilities);

        FishTank fishAnimation = new FishTank();

        canvas.addGLEventListener(fishAnimation);
        canvas.addMouseListener(fishAnimation);
        canvas.addKeyListener(fishAnimation);

        frame.add(canvas);
        frame.setSize(winSize, winSize);

        final FPSAnimator animator = new FPSAnimator(canvas, 60);
        animator.start(); // Starts the animator which calls the display method

        frame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
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
        gl.glEnable(GL_BLEND);

        //Draw Sand
        Sand.draw(gl);

        // Draw bubbles
        if(buttons[ButtonID.BUBBLES.ID].isEnabled()) {
            for(Bubble bub : bubbles) {
                bub.draw(gl);
            }
        }

        // Draw Round Weeds
        redWeed.draw(gl, -0.88f, -0.95f);
        redWeed.draw(gl, -0.65f, -0.97f);
        greenWeed.draw(gl, -0.7f,-1.0f);
        greenWeed.draw(gl, -0.45f,-1.05f);

        // Draw water
        gl.glEnable(GL_POLYGON_SMOOTH);
        water.draw(gl);
        gl.glDisable(GL_POLYGON_SMOOTH);

        // Draw blood
        double tick = System.currentTimeMillis() / 1000.0; // time since last frame
        if (prevTick > 0) {
            // animate the particle system
            particleSystem.animate(tick - prevTick);
        }
        prevTick = tick;
        particleSystem.draw(gl);

        // Draw Fish
        fish.draw(gl);

        // Draw night mask
        if(buttons[ButtonID.TIME.ID].isEnabled()) {
            timeMask.draw(gl);
            if(buttons[ButtonID.PLANKTON.ID].isEnabled()) {
                for(Plankton p : plankton) {
                    p.draw(gl, timeMask.getTransparency());
                }
            }
        }

        // Draw Fish Eye & Shark
        fish.drawEye(gl);
        shark.draw(gl);
        shark.drawEyeAndTeeth(gl);

        // Draw buttons
        for(int i = 0; i < NUM_OF_BUTTONS; i++) {
            gl.glColor3f(BUTTON_SHADOW.RED, BUTTON_SHADOW.GREEN, BUTTON_SHADOW.BLUE);
            // Draw shadow
            buttons[i].draw(gl,-0.9425f+(i*(buttons[i].getButtonWidth()+0.05f)), 0.8925f);
            if(buttons[i].isEnabled()) { // Button enabled
                gl.glColor3f(BUTTON_CLICKED.RED, BUTTON_CLICKED.GREEN, BUTTON_CLICKED.BLUE);
            } else { // Button not enabled
                gl.glColor3f(BUTTON_COLOUR.RED, BUTTON_COLOUR.GREEN, BUTTON_COLOUR.RED);
            }
            // Draw Button
            buttons[i].draw(gl, -0.95f+(i*(buttons[i].getButtonWidth()+0.05f)), 0.9f);

            // Button text
            gl.glColor3f(BUTTON_SHADOW.RED, BUTTON_SHADOW.GREEN, BUTTON_SHADOW.BLUE); // text same colour as button shadow
            buttons[i].addText(gl, ButtonID.TEXT_DESCRIPTIONS.get(i), -0.92f+(i*0.3f), 0.925f);
        }

        gl.glEnd();
        gl.glFlush();
    }

    @Override
    public void run() {
        while(buttons[ButtonID.BUBBLES.ID].isEnabled()) {
            if(bubbles.size() < MAX_BUBBLE_AMOUNT) {
                // Generate a random bubble
                float trans = rand.nextFloat() * (0.9f) + 0.1f;
                float rad = rand.nextFloat() * (0.06f - 0.01f) + 0.01f;
                float x = rand.nextFloat() * (0.6f) + (-0.95f);
                float y = (0.35f) * rand.nextFloat() -1.1f;
                float speed = rand.nextFloat() * (0.01f - 0.001f) + 0.001f;

                bubbles.add(new Bubble(trans, rad, x, y, speed));
            }
            for(Bubble bub : bubbles) {
                if(bub.y > Water.TIDE_HEIGHT) {
                    bub.y = -1.1f;
                }
                if(bub.transparency < 0.001f || bub.radius < 0.001f) {
                    bubbles.remove(bub);
                }
            }
            try {
                Thread.sleep(100);
            } catch (InterruptedException ignored) {}
        }
    }

    @Override
    public void dispose(GLAutoDrawable notUsed) {}
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
    public void mouseClicked(MouseEvent notUsed) {}
    @Override
    public void mousePressed(MouseEvent notUsed) {}
    @Override
    public void mouseReleased(MouseEvent e) { // Used for checking button clicks
        float openglX = 2.0f * ((float)e.getX() / winSize) - 1.0f;
        float openglY = 2.0f * ((winSize - (float)e.getY()) / winSize) - 1.0f;

        for(int i = 0; i < NUM_OF_BUTTONS; i++) {
            if(openglX >= (-0.9425) + (i*(buttons[i].getButtonWidth()+0.05f)) // If within bounds of the buttons[i]
                    && openglX <= ((-0.9425) + (i*(buttons[i].getButtonWidth()+0.05f)) + buttons[i].getButtonWidth())
                    && openglY >= 0.8925f && openglY <= 0.8925 + buttons[i].getButtonHeight()) {
                buttons[i].click(); // enable button
                if(i == ButtonID.SHARK.ID) {
                    shark.snap();
                    eatFish();
                }
                if(i == ButtonID.RESET.ID) {
                    fish.reset();
                }
                if(buttons[i].isEnabled()) {
                    if (i == ButtonID.BUBBLES.ID) {
                        // start generating bubbles. Thread stops when button clicked again
                        new Thread(this).start();
                    } else if (i == ButtonID.TIME.ID) {
                        timeMask.reset();
                    }
                }
            }
        }
    }
    private void eatFish() {
        if(shark.isJawClosed() && fish.isEaten()) { //Check if shark ate fish
            if(!fish.isAlive()) {
                particleSystem.createBloodParticles();
                fish.kill();
            }
        }
    }
    @Override
    public void mouseEntered(MouseEvent notUsed) {}
    @Override
    public void mouseExited(MouseEvent notUsed) {}
    @Override
    public void keyTyped(KeyEvent notUsed) {}
    @Override
    public void keyPressed(KeyEvent event) {
        // Movement of Fish
        if(!fish.isMoving()) {
            if(event.getKeyCode() == KeyEvent.VK_LEFT) {
                fish.changeMovement(Direction.LEFT);
            } else if(event.getKeyCode() == KeyEvent.VK_RIGHT) {
                fish.changeMovement(Direction.RIGHT);
            }
        }
        // Rotation of fish
        if(event.getKeyCode() == KeyEvent.VK_DOWN) {
            fish.updateState(FishState.DOWN);
        } else if(event.getKeyCode() == KeyEvent.VK_UP) {
            fish.updateState(FishState.UP);
        }
    }
    @Override
    public void keyReleased(KeyEvent event) {
        if(event.getKeyCode() == KeyEvent.VK_SPACE) { // snap sharks jaw
            shark.snap();
            buttons[ButtonID.SHARK.ID].click();
            eatFish();
        } else if(event.getKeyCode() == KeyEvent.VK_LEFT) {
            fish.changeMovement(Direction.LEFT);
        } else if(event.getKeyCode() == KeyEvent.VK_RIGHT) {
            fish.changeMovement(Direction.RIGHT);
        }  else if(event.getKeyCode() == KeyEvent.VK_DOWN || event.getKeyCode() == KeyEvent.VK_UP) {
            fish.updateState(FishState.NORMAL); // Stop changing angle
        }
    }
}
