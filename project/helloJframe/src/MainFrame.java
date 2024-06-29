import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainFrame extends JFrame {
    private JLabel label;
    private JButton button;

    private JLabel counterLabel;

    private HelloApp context;

    public MainFrame( HelloApp context) {

        this.context = context;

        // 설정
        setTitle("Simple GUI Application");
        setSize(300, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new FlowLayout());

        // 컴포넌트 초기화
        label = new JLabel("Hello, World! " + context.ApplicationName );
        counterLabel = new JLabel("Counter: 0");
        button = new JButton("Press Me");

        // 버튼 이벤트 리스너 추가
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                label.setText("버튼을 눌렀습니다");
            }
        });

        // 컴포넌트를 프레임에 추가
        add(label);
        add(counterLabel);
        add(button);
    }

    public void updateCounter(int counter) {
        counterLabel.setText("Counter: " + counter);
    }
}
