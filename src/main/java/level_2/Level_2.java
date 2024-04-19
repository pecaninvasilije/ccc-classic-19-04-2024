package level_2;

import java.io.*;

public class Level_2 {
    public static void main(String[] args) {
        try {
            // Open the input file for reading
            BufferedReader reader = new BufferedReader(new FileReader("C:\\Users\\vasil\\htlstp\\CCC 2024\\ccc-classic-19-04-2024\\src\\main\\java\\level_2\\level2_5.in"));

            // Open the output file for writing
            BufferedWriter writer = new BufferedWriter(new FileWriter("output.txt"));

            // Read the number of paths
            int N = Integer.parseInt(reader.readLine());

            // Process each path
            for (int i = 0; i < N; i++) {
                String path = reader.readLine();
                int[] lawnSize = calculateLawnSize(path);

                // Write the lawn size to the output file
                writer.write(lawnSize[0] + " " + lawnSize[1]);
                writer.newLine();
            }

            // Close the input and output files
            reader.close();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Function to calculate the size of the smallest rectangular lawn that the path can fit into
    private static int[] calculateLawnSize(String path) {
        int x = 0, y = 0;
        int minX = 0, minY = 0, maxX = 0, maxY = 0;

        for (char c : path.toCharArray()) {
            switch (c) {
                case 'W':
                    y++;
                    break;
                case 'D':
                    x++;
                    break;
                case 'S':
                    y--;
                    break;
                case 'A':
                    x--;
                    break;
            }
            minX = Math.min(minX, x);
            minY = Math.min(minY, y);
            maxX = Math.max(maxX, x);
            maxY = Math.max(maxY, y);
        }

        // Calculate the width and height of the lawn
        int width = Math.abs(maxX - minX) + 1;
        int height = Math.abs(maxY - minY) + 1;

        return new int[]{width, height};
    }
}
