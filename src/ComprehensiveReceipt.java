/**
 * COPYRIGHT 2014 InfiniteLoops. All Rights Reserved.
 * Comment goes here
 * Solves CS151 Group Project
 * @author Mike Phe, Ly Dang, Andrew Yobs
 * @version 1.00 2014/10/30
 */

/**
 * Implements the Receipt interface, generating a comprehensive receipt that details the
 * user's stay and amount to pay in length.
 */
public class ComprehensiveReceipt implements Receipt
{
    private double total;
    private double subTotal;
    private int roomCost;
    private int days;
    private static double TAX = .0875;

    /**
     * Constructor for a comprehensive receipt, takes the cost of a room per night and the amount of
     * nights stayed.
     * @param roomCost Cost of the room per night.
     * @param days Number of nights stayed.
     */
    public ComprehensiveReceipt (int roomCost, int days)
    {
        this.roomCost = roomCost;
        this.days = days;
    }

    /**
     * Generates a formatted receipt in comprehensive form.
     * @return Comprehensive receipt.
     */
    public String toString()
    {
        String receipt = "";
        for (int i = 0; i < days; i++)
        {
            receipt += "1 Night: " + roomCost + ".\n";
        }
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
