import javax.swing.SwingUtilities;

public class HelloApp {

    public String ApplicationName = "Alice";

    private int counter = 0;

    public HelloApp() {
        // 생성자
    }

    public synchronized void incrementCounter() {
        counter++;
    }

    public synchronized int getCounter() {
        return counter;
    }

    public static void main(String[] args) throws Exception {
        System.out.println("Hello, World!");

        // App 객체 생성
        HelloApp app = new HelloApp();

        // Event Dispatch Thread에서 GUI 초기화
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                MainFrame frame = new MainFrame(app);
                frame.setVisible(true);

                // 카운터를 증가시키는 스레드 시작
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        while (true) {
                            try {
                                Thread.sleep(1000);
                                app.incrementCounter();
                                SwingUtilities.invokeLater(new Runnable() {
                                    @Override
                                    public void run() {
                                        frame.updateCounter(app.getCounter());
                                    }
                                });
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }).start();

            }
        });


    }
}
