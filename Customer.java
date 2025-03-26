// This class stores information about each customer
public class Customer {
    int arrivalTime;     // Time when customer arrives at checkout
    int checkoutTime;    // Time needed to checkout

    // Constructor
    public Customer(int arrivalTime, int checkoutTime) {
        this.arrivalTime = arrivalTime;
        this.checkoutTime = checkoutTime;
    }
}
