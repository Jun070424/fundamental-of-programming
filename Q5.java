package viva1;

import java.util.Scanner;

public class Q5 { // WIX1002 Viva 1 Set B - Q5: Rangoli Star Frame
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int width, height, blockSize;

        // 1. Input validation: keep asking for all three until every rule passes
        while (true) {
            System.out.print("Enter width X: ");
            width = sc.nextInt();
            System.out.print("Enter height Y: ");
            height = sc.nextInt();
            System.out.print("Enter block size B: ");
            blockSize = sc.nextInt();

            boolean widthValid = width >= 7 && width <= 29 && width % 2 == 1;
            boolean heightValid = height >= 7 && height <= 29 && height % 2 == 1;
            boolean blockSizeValid = blockSize >= 1 && blockSize <= 3;
            if (widthValid && heightValid && blockSizeValid) {
                break;
            }
            System.out.println("Error: X and Y must be odd numbers between 7 and 29, and B must be between 1 and 3.");
        }

        // 2. Centre position (integer division)
        int centreRow = height / 2;
        int centreColumn = width / 2;

        // 3. Draw the grid row by row, column by column
        for (int row = 0; row < height; row++) {
            int firstDiagonal = (int) Math.round((double) row * (width - 1) / (height - 1));   // \ from top-left
            int secondDiagonal = (width - 1) - firstDiagonal;                                  // / mirror image

            for (int column = 0; column < width; column++) {
                boolean topOrBottomRow = (row == 0 || row == height - 1);
                boolean leftOrRightColumn = (column == 0 || column == width - 1);
                char symbol;

                // Priority order: the first matching rule wins
                if (topOrBottomRow && leftOrRightColumn) {
                    symbol = '+';
                } else if (topOrBottomRow) {
                    symbol = '=';
                } else if (leftOrRightColumn) {
                    symbol = 'I';
                } else if (row == centreRow && column == centreColumn) {
                    symbol = '@';
                } else if (column == firstDiagonal || column == secondDiagonal) {
                    symbol = 'X';
                } else if (row == centreRow) {
                    symbol = '-';
                } else if (column == centreColumn) {
                    symbol = '|';
                } else if ((row / blockSize + column / blockSize) % 2 == 0) {
                    symbol = '.';
                } else {
                    symbol = 'o';
                }
                System.out.print(symbol);
            }
            System.out.println();
        }
    }
}
