import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class PictureFrogsMain {
    public static void main(String[] args) throws FileNotFoundException {
        // This chunk used to handle input data and store in usable way
        File inputFile = new File("input.txt");
        Scanner sc = new Scanner(inputFile);
        int numFrogs = sc.nextInt();
        int numSteps = sc.nextInt();
        ArrayList<Integer> numArray = new ArrayList<Integer>(numFrogs * numSteps);
        for (int i = 0; i < numFrogs * numSteps; i++) {
            numArray.add(i, sc.nextInt());
        }
        Collections.sort(numArray);
        char[][] signArray = new char[numSteps][numFrogs];
        sc.nextLine(); // added to offload whitespace on previous line
        for (int i = 0; i < numSteps; i++) {
            String line = sc.nextLine();
            for (int j = 0; j < numFrogs; j++) {
                signArray[i][j] = line.charAt(j);
            }
        }
        int[][] outputVals = new int[numSteps][numFrogs];

        // Used to Print collected input file data
        System.out.println(numFrogs + " " + numSteps);
//        String s = "";
//        for (int i : numArray) {
//            s = s + i + " ";
//        }
//        System.out.println(s);
//        for (char[] a : signArray) {
//            s = "";
//            for (char c : a) {
//                s = s + c + " ";
//            }
//            System.out.println(s);
//        }

        // Frog motion algorithm
        // adds elements to each frog list while counting changes
        for (int x = 0; x < numFrogs; x++) {
            ArrayList<Integer> frogList = new ArrayList<Integer>(numSteps);
            int numSignChanges = 1; // code written to consider first sign a change
            for (int i = 0; i < numSteps; i++) {
                if (i > 0 && signArray[i][x] != signArray[i - 1][x]) {
                    numSignChanges += 1;
                }
                frogList.add(i, numArray.get(i + (numSteps * x))); // adds to i to get displacement within list based on which frog it is
            }

            int currentIndex = numSteps - numSignChanges + 1;
            char lastSign = 'x'; // set to first char
            int overallVal = 0;
            for (int i = 0; i < numSteps; i++) {
                currentIndex -= 1;
                if (signArray[i][x] == lastSign && frogList.size() > 1 && currentIndex > 0) {
                    if (outputVals[i - 1][x] > 0) {
                        outputVals[i][x] = -frogList.get(currentIndex - 1);
                    } else {
                        outputVals[i][x] = frogList.get(currentIndex - 1);
                    }
                    frogList.remove(currentIndex - 1);
                } else if (frogList.size() == 1) {
                    outputVals[i][x] = frogList.get(0);
                } else {
                    if (signArray[i][x] == '+') {
                        outputVals[i][x] = frogList.get(currentIndex);
                    } else {
                        outputVals[i][x] = -frogList.get(currentIndex);
                    }
                    frogList.remove(currentIndex);
                    currentIndex += 1;
                }
                // checks to make sure that value will give the right sign
                overallVal += outputVals[i][x];
                if ((signArray[i][x] == '+' && overallVal < 0) || (signArray[i][x] == '-' && overallVal > 0)) {
                    overallVal -= outputVals[i][x];
                    outputVals[i][x] = -outputVals[i][x];
                    overallVal += outputVals[i][x];
                }
                lastSign = signArray[i][x];
                System.out.println(currentIndex);
            }
        }
        try {
            PrintWriter writer = new PrintWriter("output.txt");
            for (int[] i : outputVals) {
                String st = "";
                for (int c : i) {
                    st = st + c + ",";
                }
                writer.println(st.substring(0, st.length() - 1));
            }
            writer.close();
        } catch (Exception e) {
            System.out.println(e);
        }
        // print output
//        for (int[] i : outputVals) {
//            String st = "";
//            for (int c : i) {
//                st = st + c + " ";
//            }
//            System.out.println(st);
//        }
    }
}
