from ccc39.level2 import Level2
from file_level import FileLevel


class Level3(FileLevel):
    def solve(self, inp: str) -> str:
        lines = inp.split('\n')[1:]
        outputs = []

        i = 0

        while i < len(lines):
            valid = True
            dims = [int(x) for x in lines[i].split(' ')]
            start = i + 1
            end = i + dims[1] + 1
            current_lines = lines[start:end + 1]
            i = end + 1
            positions = [(0, 0)]
            tree_positions = set()

            for y, line in enumerate(current_lines[:-1]):
                for x, char in enumerate(line):
                    if char == 'X':
                        tree_positions.add((x, dims[1] - y - 1))

            for direction in current_lines[-1]:
                x, y = positions[-1]
                dx, dy = Level2.directions[direction]
                position = (x + dx, y + dy)
                positions.append(position)

            min_x = min(positions, key=lambda x: x[0])[0]
            min_y = min(positions, key=lambda x: x[1])[1]
            visited = set()

            for position in positions:
                pos = (position[0] - min_x, position[1] - min_y)

                if pos in visited or pos in tree_positions or pos[0] < 0 or pos[1] < 0 or pos[0] >= dims[0] or pos[1] >= dims[1]:
                    valid = False
                    break

                visited.add(pos)

            if valid and len(positions) != dims[0] * dims[1] - len(tree_positions):
                valid = False

            outputs.append('VALID' if valid else 'INVALID')

        return '\n'.join(outputs)


if __name__ == '__main__':
    Level3(3)
