package level_1;

import java.io.*;
import java.util.*;

public class LawnMowerPathCounter {

    public static void main(String[] args) {
        String inputFile = "D:\\OutsourcedIdeaProject\\ccc-classic-19-04-2024\\src\\main\\resources\\level1\\level1_1.in.in"; // Adjust the input file path as necessary
        String outputFile = "D:\\OutsourcedIdeaProject\\ccc-classic-19-04-2024\\src\\main\\resources\\level1\\1.txt"; // Adjust the output file path as necessary

        try (Scanner scanner = new Scanner(new File(inputFile));
             PrintWriter writer = new PrintWriter(new File(outputFile))) {

            int N = scanner.nextInt();  // Read the number of paths
            scanner.nextLine();  // Consume the newline

            for (int i = 0; i < N; i++) {
                String path = scanner.nextLine();
                int[] counts = countDirections(path);
                writer.println(counts[0] + " " + counts[1] + " " + counts[2] + " " + counts[3]);
            }

        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + e.getMessage());
        }
    }

    private static int[] countDirections(String path) {
        int[] counts = new int[4];  // Array to store counts for 'W', 'D', 'S', 'A'
        for (char direction : path.toCharArray()) {
            switch (direction) {
                case 'W': counts[0]++; break;
                case 'D': counts[1]++; break;
                case 'S': counts[2]++; break;
                case 'A': counts[3]++; break;
            }
        }
        return counts;
    }
}
