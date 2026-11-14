package viva1;

import java.util.Scanner;

public class Q5 { // WIX1002 Viva 1 Set B - Q5: Rangoli Star Frame
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int X, Y, B;

        // 1. Input validation: keep asking for all three until every rule passes
        while (true) {
            System.out.print("Enter width X: ");
            X = sc.nextInt();
            System.out.print("Enter height Y: ");
            Y = sc.nextInt();
            System.out.print("Enter block size B: ");
            B = sc.nextInt();

            boolean xOk = X >= 7 && X <= 29 && X % 2 == 1;
            boolean yOk = Y >= 7 && Y <= 29 && Y % 2 == 1;
            boolean bOk = B >= 1 && B <= 3;
            if (xOk && yOk && bOk) {
                break;
            }
            System.out.println("Error: X and Y must be odd numbers between 7 and 29, and B must be between 1 and 3.");
        }

        // 2. Centre position (integer division)
        int centreRow = Y / 2;
        int centreCol = X / 2;

        // 3. Draw the grid row by row, column by column
        for (int r = 0; r < Y; r++) {
            int diag1 = (int) Math.round((double) r * (X - 1) / (Y - 1));
            int diag2 = (X - 1) - diag1;

            for (int c = 0; c < X; c++) {
                boolean edgeRow = (r == 0 || r == Y - 1);
                boolean edgeCol = (c == 0 || c == X - 1);
                char ch;

                // Priority order: the first matching rule wins
                if (edgeRow && edgeCol) {
                    ch = '+';
                } else if (edgeRow) {
                    ch = '=';
                } else if (edgeCol) {
                    ch = 'I';
                } else if (r == centreRow && c == centreCol) {
                    ch = '@';
                } else if (c == diag1 || c == diag2) {
                    ch = 'X';
                } else if (r == centreRow) {
                    ch = '-';
                } else if (c == centreCol) {
                    ch = '|';
                } else if ((r / B + c / B) % 2 == 0) {
                    ch = '.';
                } else {
                    ch = 'o';
                }
                System.out.print(ch);
            }
            System.out.println();
        }
    }
}
