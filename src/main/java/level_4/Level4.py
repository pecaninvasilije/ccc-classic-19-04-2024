def solve_lawn(lawn, w, h):
    tree_position = None
    start_position = None
    for y in range(h):
        for x in range(w):
            if lawn[y][x] == 'X':
                tree_position = (x, y)
            elif lawn[y][x] == '.' and start_position is None:
                start_position = (x, y)

    directions = {'S': (0, 1), 'N': (0, -1), 'E': (1, 0), 'W': (-1, 0)}
    path = []
    visited = [[False] * w for _ in range(h)]
    visited[start_position[1]][start_position[0]] = True

    def backtrack(x, y):
        if len(path) == w * h - 1:
            return True
        for d in directions:
            nx, ny = x + directions[d][0], y + directions[d][1]
            if 0 <= nx < w and 0 <= ny < h and not visited[ny][nx] and (nx, ny) != tree_position:
                visited[ny][nx] = True
                path.append(d)
                if backtrack(nx, ny):
                    return True
                visited[ny][nx] = False
                path.pop()
        return False

    if backtrack(start_position[0], start_position[1]):
        return ''.join(path)
    else:
        return "Kein Pfad gefunden"

def read_input_and_solve(filename):
    with open(filename, 'r') as file:
        n = int(file.readline().strip())
        results = []
        for _ in range(n):
            dimensions = file.readline().strip().split()
            w, h = int(dimensions[0]), int(dimensions[1])
            lawn = [file.readline().strip() for _ in range(h)]
            result = solve_lawn(lawn, w, h)
            results.append(result)
        return results

# Pfad zur Datei anpassen
file_path = 'D:\\OutsourcedIdeaProject\\ccc-classic-19-04-2024\\src\\main\\resources\\level4\\level4_1.in'
results = read_input_and_solve(file_path)
for result in results:
    print(result)
