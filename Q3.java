package viva1;

import java.util.Scanner;

public class Q3 { // WIX1002 Viva 1 Set B - Q3: Smart Courier Shipping Fee
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int parcelsProcessed = 0;
        double totalFees = 0.0;
        int heaviestKg = 0;

        while (true) {
            String adjustment = "None";

            // ---- Read the postcode ----
            System.out.print("Enter destination postcode (or EXIT to stop): ");
            String postcode = sc.nextLine();

            if (postcode.equalsIgnoreCase("EXIT")) {
                break;
            }

            // Normalise: remove spaces and hyphens, change lowercase letters to uppercase
            String cleanedPostcode = "";
            for (int i = 0; i < postcode.length(); i++) {
                char character = postcode.charAt(i);
                if (character == ' ' || character == '-') {
                    continue;
                }
                if (character >= 'a' && character <= 'z') {
                    character = (char) (character - 32);   // 'a' - 32 = 'A' (ASCII)
                }
                cleanedPostcode += character;
            }
            postcode = cleanedPostcode;

            // Remove a leading "MY" (Malaysia)
            if (postcode.length() >= 2 && postcode.charAt(0) == 'M' && postcode.charAt(1) == 'Y') {
                String postcodeWithoutMY = "";
                for (int i = 2; i < postcode.length(); i++) {
                    postcodeWithoutMY += postcode.charAt(i);
                }
                postcode = postcodeWithoutMY;
            }

            // ---- Find the zone ----
            String zone;
            boolean international = false;

            if (postcode.length() >= 2
                    && postcode.charAt(0) >= 'A' && postcode.charAt(0) <= 'Z'
                    && postcode.charAt(1) >= 'A' && postcode.charAt(1) <= 'Z') {
                // Starts with two letters = international, e.g. SG
                String countryCode = "" + postcode.charAt(0) + postcode.charAt(1);
                zone = "International (" + countryCode + ")";
                international = true;
            }
            else {
                // Malaysian postcode: exactly 5 digits
                boolean validPostcode = (postcode.length() == 5);
                for (int i = 0; i < postcode.length(); i++) {
                    char character = postcode.charAt(i);
                    if (character < '0' || character > '9') {
                        validPostcode = false;
                    }
                }

                int area = 0;
                if (validPostcode) {
                    area = (postcode.charAt(0) - '0') * 10 + (postcode.charAt(1) - '0');   // first two digits
                    validPostcode = (area >= 1 && area <= 98);
                }
                if (!validPostcode) {
                    System.out.println("Error: Invalid postcode. Parcel skipped.\n");
                    continue;   // skip this parcel, ask for the next postcode
                }

                if (area >= 50 && area <= 60) {
                    zone = "Klang Valley";
                }
                else if (area >= 61 && area <= 86 || area >= 1 && area <= 49) {
                    zone = "Peninsular";
                }
                else {
                    zone = "East Malaysia";
                }
            }

            // ---- Read the weight in grams (must be a positive integer) ----
            int weightGrams = 0;
            boolean validWeight = false;

            while (!validWeight) {
                System.out.print("Enter parcel weight (g): ");
                String weightText = sc.nextLine();

                // An integer has only digits, with an optional '-' in front
                boolean isInteger = (weightText.length() > 0);
                weightGrams = 0;
                for (int i = 0; i < weightText.length(); i++) {
                    char character = weightText.charAt(i);
                    if (character >= '0' && character <= '9') {
                        weightGrams = weightGrams * 10 + (character - '0');   // build the number digit by digit
                    }
                    else if (!(i == 0 && character == '-' && weightText.length() > 1)) {
                        isInteger = false;
                    }
                }

                if (!isInteger) {
                    System.out.println("Error: Weight must be an integer.");
                }
                else if (weightText.charAt(0) == '-' || weightGrams == 0) {
                    System.out.println("Error: Weight must be greater than 0.");
                }
                else {
                    validWeight = true;
                }
            }

            // ---- Read the dimensions LxWxH ----
            int length = 0;
            int width = 0;
            int height = 0;
            boolean validDimensions = false;

            while (!validDimensions) {
                System.out.print("Enter dimensions LxWxH (cm): ");
                String dimensions = sc.nextLine();

                int numbersRead = 0;     // how many numbers are finished: 0 = none, 1 = length, 2 = length and width
                int currentNumber = 0;   // the number being built
                int digitCount = 0;      // how many digits the current number has
                boolean correctFormat = true;

                for (int i = 0; i < dimensions.length(); i++) {
                    char character = dimensions.charAt(i);
                    if (character >= '0' && character <= '9') {
                        currentNumber = currentNumber * 10 + (character - '0');
                        digitCount++;
                    }
                    else if ((character == 'x' || character == 'X') && digitCount > 0 && numbersRead < 2) {
                        if (numbersRead == 0)
                            length = currentNumber;
                        else
                            width = currentNumber; //endif
                        numbersRead++;
                        currentNumber = 0;
                        digitCount = 0;
                    }
                    else {
                        correctFormat = false;   // any other character, or an x in the wrong place
                    }
                }

                if (correctFormat && numbersRead == 2 && digitCount > 0) {
                    height = currentNumber;
                    if (length > 0 && width > 0 && height > 0) {
                        validDimensions = true;
                    }
                }
                if (!validDimensions) {
                    System.out.println("Error: Use the format LxWxH with positive numbers, e.g. 30x20x10.");
                }
            }

            // ---- Chargeable weight = the bigger of actual and volumetric, rounded UP ----
            double actualKg = weightGrams / 1000.0;
            double volumetricKg = ((double) length * width * height) / 5000.0;

            double biggerKg = actualKg;
            if (volumetricKg > biggerKg) {
                biggerKg = volumetricKg;
            }
            int chargeableKg = (int) biggerKg;   // casting cuts off the decimals
            if (biggerKg > chargeableKg) {
                chargeableKg++;                  // there were decimals, so round up
            }

            // ---- Fee ----
            double fee;
            if (international) {
                fee = chargeableKg * 35.00;
            }
            else {
                double firstKgFee, additionalKgFee;
                if (zone.equals("Klang Valley")) {
                    firstKgFee = 6.00;
                    additionalKgFee = 1.50;
                }
                else if (zone.equals("Peninsular")) {
                    firstKgFee = 8.00;
                    additionalKgFee = 2.00;
                }
                else {
                    firstKgFee = 14.00;
                    additionalKgFee = 4.50;
                }
                fee = firstKgFee + (chargeableKg - 1) * additionalKgFee;
            }

            // Discount or surcharge based on chargeable weight
            if (chargeableKg >= 5 && chargeableKg <= 10) {
                fee = fee * 0.95;
                adjustment = "5% bulk discount";
            }
            else if (chargeableKg > 10) {
                fee = fee * 1.15;
                adjustment = "15% heavy parcel surcharge";
            }

            parcelsProcessed++;
            totalFees += fee;
            if (chargeableKg > heaviestKg) {
                heaviestKg = chargeableKg;
            }

            System.out.println("\nShipping Summary");
            System.out.println("Postcode: " + postcode);
            System.out.println("Zone: " + zone);
            System.out.printf("Actual Weight: %.2f kg | Volumetric Weight: %.2f kg%n", actualKg, volumetricKg);
            System.out.println("Chargeable Weight: " + chargeableKg + " kg");
            System.out.println("Adjustment: " + adjustment);
            System.out.printf("Shipping Fee: RM %.2f%n%n", fee);
        }

        System.out.println("Parcels Processed: " + parcelsProcessed);
        System.out.printf("Total Shipping Fees: RM %.2f%n", totalFees);
        System.out.println("Heaviest Chargeable Weight: " + heaviestKg + " kg");
    }
}
