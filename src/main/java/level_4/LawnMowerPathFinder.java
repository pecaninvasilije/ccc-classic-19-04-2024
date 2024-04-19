package level_4;

import java.util.*;

public class LawnMowerPathFinder {
    private static final int[] dRow = {-1, 1, 0, 0}; // Directions: up, down, left, right
    private static final int[] dCol = {0, 0, -1, 1};
    private static final char[] dChar = {'W', 'S', 'A', 'D'}; // Corresponding direction characters

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int N = scanner.nextInt(); // Number of lawns

        while (N-- > 0) {
            int rows = scanner.nextInt();
            int cols = scanner.nextInt();
            scanner.nextLine(); // Consume the newline after integers

            char[][] lawn = new char[rows][cols];
            for (int i = 0; i < rows; i++) {
                lawn[i] = scanner.nextLine().trim().toCharArray();
            }

            String path = findPath(lawn, rows, cols);
            System.out.println(path);
        }

        scanner.close();
    }

    private static String findPath(char[][] lawn, int rows, int cols) {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (lawn[i][j] != 'X') {
                    boolean[][] visited = new boolean[rows][cols];
                    String path = bfs(lawn, i, j, visited, rows, cols);
                    if (path != null) {
                        return path;
                    }
                }
            }
        }
        return "No valid path found";
    }

    private static String bfs(char[][] lawn, int startRow, int startCol, boolean[][] visited, int rows, int cols) {
        PriorityQueue<Node> queue = new PriorityQueue<>(Comparator.comparingInt(n -> n.neighborCount));
        queue.offer(new Node(startRow, startCol, "", countAvailableNeighbors(startRow, startCol, lawn, visited, rows, cols)));
        visited[startRow][startCol] = true;

        while (!queue.isEmpty()) {
            Node current = queue.poll();

            if (isComplete(visited, rows, cols, lawn)) {
                return current.path;
            }

            for (int i = 0; i < 4; i++) {
                int newRow = current.row + dRow[i];
                int newCol = current.col + dCol[i];
                if (isSafe(newRow, newCol, lawn, visited, rows, cols)) {
                    visited[newRow][newCol] = true;
                    String newPath = current.path + dChar[i];
                    queue.offer(new Node(newRow, newCol, newPath, countAvailableNeighbors(newRow, newCol, lawn, visited, rows, cols)));
                }
            }
        }
        return null;
    }

    private static boolean isComplete(boolean[][] visited, int rows, int cols, char[][] lawn) {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (!visited[i][j] && lawn[i][j] != 'X') {
                    return false;
                }
            }
        }
        return true;
    }

    private static boolean isSafe(int row, int col, char[][] lawn, boolean[][] visited, int rows, int cols) {
        return row >= 0 && row < rows && col >= 0 && col < cols && !visited[row][col] && lawn[row][col] != 'X';
    }

    private static int countAvailableNeighbors(int row, int col, char[][] lawn, boolean[][] visited, int rows, int cols) {
        int count = 0;
        for (int i = 0; i < 4; i++) {
            int newRow = row + dRow[i];
            int newCol = col + dCol[i];
            if (newRow >= 0 && newRow < rows && newCol >= 0 && newCol < cols && !visited[newRow][newCol] && lawn[newRow][newCol] != 'X') {
                count++;
            }
        }
        return count;
    }

    private static class Node {
        int row;
        int col;
        String path;
        int neighborCount;

        Node(int row, int col, String path, int neighborCount) {
            this.row = row;
            this.col = col;
            this.path = path;
            this.neighborCount = neighborCount;
        }
    }
}
