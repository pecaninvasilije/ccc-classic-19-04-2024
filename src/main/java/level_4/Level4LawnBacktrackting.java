package level_4;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Scanner;

class Level4LawnBacktrackting {

    private static final int[] dx = {1, -1, 0, 0}; // Right, Left, Down, Up movements
    private static final int[] dy = {0, 0, 1, -1};
    private static final char[] dirChars = {'D', 'A', 'S', 'W'};

    public static void main(String[] args) throws FileNotFoundException {
        String inputFile = "D:\\OutsourcedIdeaProject\\ccc-classic-19-04-2024\\src\\main\\resources\\level4\\level4_example.in";

        try (Scanner scanner = new Scanner(new FileInputStream(inputFile))) {
            int N = scanner.nextInt(); // Number of lawns
            for (int i = 0; i < N; i++) {
                int width = scanner.nextInt();
                int height = scanner.nextInt();
                scanner.nextLine(); // Consume the newline

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
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                if (lawn[y][x] == '.') {
                    StringBuilder path = new StringBuilder();
                    boolean[][] visited = new boolean[height][width];
                    if (dfs(lawn, visited, path, x, y, width, height)) {
                        return path.toString();
                    }
                }
            }
        }
        return null;
    }

    private static boolean dfs(char[][] lawn, boolean[][] visited, StringBuilder path, int x, int y, int width, int height) {
        visited[y][x] = true;
        path.append(' '); // Placeholder for direction, updated when a move is confirmed

        if (allVisited(visited, height, width)) {
            path.setLength(path.length() - 1); // Remove the last placeholder
            return true;
        }

        Integer[] directionOrder = getDirectionOrder(x, y, width, height, lawn, visited);
        for (int dir : directionOrder) {
            int nx = x + dx[dir];
            int ny = y + dy[dir];
            if (isValidMove(nx, ny, width, height, lawn, visited)) {
                path.setCharAt(path.length() - 1, dirChars[dir]); // Set the correct direction
                if (dfs(lawn, visited, path, nx, ny, width, height)) {
                    return true;
                }
                path.setCharAt(path.length() - 1, ' '); // Reset last direction on backtrack
            }
        }

        visited[y][x] = false; // Unmark this cell before backtracking
        path.setLength(path.length() - 1); // Remove the last direction or placeholder
        return false;
    }

    private static Integer[] getDirectionOrder(int x, int y, int width, int height, char[][] lawn, boolean[][] visited) {
        Integer[] directions = {0, 1, 2, 3};
        Arrays.sort(directions, Comparator.comparingInt(dir ->
                countFreeNeighbours(x + dx[dir], y + dy[dir], width, height, lawn, visited)));
        return directions;
    }

    private static int countFreeNeighbours(int x, int y, int width, int height, char[][] lawn, boolean[][] visited) {
        int count = 0;
        for (int i = 0; i < 4; i++) {
            int nx = x + dx[i];
            int ny = y + dy[i];
            if (isValidMove(nx, ny, width, height, lawn, visited)) {
                count++;
            }
        }
        return count;
    }

    private static boolean allVisited(boolean[][] visited, int height, int width) {
        for (boolean[] row : visited) {
            for (boolean v : row) {
                if (!v) return false;
            }
        }
        return true;
    }

    private static boolean isValidMove(int x, int y, int width, int height, char[][] lawn, boolean[][] visited) {
        return x >= 0 && x < width && y >= 0 && y < height && lawn[y][x] == '.' && !visited[y][x];
    }
}
