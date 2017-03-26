/**
 * Created by Shane Birdsall on 21/03/2017.
 * The FishState enum class is used in setting specific fish states. The fish can either be in a normal state where it
 * is not rotating or it can be in a state in which it is rotating up or down.
 */
enum FishState {
    UP(-2f), DOWN(2f), NORMAL(0);

    float angle;

    FishState(float angle)  {
        this.angle = angle;
    }
}
