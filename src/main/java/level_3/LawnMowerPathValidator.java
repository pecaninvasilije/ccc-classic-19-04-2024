package level_3;

import java.io.*;
import java.util.*;

public class LawnMowerPathValidator {

    public static void main(String[] args) {
        String inputFile = "level3_1.in";  // Adjust the file path as necessary
        String outputFile = "1.txt";  // Adjust the file path as necessary

        try (Scanner scanner = new Scanner(new File(inputFile));
             PrintWriter writer = new PrintWriter(new File(outputFile))) {

            int N = scanner.nextInt(); // Number of lawns
            scanner.nextLine(); // Clear the buffer after reading the integer

            for (int i = 0; i < N; i++) {
                int width = scanner.nextInt();
                int height = scanner.nextInt();
                scanner.nextLine(); // Consume the newline after dimensions
                char[][] lawn = new char[height][width];

                for (int j = 0; j < height; j++) {
                    lawn[j] = scanner.nextLine().toCharArray();
                }

                String path = scanner.nextLine();
                boolean valid = validatePath(lawn, path, width, height);
                writer.println(valid ? "VALID" : "INVALID");
            }
        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + e.getMessage());
        }
    }

    private static boolean validatePath(char[][] lawn, String path, int width, int height) {
        // Starting from a position that guarantees all free cells are visited
        int startX = findStartingPosition(lawn, width, height);
        int startY = 0;

        if (startX == -1) return false; // No valid start position found

        int x = startX, y = startY;
        boolean[][] visited = new boolean[height][width];
        visited[y][x] = true;

        for (char move : path.toCharArray()) {
            switch (move) {
                case 'W': y--; break;
                case 'S': y++; break;
                case 'A': x--; break;
                case 'D': x++; break;
            }

            if (lawn[y][x] == 'X' || visited[y][x]) return false;
            visited[y][x] = true;
        }

        // Ensure all free cells are visited
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                if (lawn[i][j] == '.' && !visited[i][j]) return false;
            }
        }

        return true;
    }

    private static int findStartingPosition(char[][] lawn, int width, int height) {
        for (int j = 0; j < width; j++) {
            if (lawn[0][j] == '.') {
                return j;  // Return the first free cell in the top row
            }
        }
        return -1;  // No valid starting position found
    }
}
