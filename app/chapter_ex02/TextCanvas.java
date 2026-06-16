package chapter_ex02;

import java.io.PrintStream;
import java.util.Arrays;

public class TextCanvas {

    private static final String ESC = "\u001B[";

    private final int width;
    private final int height;
    private final int originX;
    private final int originY;
    private final PrintStream out;
    private final char[][] cells;

    public TextCanvas(int width, int height) {
        this(width, height, 1, 1);
    }

    public TextCanvas(int width, int height, int originX, int originY) {
        this(width, height, originX, originY, System.out);
    }

    public TextCanvas(int width, int height, int originX, int originY, PrintStream out) {
        if (width <= 0 || height <= 0) {
            throw new IllegalArgumentException("width and height must be positive");
        }
        if (originX <= 0 || originY <= 0) {
            throw new IllegalArgumentException("originX and originY are terminal positions, so they start at 1");
        }

        this.width = width;
        this.height = height;
        this.originX = originX;
        this.originY = originY;
        this.out = out;
        this.cells = new char[height][width];

        clearBuffer();
    }

    public void clearTerminal() {
        out.print(ESC + "2J");
        moveTerminalCursor(1, 1);
        out.flush();
    }

    public void hideCursor() {
        out.print(ESC + "?25l");
        out.flush();
    }

    public void showCursor() {
        out.print(ESC + "?25h");
        out.flush();
    }

    public void clear() {
        clearBuffer();
        renderAll();
    }

    public void clearBuffer() {
        fill(' ');
    }

    public void fill(char value) {
        for (char[] row : cells) {
            Arrays.fill(row, value);
        }
    }

    public void writeCell(int x, int y, char value) {
        requireInside(x, y);
        cells[y][x] = value;
    }

    public void setCell(int x, int y, char value) {
        writeCell(x, y, value);
        renderCell(x, y);
    }

    public char getCell(int x, int y) {
        requireInside(x, y);
        return cells[y][x];
    }

    public void renderAll() {
        for (int y = 0; y < height; y++) {
            moveTerminalCursor(originX, originY + y);
            out.print(cells[y]);
        }
        out.flush();
    }

    public void renderCell(int x, int y) {
        requireInside(x, y);
        moveTerminalCursor(originX + x, originY + y);
        out.print(cells[y][x]);
        out.flush();
    }

    public void moveCursorBelowCanvas() {
        moveTerminalCursor(1, originY + height + 1);
        out.flush();
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    private void requireInside(int x, int y) {
        if (x < 0 || x >= width || y < 0 || y >= height) {
            throw new IndexOutOfBoundsException("x and y must be inside the canvas");
        }
    }

    private void moveTerminalCursor(int x, int y) {
        out.print(ESC + y + ";" + x + "H");
    }
}
