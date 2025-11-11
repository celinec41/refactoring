package theater;

import java.text.NumberFormat;
import java.util.Locale;
import java.util.Map;

/**
 * This class generates a statement for a given invoice of performances.
 */
public final class StatementPrinter {
    private final Invoice invoice;
    private final Map<String, Play> plays;

    /**
     * Constructs a StatementPrinter with specified invoice and map.
     * @param invoice the invoice to print
     * @param plays a map from play ID to Play
     */
    public StatementPrinter(Invoice invoice, Map<String, Play> plays) {
        this.invoice = invoice;
        this.plays = plays;
    }

    /**
     * Returns a formatted statement of the invoice associated with this printer.
     * @return the formatted statement
     * @throws RuntimeException if one of the play types is not known
     */
    public String statement() {

        final StringBuilder result = new StringBuilder(
                "Statement for " + invoice.getCustomer() + System.lineSeparator()
        );
        final int totalAmount = getTotalAmount();
        final int volumeCredits = getTotalVolumeCredits();
        for (final Performance performance : invoice.getPerformances()) {
            // print line for this order
            result.append(String.format(
                    "  %s: %s (%s seats)%n",
                    getPlay(performance).getName(),
                    usd(getAmount(performance)),
                    performance.getAudience()));
        }
        result.append(String.format(
                "Amount owed is %s%n",
                usd(totalAmount)));
        result.append(String.format(
                "You earned %s credits%n",
                volumeCredits));
        return result.toString();
    }

    private int getTotalVolumeCredits() {
        int volumeCredits = 0;
        for (final Performance performance : invoice.getPerformances()) {
            volumeCredits = getVolumeCredits(performance, volumeCredits);
        }
        return volumeCredits;
    }

    private String getFormat(Performance performance) {
        return NumberFormat.getCurrencyInstance(Locale.US).format(getAmount(performance) / Constants.PERCENT_FACTOR);
    }

    private static String usd(int totalAmount) {
        return NumberFormat.getCurrencyInstance(Locale.US).format(totalAmount / Constants.PERCENT_FACTOR);
    }

    private int getTotalAmount() {
        int totalAmount = 0;
        for (final Performance performance : invoice.getPerformances()) {
            totalAmount += getAmount(performance);
        }
        return totalAmount;
    }

    private int getVolumeCredits(Performance performance, int credits) {
        int totalCredits = credits;
        totalCredits += Math.max(
                performance.getAudience() - Constants.BASE_VOLUME_CREDIT_THRESHOLD, 0);
        if ("comedy".equals(getPlay(performance).getType())) {
            totalCredits += performance.getAudience() / Constants.COMEDY_EXTRA_VOLUME_FACTOR;
        }
        return totalCredits;
    }

    private Play getPlay(Performance performance) {
        return plays.get(performance.getPlayID());
    }

    /**
     * Return the amount for the given performance and play.
     *
     * @param performance the performance to charge for
     * @return the amounts need to be charged for the performance
     */
    private int getAmount(Performance performance) {
        return getThisAmount(performance, getPlay(performance));
    }

    private int getThisAmount(Performance performance, Play play) {
        int result;
        switch (getPlay(performance).getType()) {
            case "tragedy":
                result = Constants.TRAGEDY_BASE_AMOUNT;
                if (performance.getAudience() > Constants.TRAGEDY_AUDIENCE_THRESHOLD) {
                    result += Constants.TRAGEDY_OVER_BASE_CAPACITY_PER_PERSON * (performance.getAudience()
                            - Constants.TRAGEDY_AUDIENCE_THRESHOLD);
                }
                break;
            case "comedy":
                result = Constants.COMEDY_BASE_AMOUNT;
                if (performance.getAudience() > Constants.COMEDY_AUDIENCE_THRESHOLD) {
                    result += Constants.COMEDY_OVER_BASE_CAPACITY_AMOUNT
                            + (Constants.COMEDY_OVER_BASE_CAPACITY_PER_PERSON
                            * (performance.getAudience()
                            - Constants.COMEDY_AUDIENCE_THRESHOLD));
                }
                result += Constants.COMEDY_AMOUNT_PER_AUDIENCE * performance.getAudience();
                break;
            default:
                throw new RuntimeException(String.format("unknown type: %s", getPlay(performance).getType()));
        }
        return result;
    }
}
