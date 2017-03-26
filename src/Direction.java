/**
 * Created by Shane Birdsall on 21/03/2017.
 * The Direction enum class is used to define the SPEED at which the fish moves left and right.
 */
enum Direction {
    LEFT(-0.01f), RIGHT(0.01f);

    final float SPEED;
    Direction(float speed) {
        this.SPEED = speed;
    }
}
