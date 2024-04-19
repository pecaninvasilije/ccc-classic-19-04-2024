package level_4;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Scanner;

public class Level4LawnMower {

    private static final int[] dx = {1, -1, 0, 0}; // Right, Left, Down, Up movements
    private static final int[] dy = {0, 0, 1, -1};
    private static final char[] dirChars = {'D', 'A', 'S', 'W'};

    public static void main(String[] args) throws FileNotFoundException {
        String inputFile = "D:\\OutsourcedIdeaProject\\ccc-classic-19-04-2024\\src\\main\\resources\\level4\\level4_1.in";  // Update the path to your input file

        try (Scanner scanner = new Scanner(new FileInputStream(inputFile))) {
            int N = scanner.nextInt();  // Number of lawns
            for (int i = 0; i < N; i++) {
                int width = scanner.nextInt();
                int height = scanner.nextInt();
                scanner.nextLine();  // Consume the remaining newline character

                char[][] lawn = new char[height][];
                for (int j = 0; j < height; j++) {
                    lawn[j] = scanner.nextLine().toCharArray();
                }

                String result = findPathForLawn(lawn, width, height);
                System.out.println(result != null ? result : "INVALID");
            }
        }
    }

    private static String findPathForLawn(char[][] lawn, int width, int height) {
        StringBuilder path = new StringBuilder();
        boolean[][] visited = new boolean[height][width];

        // Heuristically find the best starting point or try multiple starting points
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                if (lawn[y][x] == '.' && dfs(lawn, visited, path, x, y, width, height, 1)) {
                    return path.toString();
                }
                path.setLength(0);
                for (boolean[] row : visited) {
                    java.util.Arrays.fill(row, false);
                }
            }
        }
        return null;
    }

    private static boolean dfs(char[][] lawn, boolean[][] visited, StringBuilder path, int x, int y, int width, int height, int stepCount) {
        visited[y][x] = true;
        if (stepCount == width * height - numberOfTrees(lawn, width, height)) {
            return true;
        }

        // Order directions based on a heuristic (e.g., least restrictive first)
        Integer[] directions = {0, 1, 2, 3};
        Arrays.sort(directions, (a, b) -> countFreeNeighbours(x + dx[b], y + dy[b], width, height, lawn, visited) -
                countFreeNeighbours(x + dx[a], y + dy[a], width, height, lawn, visited));

        for (int i : directions) {
            int nx = x + dx[i];
            int ny = y + dy[i];
            if (isValidMove(nx, ny, width, height, lawn, visited)) {
                path.append(dirChars[i]);
                if (dfs(lawn, visited, path, nx, ny, width, height, stepCount + 1)) {
                    return true;
                }
                path.deleteCharAt(path.length() - 1);
            }
        }

        visited[y][x] = false;
        return false;
    }

    private static int numberOfTrees(char[][] lawn, int width, int height) {
        int trees = 0;
        for (char[] row : lawn) {
            for (char c : row) {
                if (c == 'X') trees++;
            }
        }
        return trees;
    }

    private static int countFreeNeighbours(int x, int y, int width, int height, char[][] lawn, boolean[][] visited) {
        int freeNeighbours = 0;
        for (int i = 0; i < 4; i++) {
            int nx = x + dx[i];
            int ny = y + dy[i];
            if (nx >= 0 && nx < width && ny >= 0 && ny < height && lawn[ny][nx] == '.' && !visited[ny][nx]) {
                freeNeighbours++;
            }
        }
        return freeNeighbours;
    }

    private static boolean isValidMove(int x, int y, int width, int height, char[][] lawn, boolean[][] visited) {
        return x >= 0 && x < width && y >= 0 && y < height && lawn[y][x] == '.' && !visited[y][x];
    }
}
