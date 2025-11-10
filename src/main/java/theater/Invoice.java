package theater;

import java.util.List;

/**
 * Represent a customer's invoice containing a list of performances attended.
 * Class representing an invoice for a customer.
 */
public final class Invoice {

    /** The name of the customer for the invoice*/
    private final String customer;
    /** The list of performances in the invoice*/
    private final List<Performance> performances;

    /**
     * Creates the invoice list given the customer and performances
     * @param customer the name of the customer for the invoice
     * @param performances the list of the performances for specifies customer
     */
    public Invoice(String customer, List<Performance> performances) {
        this.customer = customer;
        this.performances = performances;
    }

    /**
     * Returns the names of the customer
     * @return the customer name
     */
    public String getCustomer() {
        return customer;
    }

    /**
     * Returns the list of performances
     * @return the list of performances
     */
    public List<Performance> getPerformances() {
        return performances;
    }
}
