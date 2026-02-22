package exam01;

import javax.swing.*;

public class mainFrame extends JFrame{
    public mainFrame() {
        setTitle("Main Frame");
        setSize(300, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    public static void main(String[] args) {
        // System.out.println("Hello, World!");
        new mainFrame();
    }
}