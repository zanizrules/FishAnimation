/**
 * Created by Shane Birdsall on 21/03/2017.
 *
 */
enum FishState {
    UP(-2f), DOWN(2f), NORMAL(0);

    float angle;

    FishState(float angle)  {
        this.angle = angle;
    }
}
