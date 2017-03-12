import com.jogamp.opengl.GL2;

/* Author: Shane Birdsall
 * Version: 1.0
 * Include in future versions: 
 * - Square
 *
 */

public class ShapeBuilder {
	
	public static void DrawCircle(GL2 gl, float x, float y, float radius, boolean filled) {
		gl.glBegin(filled ? GL2.GL_TRIANGLE_FAN:GL2.GL_LINE_LOOP);
	 		// 0, 0 center
	 		for(int i = 0; i < 50; i++) {
	 			gl.glVertex2d(x + (radius * Math.cos(i * (2*Math.PI)/50)), y + (((radius * Math.sin(i * (2*Math.PI) / 50)))));
	 		}
		gl.glEnd();
	}
	public static void DrawCircle(GL2 gl, float x, float y, float radius) {
		DrawCircle(gl, x, y, radius, true);
	}
	public static void DrawCircle(GL2 gl, float radius) {
		DrawCircle(gl, 0, 0, radius, true);
	}
}
