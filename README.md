# maze-solver

A Java maze solver that models the maze as a graph and finds a path using depth-first search. Includes a Swing GUI that animates the solution step by step.

## How it works

Mazes are defined in text files. Rooms connect via corridors (free) or doors (require coins to unlock). The solver builds an undirected graph — each room is a node, each passage is a weighted edge — then runs a recursive DFS that tracks remaining coins and backtracks when it runs out.

```
s1owo1o     s = start
cwcwcwc     x = exit
o2o3oco     o = open room
ww4wcwc     w = wall
ococx3o     1-9 = door (costs that many coins)
```

## Run

Compile all `.java` files, then:

```bash
javac *.java
java Solve maze0.txt
# optionally pass a delay in ms between animation steps
java Solve maze1.txt 200
```

The GUI window opens automatically. Press Enter to start the animation, then again to close.

## File format

```
<scale>       # display scale factor
<width>       # rooms wide
<height>      # rooms tall
<coins>       # coins available
<maze rows>   # alternating room/wall rows
```

## Implementation

| File | Role |
|---|---|
| `Graph.java` | Adjacency-list undirected graph |
| `GraphNode` / `GraphEdge` | Node and edge with coin cost |
| `Maze.java` | Parses file, builds graph, runs DFS |
| `DrawMaze.java` | Swing visualizer, animates path |
| `Solve.java` | Entry point |

## Tech

Java · graph data structures · DFS · Java Swing
