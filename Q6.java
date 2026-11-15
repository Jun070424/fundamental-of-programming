package viva1;

import java.util.Scanner;

public class Q6 { // WIX1002 Viva 1 Set B - Q6: Two Robots
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter robot commands: ");
        String commands = sc.nextLine();

        // Step 0: the whole string is invalid if any character is not F/B/L/R (either case)
        boolean valid = true;
        for (int i = 0; i < commands.length(); i++) {
            char command = commands.charAt(i);
            if (command != 'F' && command != 'B' && command != 'L' && command != 'R'
                    && command != 'f' && command != 'b' && command != 'l' && command != 'r') {
                valid = false;
                break;
            }
        }

        if (!valid) {
            System.out.println("Invalid Command String");
        } else {
            // Directions in clockwise order: 0 = North, 1 = East, 2 = South, 3 = West
            String directionLetters = "NESW";
            int[] stepX = {0, 1, 0, -1};   // how much x changes when moving one step in each direction
            int[] stepY = {1, 0, -1, 0};   // how much y changes when moving one step in each direction

            int robotPX = 0, robotPY = 0, robotPDirection = 0;   // Robot P: position and direction
            int robotQX = 0, robotQY = 0, robotQDirection = 0;   // Robot Q: position and direction
            int meetings = 0, originReturns = 0, maximumGap = 0;

            // simplified[] holds the simplified commands like a stack of plates; simplifiedCount = how many are kept
            char[] simplified = new char[commands.length()];
            int simplifiedCount = 0;

            for (int i = 0; i < commands.length(); i++) {
                char command = commands.charAt(i);
                boolean isRobotP = (command >= 'A' && command <= 'Z');   // uppercase = Robot P, lowercase = Robot Q
                char upperCommand = command;                             // uppercase version, so P and Q share one set of checks
                if (!isRobotP)
                    upperCommand = (char) (command - 32);                // 'f' - 32 = 'F' (ASCII)

                // ---- Move or turn the robot ----
                int step = 0;   // +1 = forward, -1 = backward, 0 = it was a turn
                if (upperCommand == 'F') {
                    step = 1;
                }
                else if (upperCommand == 'B') {
                    step = -1;
                }
                else if (upperCommand == 'R') {
                    if (isRobotP)
                        robotPDirection = (robotPDirection + 1) % 4;
                    else
                        robotQDirection = (robotQDirection + 1) % 4; //endif
                }
                else { // 'L': adding 3 is the same as subtracting 1, but never becomes negative
                    if (isRobotP)
                        robotPDirection = (robotPDirection + 3) % 4;
                    else
                        robotQDirection = (robotQDirection + 3) % 4; //endif
                }//endif

                if (step != 0) {
                    if (isRobotP) {
                        robotPX += step * stepX[robotPDirection];
                        robotPY += step * stepY[robotPDirection];
                        if (robotPX == 0 && robotPY == 0)
                            originReturns++; //endif
                    }
                    else {
                        robotQX += step * stepX[robotQDirection];
                        robotQY += step * stepY[robotQDirection];
                        if (robotQX == 0 && robotQY == 0)
                            originReturns++; //endif
                    }//endif
                    if (robotPX == robotQX && robotPY == robotQY)
                        meetings++; //endif

                    int gapX = robotPX - robotQX;
                    if (gapX < 0)
                        gapX = -gapX; //endif

                    int gapY = robotPY - robotQY;
                    if (gapY < 0)
                        gapY = -gapY; //endif
                    if (gapX + gapY > maximumGap)
                        maximumGap = gapX + gapY; //endif
                }//endif

                // ---- Build the simplified command list ----
                char oppositeCommand;
                if (upperCommand == 'F')
                    oppositeCommand = 'B';
                else if (upperCommand == 'B')
                    oppositeCommand = 'F';
                else if (upperCommand == 'L')
                    oppositeCommand = 'R';
                else
                    oppositeCommand = 'L';
                //endif

                if (!isRobotP)
                    oppositeCommand = (char) (oppositeCommand + 32);   // Robot Q's opposite is lowercase, so P and Q never cancel

                if (simplifiedCount > 0 && simplified[simplifiedCount - 1] == oppositeCommand) {
                    simplifiedCount--;                                  // cancel rule: remove the last kept command
                }
                else {
                    simplified[simplifiedCount] = command;
                    simplifiedCount++;
                    boolean isTurn = (upperCommand == 'L' || upperCommand == 'R');
                    if (isTurn && simplifiedCount >= 4 && simplified[simplifiedCount - 2] == command
                            && simplified[simplifiedCount - 3] == command && simplified[simplifiedCount - 4] == command) {
                        simplifiedCount -= 4;                           // spin rule: 4 same turns = 360 degrees, remove all four
                    }
                }
            }

            System.out.println("Meetings: " + meetings);
            System.out.println("Origin Returns: " + originReturns);
            System.out.println("Maximum Gap: " + maximumGap);
            System.out.println("Robot P Final: (" + robotPX + "," + robotPY + ") facing " + directionLetters.charAt(robotPDirection));
            System.out.println("Robot Q Final: (" + robotQX + "," + robotQY + ") facing " + directionLetters.charAt(robotQDirection));
            System.out.print("Simplified Commands: ");
            if (simplifiedCount == 0) {
                System.out.print("EMPTY");
            }
            for (int i = 0; i < simplifiedCount; i++) {
                System.out.print(simplified[i]);
            }
            System.out.println();
        }
    }
}
