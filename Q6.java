package viva1;

import java.util.Scanner;

public class Q6 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter robot commands: ");
        String cmd = sc.nextLine();

        // Step 0: reject the whole string if any character is not F/B/L/R (either case)
        for (int i = 0; i < cmd.length(); i++) {
            if ("FBLRfblr".indexOf(cmd.charAt(i)) == -1) {
                System.out.println("Invalid Command String");
                return;
            }
        }

        // Headings in clockwise order: 0=N, 1=E, 2=S, 3=W
        String headings = "NESW";
        int[] dx = {0, 1, 0, -1};
        int[] dy = {1, 0, -1, 0};

        int xP = 0, yP = 0, dP = 0;   // Robot P: position + heading
        int xQ = 0, yQ = 0, dQ = 0;   // Robot Q
        int meetings = 0, originReturns = 0, maxGap = 0;
        String kept = "";             // simplified commands built so far

        for (int i = 0; i < cmd.length(); i++) {
            char c = cmd.charAt(i);
            boolean isP = Character.isUpperCase(c);
            char u = Character.toUpperCase(c);   // same letter, so P and Q share one switch

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
                int gap = Math.abs(xP - xQ) + Math.abs(yP - yQ);
                if (gap > maxGap) maxGap = gap;
            }

            // ---- Build the simplified command string ----
            char opposite;
            if (u == 'F') opposite = 'B';
            else if (u == 'B') opposite = 'F';
            else if (u == 'L') opposite = 'R';
            else opposite = 'L';
            if (!isP) opposite = Character.toLowerCase(opposite);   // keeps P and Q separate

            int n = kept.length();
            if (n > 0 && kept.charAt(n - 1) == opposite) {
                kept = kept.substring(0, n - 1);   // cancel rule
            } else {
                kept += c;
                n++;
                boolean isTurn = (u == 'L' || u == 'R');
                if (isTurn && n >= 4 && kept.substring(n - 4).equals("" + c + c + c + c)) {
                    kept = kept.substring(0, n - 4);   // spin rule: 4 same turns = 360°
                }
            }
        }

        System.out.println("Meetings: " + meetings);
        System.out.println("Origin Returns: " + originReturns);
        System.out.println("Maximum Gap: " + maxGap);
        System.out.println("Robot P Final: (" + xP + "," + yP + ") facing " + headings.charAt(dP));
        System.out.println("Robot Q Final: (" + xQ + "," + yQ + ") facing " + headings.charAt(dQ));
        System.out.println("Simplified Commands: " + (kept.isEmpty() ? "EMPTY" : kept));
    }
}
