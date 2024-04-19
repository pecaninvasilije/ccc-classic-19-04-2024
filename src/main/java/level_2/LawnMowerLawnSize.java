package level_2;

import java.io.*;
import java.util.*;

public class LawnMowerLawnSize {

    public static void main(String[] args) {
        String inputFile = "D:\\OutsourcedIdeaProject\\ccc-classic-19-04-2024\\src\\main\\resources\\level2\\level2_example.in"; // Path to input file
        String outputFile = "D:\\OutsourcedIdeaProject\\ccc-classic-19-04-2024\\src\\main\\resources\\level2\\example2.txt"; // Path to output file

        try (Scanner scanner = new Scanner(new File(inputFile));
             PrintWriter writer = new PrintWriter(new File(outputFile))) {

            int N = scanner.nextInt();  // Read the number of paths
            scanner.nextLine();  // Consume the newline

            for (int i = 0; i < N; i++) {
                String path = scanner.nextLine();
                int[] lawnSize = calculateLawnSize(path);
                writer.println(lawnSize[0] + " " + lawnSize[1]);
            }

        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + e.getMessage());
        }
    }

    private static int[] calculateLawnSize(String path) {
        int x = 0, y = 0;
        int minX = 0, maxX = 0, minY = 0, maxY = 0;

        for (char direction : path.toCharArray()) {
            switch (direction) {
                case 'W': y++; break;
                case 'D': x++; break;
                case 'S': y--; break;
                case 'A': x--; break;
            }
            minX = Math.min(minX, x);
            maxX = Math.max(maxX, x);
            minY = Math.min(minY, y);
            maxY = Math.max(maxY, y);
        }

        int width = maxX - minX + 1;
        int height = maxY - minY + 1;
        return new int[] {width, height};
    }
}
