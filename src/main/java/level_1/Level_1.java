package level_1;

import java.io.*;
import java.util.Scanner;

public class Level_1 {
    public static void main(String[] args) {
        try {
            // Open the input file for reading
            BufferedReader reader = new BufferedReader(new FileReader("C:\\Users\\vasil\\htlstp\\CCC 2024\\ccc-classic-19-04-2024\\src\\main\\java\\level_1\\level1_5.in"));

            // Open the output file for writing
            BufferedWriter writer = new BufferedWriter(new FileWriter("output.txt"));

            // Read the number of paths
            int N = Integer.parseInt(reader.readLine());

            // Process each path
            for (int i = 0; i < N; i++) {
                String path = reader.readLine();
                int[] counts = countDirections(path);

                // Write the counts to the output file
                for (int j = 0; j < counts.length; j++) {
                    writer.write(counts[j] + " ");
                }
                writer.newLine();
            }

            // Close the input and output files
            reader.close();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Function to count the occurrences of each direction in the path
    private static int[] countDirections(String path) {
        int[] counts = new int[4]; // Index 0: W, Index 1: D, Index 2: S, Index 3: A

        for (char c : path.toCharArray()) {
            switch (c) {
                case 'W':
                    counts[0]++;
                    break;
                case 'D':
                    counts[1]++;
                    break;
                case 'S':
                    counts[2]++;
                    break;
                case 'A':
                    counts[3]++;
                    break;
            }
        }

        return counts;
    }
}
