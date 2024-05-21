package exam02;

import java.awt.*;

import javax.swing.*;


public class MainFrame extends JFrame {

    public MainFrame() {
        setTitle("Main Frame");
        
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Container contentPane = getContentPane();

        contentPane.setBackground(Color.PINK);
        contentPane.setLayout(new FlowLayout());

        contentPane.add(new JButton("OK"));
        contentPane.add(new JButton("Cancel"));
        contentPane.add(new JButton("Ignore"));

        setSize(300, 200);

        //set window position
        setLocation(100, 100);


        setVisible(true);
    }

    public static void main(String[] args) {
        // System.out.println("Hello, World!");
        MainFrame myFrame = new MainFrame();
    }
    
}
