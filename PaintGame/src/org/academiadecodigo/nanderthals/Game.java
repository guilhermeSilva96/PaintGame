package org.academiadecodigo.nanderthals;

import org.academiadecodigo.simplegraphics.keyboard.Keyboard;
import org.academiadecodigo.simplegraphics.keyboard.KeyboardEvent;
import org.academiadecodigo.simplegraphics.keyboard.KeyboardEventType;
import org.academiadecodigo.simplegraphics.keyboard.KeyboardHandler;

import java.io.*;

public class Game implements KeyboardHandler {
    private int columns;
    private int rows;
    public static final int cellSize = 25;
    public static final int PADDING = 10;
    private GridField gridField;
    private Cursor cursor;
    private Keyboard keyboard;
    private final String filePath = "grid.txt";
    private boolean spacePressed;

    public Game(int columns, int rows) {
        this.columns = columns;
        this.rows = rows;

        keyboard = new Keyboard(this);
        keyboardEvents();
    }

    public void start() {
        this.gridField = new GridField(columns, rows);
        this.cursor = new Cursor();

        initializeCursor();
    }

    private void keyboardEvents() {
        KeyboardEvent left = new KeyboardEvent();
        left.setKey(KeyboardEvent.KEY_A);
        left.setKeyboardEventType(KeyboardEventType.KEY_PRESSED);

        KeyboardEvent right = new KeyboardEvent();
        right.setKey(KeyboardEvent.KEY_D);
        right.setKeyboardEventType(KeyboardEventType.KEY_PRESSED);

        KeyboardEvent up = new KeyboardEvent();
        up.setKey(KeyboardEvent.KEY_W);
        up.setKeyboardEventType(KeyboardEventType.KEY_PRESSED);

        KeyboardEvent down = new KeyboardEvent();
        down.setKey(KeyboardEvent.KEY_S);
        down.setKeyboardEventType(KeyboardEventType.KEY_PRESSED);

        KeyboardEvent space = new KeyboardEvent();
        space.setKey(KeyboardEvent.KEY_SPACE);
        space.setKeyboardEventType(KeyboardEventType.KEY_PRESSED);

        KeyboardEvent spaceReleased = new KeyboardEvent();
        spaceReleased.setKey(KeyboardEvent.KEY_SPACE);
        spaceReleased.setKeyboardEventType(KeyboardEventType.KEY_RELEASED);

        KeyboardEvent clear = new KeyboardEvent();
        clear.setKey(KeyboardEvent.KEY_C);
        clear.setKeyboardEventType(KeyboardEventType.KEY_PRESSED);

        KeyboardEvent save = new KeyboardEvent();
        save.setKey(KeyboardEvent.KEY_F);
        save.setKeyboardEventType(KeyboardEventType.KEY_PRESSED);

        KeyboardEvent load = new KeyboardEvent();
        load.setKey(KeyboardEvent.KEY_L);
        load.setKeyboardEventType(KeyboardEventType.KEY_PRESSED);


        keyboard.addEventListener(left);
        keyboard.addEventListener(right);
        keyboard.addEventListener(up);
        keyboard.addEventListener(down);
        keyboard.addEventListener(space);
        keyboard.addEventListener(clear);
        keyboard.addEventListener(spaceReleased);
        keyboard.addEventListener(save);
        keyboard.addEventListener(load);
    }

    private void initializeCursor() {
        cursor.draw(PADDING, PADDING);
    }

    @Override
    public void keyPressed(KeyboardEvent event) {
        switch (event.getKey()) {
            case KeyboardEvent.KEY_A:
                if (cursor.getX() > PADDING) {
                    cursor.moveLeft();
                    if (spacePressed) {
                        toggleCellPainted(cursor.getX(), cursor.getY());
                    }
                }
                break;
            case KeyboardEvent.KEY_D:
                if (cursor.getX() < (columns - 1) * cellSize) {
                    cursor.moveRight();
                    if (spacePressed) {
                        toggleCellPainted(cursor.getX(), cursor.getY());
                    }
                }
                break;
            case KeyboardEvent.KEY_W:
                if (cursor.getY() > PADDING) {
                    cursor.moveUp();
                    if (spacePressed) {
                        toggleCellPainted(cursor.getX(), cursor.getY());
                    }
                }
                break;
            case KeyboardEvent.KEY_S:
                if (cursor.getY() < (rows - 1) * cellSize) {
                    cursor.moveDown();
                    if (spacePressed) {
                        toggleCellPainted(cursor.getX(), cursor.getY());
                    }
                }
                break;
            case KeyboardEvent.KEY_SPACE:
                spacePressed = true;
                int col = cursor.getX();
                int row = cursor.getY();
                toggleCellPainted(col, row);
                break;
            case KeyboardEvent.KEY_C:
                deleteGridField();
                break;
            case KeyboardEvent.KEY_F:
                saveToFile();
                break;
            case KeyboardEvent.KEY_L:
                loadFromFile();
                break;
        }
    }

    @Override
    public void keyReleased(KeyboardEvent event) {
        if (event.getKey() == KeyboardEvent.KEY_SPACE) {
            spacePressed = false;
        }
    }

    private void toggleCellPainted(int col, int row) {
        this.gridField.paintCell(col, row);
    }

    private void deleteGridField() {
        this.gridField.clearGrid();
    }

    private void loadFromFile() {
        int col = 0, row = 0;
        try {
            FileReader fileReader = new FileReader(filePath);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String line = "";
            while ((line = bufferedReader.readLine()) != null) {
                for (String s : line.split(" ")) {
                    if (s.compareTo("true") == 0) {
                        toggleCellPainted(convertToPixel(col), convertToPixel(row));
                    }
                    row = (row < rows - 1) ? (row + 1) : 0;
                }
                col = (col < columns - 1) ? (col + 1) : 0;
            }
            bufferedReader.close();
            fileReader.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void saveToFile() {
        Cell cellAux = this.gridField.get(0);
        try {
            FileWriter fileWriter = new FileWriter(filePath);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

            for (Cell c : this.gridField) {

                if (cellAux.getX() != c.getX()) {
                    bufferedWriter.newLine();
                }

                bufferedWriter.write(c.isPainted() + " ");
                cellAux = c;
            }
            bufferedWriter.close();
            fileWriter.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private int convertToPixel(int num) {
        return (num * cellSize + PADDING);
    }
}

