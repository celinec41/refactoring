package theater;

/**
 * Represents a play with specified name and type.
 */
public final class Play {

    /** The name of the play. */
    private final String name;
    /** The type of the play. */
    private final String type;

    /**
     * Constructs the Play with specified name and type.
     * @param name the name of the play
     * @param type the type of the play
     */
    public Play(String name, String type) {
        this.name = name;
        this.type = type;
    }

    /**
     * Returns the name of the play.
     * @return the name of the play
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the type of the play.
     * @return the type of the play
     */
    public String getType() {
        return type;
    }
}
