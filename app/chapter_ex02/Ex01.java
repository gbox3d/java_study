package chapter_ex02;

public class Ex01 {

    public static void main(String[] args) throws InterruptedException {
        TextCanvas canvas = new TextCanvas(8, 8, 4, 2);

        try {
            canvas.clearTerminal();
            canvas.hideCursor();

            canvas.writeCell(3, 2, 'A');
            canvas.writeCell(0, 0, '#');
            canvas.writeCell(7, 7, '@');
            canvas.renderAll();

            Thread.sleep(600);

            for (int x = 0; x < canvas.getWidth(); x++) {
                canvas.setCell(x, 4, '*');
                Thread.sleep(120);
            }
        } finally {
            canvas.showCursor();
            canvas.moveCursorBelowCanvas();
        }
    }
}
