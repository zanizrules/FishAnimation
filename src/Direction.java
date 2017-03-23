/**
 * Created by Shane Birdsall on 21/03/2017.
 *
 */
enum Direction {
    LEFT(-0.01f), RIGHT(0.01f);

    float speed;
    Direction(float speed) {
        this.speed = speed;
    }
}
