package viva1;

import java.util.Scanner;

public class Q6 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter robot commands: ");
        String cmd = sc.nextLine();

        // Step 0: the whole string is invalid if any character is not F/B/L/R (either case)
        boolean valid = true;
        for (int i = 0; i < cmd.length(); i++) {
            char c = cmd.charAt(i);
            if (c != 'F' && c != 'B' && c != 'L' && c != 'R'
                    && c != 'f' && c != 'b' && c != 'l' && c != 'r') {
                valid = false;
                break;
            }
        }

        if (!valid) {
            System.out.println("Invalid Command String");
        } else {
            // Headings in clockwise order: 0=N, 1=E, 2=S, 3=W
            String headings = "NESW";
            int[] dx = {0, 1, 0, -1};
            int[] dy = {1, 0, -1, 0};

            int xP = 0, yP = 0, dP = 0;   // Robot P: position + heading
            int xQ = 0, yQ = 0, dQ = 0;   // Robot Q
            int meetings = 0, originReturns = 0, maxGap = 0;

            // kept[] works like a stack of the simplified commands; top = how many are kept
            char[] kept = new char[cmd.length()];
            int top = 0;

            for (int i = 0; i < cmd.length(); i++) {
                char c = cmd.charAt(i);
                boolean isP = (c >= 'A' && c <= 'Z');
                char u = c;                       // u = the uppercase letter, so P and Q share one set of checks
                if (!isP) u = (char) (c - 32);    // 'f' - 32 = 'F' (ASCII)

                // ---- Simulate the move ----
                int step = 0;   // +1 forward, -1 backward, 0 = it was a turn
                if (u == 'F') {
                    step = 1;
                } else if (u == 'B') {
                    step = -1;
                } else if (u == 'R') {
                    if (isP) dP = (dP + 1) % 4; else dQ = (dQ + 1) % 4;
                } else { // 'L': +3 is the same as -1 but never goes negative
                    if (isP) dP = (dP + 3) % 4; else dQ = (dQ + 3) % 4;
                }

                if (step != 0) {
                    if (isP) {
                        xP += step * dx[dP];
                        yP += step * dy[dP];
                        if (xP == 0 && yP == 0) originReturns++;
                    } else {
                        xQ += step * dx[dQ];
                        yQ += step * dy[dQ];
                        if (xQ == 0 && yQ == 0) originReturns++;
                    }
                    if (xP == xQ && yP == yQ) meetings++;

                    int gapX = xP - xQ;
                    if (gapX < 0) gapX = -gapX;
                    int gapY = yP - yQ;
                    if (gapY < 0) gapY = -gapY;
                    if (gapX + gapY > maxGap) maxGap = gapX + gapY;
                }

                // ---- Build the simplified command list ----
                char opposite;
                if (u == 'F') opposite = 'B';
                else if (u == 'B') opposite = 'F';
                else if (u == 'L') opposite = 'R';
                else opposite = 'L';
                if (!isP) opposite = (char) (opposite + 32);   // Q's opposite is lowercase, so P and Q never cancel

                if (top > 0 && kept[top - 1] == opposite) {
                    top--;                                    // cancel rule: drop the last kept command
                } else {
                    kept[top] = c;
                    top++;
                    boolean isTurn = (u == 'L' || u == 'R');
                    if (isTurn && top >= 4 && kept[top - 2] == c && kept[top - 3] == c && kept[top - 4] == c) {
                        top -= 4;                             // spin rule: 4 same turns = 360°, remove all four
                    }
                }
            }

            System.out.println("Meetings: " + meetings);
            System.out.println("Origin Returns: " + originReturns);
            System.out.println("Maximum Gap: " + maxGap);
            System.out.println("Robot P Final: (" + xP + "," + yP + ") facing " + headings.charAt(dP));
            System.out.println("Robot Q Final: (" + xQ + "," + yQ + ") facing " + headings.charAt(dQ));
            System.out.print("Simplified Commands: ");
            if (top == 0) {
                System.out.print("EMPTY");
            }
            for (int i = 0; i < top; i++) {
                System.out.print(kept[i]);
            }
            System.out.println();
        }
    }
}
