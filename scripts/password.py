import sys
from typing import List


def main() -> None:
    if len(sys.argv) != 3:
        print("Usage: python password.py '<password>' '<indexes separated by comma>'")
        sys.exit(1)
    password: str = sys.argv[1]
    indices: List[int] = list(map(lambda val: int(val), sys.argv[2].split(",")))
    combination: str = ""

    for i in indices:
        if i >= len(password):
            print("Index out of range")

            sys.exit(1)
        combination += password[i - 1]
    print(combination)


if __name__ == '__main__':
    main()
