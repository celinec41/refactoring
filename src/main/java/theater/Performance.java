package theater;

/**
 * Class representing a performance of a play..
 */
public final class Performance {

    /** The ID of the play being performed */
    private final String playID;

    /** The number of audiences attending the play*/
    private final int audience;

    /**
     * Constructs the performance with unique ID and number of audiences.
     * @param playID the ID of the play
     * @param audience the number of audiences attending the play
     */
    public Performance(String playID, int audience) {
        this.playID = playID;
        this.audience = audience;
    }

    /**
     * Returns the play ID for the performance
     * @return the ID of the play
     */
    public String getPlayID() {
        return playID;
    }

    /**
     * Returns the audience size of the play
     * @return the number of audiences attending the play
     */
    public int getAudience() {
        return audience;
    }
}
