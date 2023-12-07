
![PaintGameImg](https://github.com/guilhermeSilva96/PaintGame/assets/139381851/9dc44f3c-0a50-4246-bdf8-b714d9846f1a)


# PaintGame

## Overview

In this repository you will find a simple Paint Game, made in Java with the use of an external library called SimpleGraphics.

The commands are:
 - W,A,S,D for movement.
 - Space to Paint or Delete.
 - C will delete all the painted cells.
 - F will save to a file the current state of the game.
 - L will load from that file the last save commited.

## Java Mechanics and Principles

### Inheritance

The `Cell` class extends the `Rectangle` class, inheriting its properties and behaviors.

### Encapsulation

The `Cell` class encapsulates the state of a cell, managing its painted state and exposing methods to access and modify it.

### Polymorphism

The `draw()` method is polymorphic, being invoked differently in subclasses `Cursor` and `GridField`.

### Abstraction

Abstraction is employed by creating classes like `Cursor` and `GridField` to represent real-world concepts without exposing all the implementation details.

### File I/O

File input/output is used to save and load grid configurations in/from a text file (`grid.txt`). This involves classes like `FileReader`, `BufferedReader`, `FileWriter`, and `BufferedWriter`.

### Event Handling

The project handles keyboard events (`KeyboardHandler`) to control the cursor's movement and interaction with the grid.

### Collections

The `GridField` class extends `ArrayList<Cell>`, utilizing a collection to manage and manipulate grid cells efficiently.

### Exception Handling

Exceptions (e.g., `IOException`) are handled when dealing with file operations (`try-catch` blocks).

## Dependencies

An external library called SimpleGfx is used.

## Contributors

Guilherme Silva 💃
