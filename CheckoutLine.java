import java.util.LinkedList;
import java.util.Queue;

// This class manages a line (queue) of customers
public class CheckoutLine {
    Queue<Customer> queue;  // Queue to store customers
    int timeLeft;           // Time left for current customer checkout

    public CheckoutLine() {
        queue = new LinkedList<>();
        timeLeft = 0;
    }

    // Add a customer to the line
    public void addCustomer(Customer c) {
        queue.add(c);
    }

    // Remove and return the next customer
    public Customer nextCustomer() {
        return queue.poll();
    }

    // Check if the line is empty
    public boolean isEmpty() {
        return queue.isEmpty();
    }

    // Get number of people in line
    public int getSize() {
        return queue.size();
    }

    // Start checking out a customer
    public void startCheckout(int checkoutTime) {
        timeLeft = checkoutTime;
    }

    // Decrease time left for current checkout
    public void tick() {
        if (timeLeft > 0) {
            timeLeft--;
        }
    }

    // Check if someone is being checked out
    public boolean isBusy() {
        return timeLeft > 0;
    }
}
