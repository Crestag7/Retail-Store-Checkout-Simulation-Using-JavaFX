// RetailCheckoutSimulation.java
// Author: Aadarsha Shrestha
// Description: Simulates a retail checkout area with configurable lines and arrival rates.

import java.util.*;

public class RetailCheckoutSimulation {
    int SIMULATION_LENGTH = 43200; // 12 hours = 43200 seconds

    int linesOpen;       // Number of checkout lines
    int arrivalChance;   // How often customers arrive (in seconds)
    Random random;

    CheckoutLine[] lines;
    int[] idletime;         // Idle time for each worker
    int[] waittimeperline;  // Total wait time per line
    int[] customercountperline; // Customers per line
    int totalwaittime;      // Total wait time of all customers
    int totalcustomers;     // Total number of customers

    public RetailCheckoutSimulation(int linesOpen, int arrivalChance) {
        this.linesOpen = linesOpen;
        this.arrivalChance = arrivalChance;
        this.random = new Random();
        lines = new CheckoutLine[linesOpen];
        idletime = new int[linesOpen];
        waittimeperline = new int[linesOpen];
        customercountperline = new int[linesOpen];

        for (int i = 0; i < linesOpen; i++) {
            lines[i] = new CheckoutLine();
            idletime[i] = 0;
            waittimeperline[i] = 0;
            customercountperline[i] = 0;
        }

        totalwaittime = 0;
        totalcustomers = 0;
    }

    // Run the simulation
    public String runSimulation() {
        for (int second = 0; second < SIMULATION_LENGTH; second++) {
            // Randomly add a customer
            if (random.nextInt(arrivalChance) == 0) {
                int checkoutTime = getRandomCheckoutTime();
                Customer c = new Customer(second, checkoutTime);
                getShortestLine().addCustomer(c);
            }

            // Process each checkout line
            for (int i = 0; i < linesOpen; i++) {
                CheckoutLine line = lines[i];
                if (line.isBusy()) {
                    line.tick();  // Decrease current customer time
                } else if (!line.isEmpty()) {
                    Customer next = line.nextCustomer();
                    int wait = second - next.arrivalTime;
                    totalwaittime += wait;
                    waittimeperline[i] += wait;
                    customercountperline[i]++;
                    totalcustomers++;
                    line.startCheckout(next.checkoutTime);
                } else {
                    idletime[i]++;
                }
            }
        }

        return showResults();
    }

    // Find the line with the least customers
    private CheckoutLine getShortestLine() {
        CheckoutLine shortest = lines[0];
        for (CheckoutLine line : lines) {
            if (line.getSize() < shortest.getSize()) {
                shortest = line;
            }
        }
        return shortest;
    }

    // Choose random checkout time: 1min (20%), 2min (60%), or 5min (20%)
    private int getRandomCheckoutTime() {
        int roll = random.nextInt(100);
        if (roll < 20) return 60;
        if (roll < 80) return 120;
        return 300;
    }

    // Print results
    private String showResults() {
        StringBuilder result = new StringBuilder();
        result.append("Simulation Done!\n");

        int totalIdle = 0;
        for (int i = 0; i < linesOpen; i++) {
            result.append("Line ").append(i + 1).append(" Idle Time: ").append(idletime[i]).append(" seconds\n");
            totalIdle += idletime[i];

            double lineAvgWait = (customercountperline[i] > 0) ? (double) waittimeperline[i] / customercountperline[i] : 0;
            result.append(String.format("Line %d Average Wait Time: %.2f seconds\n", i + 1, lineAvgWait));
        }

        double averageWait = (totalcustomers > 0) ? (double) totalwaittime / totalcustomers : 0;
        result.append("\nTotal Customers: ").append(totalcustomers).append("\n");
        result.append("Total Worker Idle Time: ").append(totalIdle).append(" seconds\n");
        result.append(String.format("Overall Average Wait Time: %.2f seconds\n", averageWait));
        return result.toString();
    }
}
