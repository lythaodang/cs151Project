/**
 * COPYRIGHT 2014 InfiniteLoops. All Rights Reserved.
 * Comment goes here
 * Solves CS151 Group Project
 * @author Mike Phe, Ly Dang, Andrew Yobs
 * @version 1.00 2014/10/30
 */

/**
 * Implements the Receipt interface, generating a simple receipt that details the
 * user's stay and amount to pay in length.
 */
public class SimpleReceipt implements Receipt
{
    private double total;
    private double subTotal;
    private int roomCost;
    private int days;
    private static double TAX = .0875;

    /**
     * Constructor for a simple receipt, takes the cost of a room per night and the amount of
     * nights stayed.
     * @param roomCost Cost of the room per night.
     * @param days Number of nights stayed.
     */
    public SimpleReceipt (int roomCost, int days)
    {
        this.roomCost = roomCost;
        this.days = days;
    }

    /**
     * Generates a formatted receipt in simple form.
     * @return simple receipt.
     */
    public String toString()
    {
        String receipt = "";
        receipt += "Nights stayed: " + days + ".\n";
        subTotal = roomCost * days;
        receipt += "Subtotal: " + subTotal + ".\n";
        double tax = subTotal * TAX;
        receipt += "Tax: " + tax + ".\n";
        receipt += "---------------";
        total = subTotal + tax;
        receipt += "Total: " + total + ".\n";
        return receipt;
    }
}
