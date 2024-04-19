package level_4;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.*;

public class Level4LawnMowerWarndorff {

    private static final int[] dx = {0, 1, 0, -1}; // Up, Right, Down, Left
    private static final int[] dy = {-1, 0, 1, 0};
    private static final char[] dirChars = {'W', 'D', 'S', 'A'};

    public static void main(String[] args) throws FileNotFoundException {
        String inputFile = "D:\\OutsourcedIdeaProject\\ccc-classic-19-04-2024\\src\\main\\resources\\level4\\level4_2.in";

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
        StringBuilder path = new StringBuilder();
        boolean[][] visited = new boolean[height][width];

        // Start from an arbitrary cell that is not a tree
        Point start = findStartPoint(lawn, width, height);
        if (start == null) return null;

        if (warnsdorffs(lawn, visited, path, start.x, start.y, width, height, 1)) {
            return path.toString();
        }
        return null;
    }

    private static boolean warnsdorffs(char[][] lawn, boolean[][] visited, StringBuilder path, int x, int y, int width, int height, int count) {
        visited[y][x] = true;
        if (count == width * height - numberOfTrees(lawn, width, height)) return true;

        List<Point> moves = getMoves(x, y, width, height, lawn, visited);
        moves.sort(Comparator.comparingInt(m -> getMoves(m.x, m.y, width, height, lawn, visited).size()));

        for (Point move : moves) {
            path.append(getDirection(x, y, move.x, move.y));
            if (warnsdorffs(lawn, visited, path, move.x, move.y, width, height, count + 1)) return true;
            path.deleteCharAt(path.length() - 1);
        }

        visited[y][x] = false;
        return false;
    }

    private static List<Point> getMoves(int x, int y, int width, int height, char[][] lawn, boolean[][] visited) {
        List<Point> moves = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            int nx = x + dx[i];
            int ny = y + dy[i];
            if (nx >= 0 && nx < width && ny >= 0 && ny < height && lawn[ny][nx] == '.' && !visited[ny][nx]) {
                moves.add(new Point(nx, ny));
            }
        }
        return moves;
    }

    private static char getDirection(int x1, int y1, int x2, int y2) {
        if (x2 == x1 + 1) return 'D';
        if (x2 == x1 - 1) return 'A';
        if (y2 == y1 + 1) return 'S';
        if (y2 == y1 - 1) return 'W';
        return ' ';
    }

    private static int numberOfTrees(char[][] lawn, int width, int height) {
        int trees = 0;
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                if (lawn[y][x] == 'X') trees++;
            }
        }
        return trees;
    }

    private static Point findStartPoint(char[][] lawn, int width, int height) {
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                if (lawn[y][x] == '.') return new Point(x, y);
            }
        }
        return null;
    }

    static class Point {
        int x, y;

        Point(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }
}
