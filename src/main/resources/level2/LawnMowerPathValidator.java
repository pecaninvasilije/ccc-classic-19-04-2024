package level3;

import java.io.*;
import java.util.*;

public class LawnMowerPathValidator {
    public static void main(String[] args) {
        String inputFile = "D:\\OutsourcedIdeaProject\\ccc-classic-19-04-2024\\src\\main\\resources\\level2\\level2_example.in"; // Path to input file
        String outputFile = "D:\\OutsourcedIdeaProject\\ccc-classic-19-04-2024\\src\\main\\resources\\level2\\example3.txt";

        try (Scanner scanner = new Scanner(new File(inputFile));
             PrintWriter writer = new PrintWriter(new File(outputFile))) {

            int N = scanner.nextInt();  // Read the number of lawns
            scanner.nextLine();  // Consume the newline

            for (int i = 0; i < N; i++) {
                int width = scanner.nextInt();
                int height = scanner.nextInt();
                scanner.nextLine();  // Consume the newline
                char[][] lawn = new char[height][width];

                for (int j = 0; j < height; j++) {
                    lawn[j] = scanner.nextLine().toCharArray();
                }

                String path = scanner.nextLine();
                String validity = validatePath(lawn, path, width, height) ? "VALID" : "INVALID";
                writer.println(validity);
            }

        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + e.getMessage());
        }
    }

    private static boolean validatePath(char[][] lawn, String path, int width, int height) {
        int x = 0, y = 0; // Starting point, assuming it starts at the top-left corner
        boolean[][] visited = new boolean[height][width];

        for (char step : path.toCharArray()) {
            switch (step) {
                case 'W': y--; break;
                case 'D': x++; break;
                case 'S': y++; break;
                case 'A': x--; break;
            }
            if (x < 0 || x >= width || y < 0 || y >= height || lawn[y][x] == 'X') {
                return false;  // Invalid move
            }
            if (visited[y][x]) {
                return false;  // Already visited
            }
            visited[y][x] = true;  // Mark as visited
        }

        // Check if all cells except the tree are visited
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                if (lawn[i][j] == '.' && !visited[i][j]) {
                    return false;  // Not all free cells are visited
                }
            }
        }

        return true;
    }
}
