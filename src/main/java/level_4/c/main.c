#include <stdio.h>
#include <stdlib.h>

#define MAX_HEIGHT 100
#define MAX_WIDTH 100
#define BIT_MASK_INDEX(x) ((x) / 8)
#define BIT_OFFSET_INDEX(x) ((x) % 8)

// Direction vectors for Right, Left, Down, Up movements
int dx[4] = {1, -1, 0, 0};
int dy[4] = {0, 0, 1, -1};
char dirChars[4] = {'D', 'A', 'S', 'W'};

// Visited matrix stored as bits for space and access efficiency
unsigned char visited[MAX_HEIGHT][MAX_WIDTH / 8 + 1];

// Inline functions for bit manipulation
inline void setVisited(int x, int y) {
    visited[y][BIT_MASK_INDEX(x)] |= (1 << BIT_OFFSET_INDEX(x));
}

inline void clearVisited(int x, int y) {
    visited[y][BIT_MASK_INDEX(x)] &= ~(1 << BIT_OFFSET_INDEX(x));
}

inline int isVisited(int x, int y) {
    return visited[y][BIT_MASK_INDEX(x)] & (1 << BIT_OFFSET_INDEX(x));
}

// Function prototypes
int dfs(char **lawn, int width, int height, int x, int y, char *path, int pathIndex);
int allVisited(int width, int height);
int isValidMove(int x, int y, int width, int height, char **lawn);

int main() {
    char inputFile[] = ".\\level4_example.in"; // Adjust path as needed
    FILE *file = fopen(inputFile, "r");
    if (!file) {
        perror("Failed to open file");
        return EXIT_FAILURE;
    }

    int width, height;
    fscanf(file, "%d %d", &width, &height); // Read dimensions

    // Allocate lawn memory
    char **lawn = malloc(height * sizeof(char *));
    for (int i = 0; i < height; i++) {
        lawn[i] = malloc((width + 1) * sizeof(char));
        fscanf(file, "%s", lawn[i]);
    }

    char *path = malloc((width * height + 1) * sizeof(char)); // Path storage
    int found = 0;

    // Start DFS from each cell that is not blocked
    for (int y = 0; !found && y < height; y++) {
        for (int x = 0; !found && x < width; x++) {
            if (lawn[y][x] == '.') {
                if (dfs(lawn, width, height, x, y, path, 0)) {
                    printf("Path: %s\n", path);
                    found = 1;
                }
            }
        }
    }

    if (!found) printf("No path found\n");

    // Cleanup
    for (int i = 0; i < height; i++) free(lawn[i]);
    free(lawn);
    free(path);
    fclose(file);
    return 0;
}

int dfs(char **lawn, int width, int height, int x, int y, char *path, int pathIndex) {
    setVisited(x, y);
    path[pathIndex] = dirChars[0]; // Initially set to some direction

    if (allVisited(width, height)) {
        path[pathIndex + 1] = '\0'; // Terminate string
        return 1;
    }

    for (int i = 0; i < 4; i++) {
        int nx = x + dx[i], ny = y + dy[i];
        if (isValidMove(nx, ny, width, height, lawn) && !isVisited(nx, ny)) {
            path[pathIndex] = dirChars[i];
            if (dfs(lawn, width, height, nx, ny, path, pathIndex + 1)) return 1;
        }
    }

    clearVisited(x, y);
    return 0;
}

int allVisited(int width, int height) {
    for (int y = 0; y < height; y++) {
        for (int x = 0; x < width; x++) {
            if (!(visited[y][BIT_MASK_INDEX(x)] & (1 << BIT_OFFSET_INDEX(x)))) return 0;
        }
    }
    return 1;
}

int isValidMove(int x, int y, int width, int height, char **lawn) {
    return x >= 0 && x < width && y >= 0 && y < height && lawn[y][x] == '.';
}
