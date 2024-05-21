package exam01;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

public class MainFrame extends JFrame {
    public MainFrame() {
        setTitle("BorderLayout Practice");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Container c = getContentPane();
        c.setLayout(new FlowLayout());
        JButton btn1 = new JButton("Button 1");

        btn1.addActionListener( new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JButton btn = (JButton)e.getSource();
                btn.setText("Clicked");
            }
        });

        c.add(btn1);
        

        setSize(300, 200);
        setVisible(true);
    }

    public static void main(String[] args) {
        new MainFrame();
    }
    
}
