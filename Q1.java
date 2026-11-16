package viva1;

import java.util.Scanner;
public class Q1 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        // Read the original phrase
        // nextLine() allows the phrase to contain spaces
        System.out.print("Enter Original Phrase: ");
        String phrase = sc.nextLine();

        // Calculate the shift key from digits in the phrase
        int digitSum = 0;
        for (int i = 0; i < phrase.length(); i++) {
            // Loop through every character in the phrase to locate digits
            char ch = phrase.charAt(i);
            
            // Check if the character is a numeric digit ('0' to '9')
            if (ch >= '0' && ch <= '9') {
                // Convert the character digit to its integer value using ASCII subtraction
                digitSum += ch - '0';
            }
        }

        // Rule 1: Shift Key = sum % 26 (If key is 0, use 13 instead)
        int key = digitSum % 26;
        if (key == 0) {
            key = 13;
        }

        // Define string encoded and symbol
        String encoded = "";

        // Examine each character in the original phrase
        for (int i = 0; i < phrase.length(); i++) {
            char ch = phrase.charAt(i);

            // Rule 2: Shift lowercase letters forward by the key (circular)
            if (ch >= 'a' && ch <= 'z') {
                // Normalize to 0-25 index, add key, wrap around with % 26, and convert back to char
                char converted = (char) ('a' + (ch - 'a' + key) % 26);
                encoded += converted;
            }
            
            // Rule 3: Process uppercase letters (vowels vs consonants)
            else if (ch >= 'A' && ch <= 'Z') {
                if (ch == 'A' || ch == 'E' || ch == 'I' || ch == 'O' || ch == 'U') {
                    // Uppercase vowels become '*' followed by their lowercase version (+32 in ASCII)
                    encoded += "*" + (char) (ch + 32);
                } else {
                    // Uppercase consonants become the next letter circularly in lowercase
                    encoded += (char) ('a' + (ch - 'A' + 1) % 26);
                }
            }
            
            // Rule 4: Replace digit d with (d * d) % 10
            else if (ch >= '0' && ch <= '9') {
                int d = ch - '0';
                int newDigit = (d * d) % 10;
                encoded += newDigit;
            }
            
            // Rule 5: Replace spaces with a dot
            else if (ch == ' ') {
                encoded += '.';
            }
            
            // Rule 6: Move 2 places forward in circular sequence !@#$%
            else {
                // Move 2 places forward in ! @ # $ % (circular)
                switch (ch) {
                    case '!': 
                        encoded += '#'; 
                        break;
                    case '@': 
                        encoded += '$'; 
                        break;
                    case '#': 
                        encoded += '%'; 
                        break;
                    case '$': 
                        encoded += '!'; 
                        break;
                    case '%': 
                        encoded += '@'; 
                        break;
                    default:  
                        encoded += ch;   // any other character is left unchanged
                }
            }
        }

        // Save the string before reversal
        String beforeReversal = encoded;

        // Rule 7: Reverse the whole encoded string using a loop
        String finalPassword = "";
        for (int i = encoded.length() - 1; i >= 0; i--) {
            finalPassword += encoded.charAt(i);
        }

        // Print the final outputs in exact format
        System.out.println("Shift Key: " + key);
        System.out.println("Before Reversal: " + beforeReversal);
        System.out.println("Encoded Password: " + finalPassword);
    }
}
