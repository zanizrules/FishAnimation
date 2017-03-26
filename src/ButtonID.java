import java.util.HashMap;

/**
 * Created by Shane Birdsall on 19/03/2017.
 * The ButtonID enum class is used to store all of the different buttons and link them to a numerical ID
 * and a String text representation.
 */
enum ButtonID {
    BUBBLES(0), TIME(1), PLANKTON(2), SHARK(3), RESET(4);

    static HashMap<Integer, String> TEXT_DESCRIPTIONS = null;
    final int ID;

    static void init() {
        if(TEXT_DESCRIPTIONS == null) {
            TEXT_DESCRIPTIONS = new HashMap<>();
            TEXT_DESCRIPTIONS.put(0, "Bubbles");
            TEXT_DESCRIPTIONS.put(1, "Time");
            TEXT_DESCRIPTIONS.put(2, "Plankton");
            TEXT_DESCRIPTIONS.put(3, "Snap Jaw");
            TEXT_DESCRIPTIONS.put(4, "Reset Fish");
        }
    }

    ButtonID(int id) { this.ID = id; }
}
