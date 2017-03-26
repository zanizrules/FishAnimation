/**
 * Created by Shane on 13/03/2017.
 * The ColourRGB class allows for Colours to be defined and referenced and promotes avoiding magic numbers.
 */
class ColourRGB {
    float red, green, blue, alpha;

    ColourRGB(float r, float g, float b, float a) {
        red = r;
        green = g;
        blue = b;
        alpha = a;
    }
    ColourRGB(float r, float g, float b) {
        this(r,g,b, 1.0f);
    }
}
