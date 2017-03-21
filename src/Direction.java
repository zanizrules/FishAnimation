/**
 * Created by Shane Birdsall on 21/03/2017.
 *
 */
enum Direction {
    LEFT(-0.005f), RIGHT(0.005f);

    float speed;
    Direction(float speed) {
        this.speed = speed;
    }
}
