package level_4;

import fileHandler.FileHandler;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.*;

public class Level4LawnMower {

    private static final int[] dx = {1, -1, 0, 0}; // movements in x direction (right and left)
    private static final int[] dy = {0, 0, 1, -1}; // movements in y direction (down and up)
    private static final char[] dirChars = {'D', 'A', 'S', 'W'}; // corresponding directions as characters

    public static void main(String[] args) throws FileNotFoundException {
        String inputFile = "D:\\OutsourcedIdeaProject\\ccc-classic-19-04-2024\\src\\main\\resources\\level4\\level4_1.in"; // Path to input file

        Scanner scanner = new Scanner(new FileInputStream(inputFile));
        int N = scanner.nextInt(); // number of lawns
        for (int i = 0; i < N; i++) {
            int width = scanner.nextInt();
            int height = scanner.nextInt();
            scanner.nextLine(); // consume the remaining newline character

            char[][] lawn = new char[height][];
            for (int j = 0; j < height; j++) {
                lawn[j] = scanner.nextLine().toCharArray();
            }

            Optional<String> result = findPathForLawn(lawn, width, height);
            System.out.println(result.orElse("INVALID")); // print the result or "INVALID" if no path found
        }
        scanner.close();
    }

    private static Optional<String> findPathForLawn(char[][] lawn, int width, int height) {
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                if (lawn[y][x] == '.') {
                    StringBuilder path = new StringBuilder();
                    boolean[][] visited = new boolean[height][width];
                    if (dfs(lawn, visited, path, x, y, width, height)) {
                        System.out.println(path);
                        return Optional.of(path.toString());
                    }
                }
            }
        }
        return Optional.empty();
    }

    private static boolean dfs(char[][] lawn, boolean[][] visited, StringBuilder path, int x, int y, int width, int height) {
        visited[y][x] = true;
        if (isComplete(visited, width, height, lawn)) return true; // Check if all free cells are visited

        for (int i = 0; i < 4; i++) {
            int nx = x + dx[i];
            int ny = y + dy[i];
            if (isValidMove(nx, ny, width, height, lawn, visited)) {
                path.append(dirChars[i]);
                if (dfs(lawn, visited, path, nx, ny, width, height)) return true;
                path.deleteCharAt(path.length() - 1); // Backtrack
            }
        }

        visited[y][x] = false; // Unmark this cell before backtracking
        return false;
    }

    private static boolean isValidMove(int x, int y, int width, int height, char[][] lawn, boolean[][] visited) {
        return x >= 0 && x < width && y >= 0 && y < height && lawn[y][x] == '.' && !visited[y][x];
    }

    private static boolean isComplete(boolean[][] visited, int width, int height, char[][] lawn) {
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                if (!visited[y][x] && lawn[y][x] == '.') return false;
            }
        }
        return true;
    }
}
