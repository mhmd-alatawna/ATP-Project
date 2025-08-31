MAZE GENERATOR & SOLVER — README

OVERVIEW
This repository contains a multi-part project on maze generation and pathfinding. The “partC” branch is the final, integrated version with all features (generators, solvers, JavaFX UI, client–server mode, file I/O, and configuration).

KEY FEATURES

* Generators: from simple randomized methods to graph-based algorithms. The default uses a Kruskal-style spanning-tree approach to guarantee at least one valid exit path.
* Solvers: BFS, DFS, and Dijkstra. Mazes are adapted via a “SearchableMaze” adapter so algorithms operate on a graph abstraction.
* UI (JavaFX): draw and play with mazes; save/load maze files; optional custom audio; straightforward controls.
* Client–Server mode: run a Generator Server and a Solver Server; the client picks a strategy (which algorithm to use) at runtime.
* Extensible design: heavy use of Strategy, Adapter, Dependency Injection , clean MVC-style separation and many more well known design patterns for easy expansion.

HOW TO RUN

1. Checkout final build:
   git checkout partC
2. Start servers (in any order):

   * Generator Server — required before generating mazes
   * Solver Server — start if you want automatic solutions
     (Ports/host are configurable in the app settings; start/stop buttons are provided.)
3. Launch the JavaFX app:

   * Generate a maze (choose generator strategy).
   * Solve (choose BFS/DFS/Dijkstra) or play it yourself.
   * Save/export or load existing maze files.

TIP
If you click “Run Solver,” ensure the Solver Server is running; otherwise start it from the UI.

DESIGN NOTES

* Strategy Pattern: swap algorithms (generator/solver) without changing UI or service code.
* Adapter Pattern: SearchableMaze converts a grid to a graph; solvers see a generic search problem (path from start to goal).
* Dependency Injection: decouples components for testing and future features (e.g., weighted mazes or new heuristics).

FILE I/O

* Save mazes to a custom maze file format.
* Load files back into the UI or send them to the Solver Server.

BRANCHES

* partA : includes all the basic logic needed for mazes , maze generators and maze solvers .
* partB: add compressors and decompressors to the project for file I/O as well as server and client support with customizable features
* partC: final, feature-complete project which includes a full javaFX tunning application with support to all features descriped before.

GETTING HELP

* Start the Generator Server before generating.
* To auto-solve, start the Solver Server, then select a solver strategy and run.
* Check app settings for host/port and algorithm selection.
