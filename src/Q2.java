package viva1;

import java.util.Scanner;
public class Q2 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        // Step 1: Get the outstanding balance with input validation (RM 500 - RM 50,000)
        double balance = 0;
        do {
            System.out.print("Enter outstanding balance (RM): ");
            balance = sc.nextDouble();
            if (balance < 500 || balance > 50000) {
                System.out.println("Error: Balance must be between RM 500 and RM 50000.");
            }
        } while (balance < 500 || balance > 50000);

        // Step 2: Get the annual interest rate as a percentage
        System.out.print("Enter annual interest rate (%): ");
        double annualRate = sc.nextDouble();

        // Step 3: Convert the annual rate into a monthly rate
        double monthlyRate = (annualRate / 100) / 12;

        // Step 4: Get the planned monthly payment
        System.out.print("Enter planned monthly payment (RM): ");
        double plannedPayment = sc.nextDouble();

        // Step 5: Ask whether the user will take a payment holiday
        System.out.print("Take a payment holiday? (Y/N): ");
        char choice = sc.next().charAt(0);

        // Initialize holiday payment details with default values
        int holidayMonth = -1;
        if (choice == 'Y' || choice == 'y') {
            System.out.print("Enter holiday month (1-36): ");
            holidayMonth = sc.nextInt();
        }

        // Step 6: Prepare the variables used by the simulation
        double totalInterest = 0;
        double totalLateFees = 0;
        double totalAmountPaid = 0;
        int raisedCount = 0;
        double year1Balance = 0;
        double year2Balance = 0;
        double year3Balance = 0;
        boolean cleared = false;

        // Step 7: Simulate the loan for up to 36 months
        for (int month = 1; month <= 36; month++) {
            // At the start of each month, add the monthly interest
            double interest = balance * monthlyRate;
            balance += interest;
            totalInterest += interest;

            // Step 8: Check whether the payment holiday occurs this month
            if (month == holidayMonth) {
                balance += 25.00;
                totalLateFees += 25.00;
                System.out.println("Month " + month + ": Payment holiday - late fee RM 25.00 charged.");
            } else {
                // Step 9: Calculate minimum payment logic
                double minPayment = balance * 0.05;
                if (minPayment < 50.00) {
                    minPayment = 50.00;
                }

                // Determine actual payment amount to pay
                double paymentToMake = plannedPayment;
                if (plannedPayment < minPayment) {
                    paymentToMake = minPayment;
                    raisedCount++;
                }

                // Never pay more than the balance owed
                if (paymentToMake > balance) {
                    paymentToMake = balance;
                }

                // Make payment
                balance -= paymentToMake;
                totalAmountPaid += paymentToMake;
            }

            // Step 10: Check if the balance drops below RM 0.005 (cleared)
            if (balance < 0.005) {
                balance = 0.0;
                cleared = true;
                System.out.println("Debt fully cleared in Month " + month + "!");
                
                // Set remaining yearly balance markers if cleared earlier
                if (month <= 12 && year1Balance == 0) year1Balance = 0.0;
                if (month <= 24 && year2Balance == 0) year2Balance = 0.0;
                if (month <= 36 && year3Balance == 0) year3Balance = 0.0;
                
                // Record the balances for relevant year boundaries up to settlement month
                if (month == 12) year1Balance = 0.0;
                if (month == 24) year2Balance = 0.0;
                if (month == 36) year3Balance = 0.0;

                break;
            }

            // Step 11: Record yearly balances
            if (month == 12) {
                year1Balance = balance;
            }
            if (month == 24) {
                year2Balance = balance;
            }
            if (month == 36) {
                year3Balance = balance;
            }
        }

        // Step 12: Display the results matching the required output format
        System.out.printf("End of Year 1 Balance: RM %.2f%n", year1Balance);
        System.out.printf("End of Year 2 Balance: RM %.2f%n", year2Balance);
        System.out.printf("End of Year 3 Balance: RM %.2f%n", year3Balance);
        System.out.printf("Total Interest Charged: RM %.2f%n", totalInterest);
        System.out.printf("Total Late Fees: RM %.2f%n", totalLateFees);
        System.out.printf("Total Amount Paid: RM %.2f%n", totalAmountPaid);
        System.out.println("Months Raised to Minimum Payment: " + raisedCount);

        if (!cleared) {
            System.out.println("Debt not cleared after 36 months.");
        }
    }
}