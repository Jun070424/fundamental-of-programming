/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.viva_q4;

/**
 *
 * @author Ryan
 */
import java.util.Scanner;

public class Viva_Q4 {

    public static void main(String[] args) {

        int n, b, t, count = 0;
        int capacity = 0;
        int maxPower = 0;

        double hub = 150.00;
        double delivered, drawn, power, time, cost;
        double totalEnergy = 0, totalRevenue = 0;
        double reached;

        String vehicle = "";

        boolean[] done = new boolean[5];

        boolean running = true;
        boolean depleted = false;

        Scanner enter = new Scanner(System.in);

        while (hub > 0 && running && !depleted) {

            System.out.println("\n===== Solar EV Charging Hub =====");
            System.out.printf("Hub Battery: %.2f kWh%n", hub);

            System.out.println("1. Compact Hatch (40 kWh, max 50 kW)"
                    + (done[1] ? " [DONE]" : ""));

            System.out.println("2. Family SUV (75 kWh, max 120 kW)"
                    + (done[2] ? " [DONE]" : ""));

            System.out.println("3. Delivery Van (90 kWh, max 80 kW)"
                    + (done[3] ? " [DONE]" : ""));

            System.out.println("4. E-Scooter (4 kWh, max 3 kW)"
                    + (done[4] ? " [DONE]" : ""));

            System.out.println("0. Close hub");

            System.out.print("Select a vehicle (0-4): ");

            // Validate integer input

            if (!enter.hasNextInt()) {

                System.out.println("Error: Please enter an integer from 0 to 4.");
                enter.next();
                continue;

            }

            n = enter.nextInt();

            // Validate vehicle selection

            if (n < 0 || n > 4) {

                System.out.println("Error: Invalid vehicle selection.");
                continue;

            }

            if (n == 0) {

                System.out.println("Hub closed by operator.");
                running = false;
                continue;

            }

            if (done[n]) {

                System.out.println("Error: This vehicle has already been charged.");
                continue;

            }

            // Vehicle selection

            switch (n) {

                case 1:
                    vehicle = "Compact Hatch";
                    capacity = 40;
                    maxPower = 50;
                    break;

                case 2:
                    vehicle = "Family SUV";
                    capacity = 75;
                    maxPower = 120;
                    break;

                case 3:
                    vehicle = "Delivery Van";
                    capacity = 90;
                    maxPower = 80;
                    break;

                case 4:
                    vehicle = "E-Scooter";
                    capacity = 4;
                    maxPower = 3;
                    break;

            }

            // Input current battery level

            do {

                System.out.print("Enter current battery level (0-99): ");
                b = enter.nextInt();

                if (b < 0 || b > 99) {

                    System.out.println("Error: Current level must be between 0 and 99.");

                }

            } while (b < 0 || b > 99);

            // Input target battery level

            do {

                System.out.printf("Enter target battery level (%d-100): ", b + 1);
                t = enter.nextInt();

                if (t <= b || t > 100) {

                    System.out.println("Error: Target must be higher than the current level and at most 100.");

                }

            } while (t <= b || t > 100);

            // Calculate charging power

            power = Math.min(maxPower, 60);

            // Calculate energy delivered

            delivered = capacity * (t - b) / 100.0;

            // Calculate energy drawn from hub

            drawn = delivered / 0.90;

            // Check whether hub has enough energy

            if (drawn > hub) {

                drawn = hub;

                delivered = drawn * 0.90;

                reached = b + (delivered / capacity) * 100;

                depleted = true;

            } else {

                reached = t;

            }

            // Calculate charging time

            time = (delivered / power) * 60;

            // Calculate charging cost

            cost = delivered * 1.20;

            if (delivered > 30) {

                cost = cost * 0.95;

            }

            // Update hub energy

            hub = hub - drawn;

            if (hub < 0.000001) {
                hub = 0;
            }

            // Update total statistics

            totalEnergy = totalEnergy + delivered;
            totalRevenue = totalRevenue + cost;

            count++;

            done[n] = true;

            // Charging summary

            System.out.println("\n--- Charging Summary ---");

            System.out.printf("Vehicle: %s%n", vehicle);

            System.out.printf("Battery: %d%% -> %d%%%n", b, t);
             
            System.out.printf("Charging Power: %.0f kW%n", power);

            System.out.printf("Energy Delivered: %.2f kWh (drawn from hub: %.2f kWh)%n",
                    delivered, drawn);

            System.out.printf("Charging Time: %.2f minutes%n", time);

            System.out.printf("Cost: RM %.2f%n", cost);

            if (depleted) {

                System.out.printf("Hub Battery Depleted! Vehicle only reached %.2f%%.%n",
                        reached);

            } else {

                System.out.printf("Remaining Hub Battery: %.2f kWh%n", hub);

            }

            System.out.println("------------------------");

        }

        // Final hub report

        System.out.println("\n===== Hub Report =====");

        System.out.printf("Vehicles Charged: %d%n", count);

        System.out.printf("Total Energy Delivered: %.2f kWh%n", totalEnergy);

        System.out.printf("Total Revenue: RM %.2f%n", totalRevenue);

        System.out.printf("Hub Battery Left: %.2f kWh%n", hub);

        enter.close();

    }

}
