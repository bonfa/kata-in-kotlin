source: https://codingdojo.org/kata/manhattan-distance/

# Manhattan Distance
Manhattan distance is the distance between two points in a grid (like the grid-like street geography of the New York borough of Manhattan) calculated by only taking a vertical and/or horizontal path.

Additional info about manhattan distance: https://en.wikipedia.org/wiki/Taxicab_geometry

Write a function int manhattanDistance(Point, Point) that returns the Manhattan Distance between the two points.

## Constraints
The class Point is immutable
The class Point has no Getters, no Setters
The class Point has no public properties (i.e. the internal state cannot be read from outside the class).

## Suggested tests
- manhattanDistance( Point(1, 1), Point(1, 1) ) should return 0
- manhattanDistance( Point(5, 4), Point(3, 2) ) should return 4
- manhattanDistance( Point(1, 1), Point(0, 3) ) should return 3
- 