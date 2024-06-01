import com.fazecast.jSerialComm.SerialPort;

public class App {

    public static void main(String[] args) {
        // 시리얼 포트 찾기
        SerialPort[] ports = SerialPort.getCommPorts();
        if (ports.length == 0) {
            System.out.println("No serial ports found.");
        } else {
            System.out.println("Available serial ports:");
            for (int i = 0; i < ports.length; i++) {
                System.out.println("[" + i + "] " + ports[i].getSystemPortName());
            }
        }
    }
}
    // public static void main(String[] args) {
    //     // 시리얼 포트 찾기
    //     SerialPort[] ports = SerialPort.getCommPorts();
    //     for (int i = 0; i < ports.length; i++) {
    //         System.out.println("[" + i + "] " + ports[i].getSystemPortName());
    //     }
        
    //     // 시리얼 포트 선택 (아두이노가 연결된 포트를 선택하세요)
    //     SerialPort serialPort = ports[0];
    //     serialPort.setBaudRate(9600);
        
    //     if (serialPort.openPort()) {
    //         System.out.println("Port opened successfully.");
    //     } else {
    //         System.out.println("Failed to open port.");
    //         return;
    //     }
        
    //     // 데이터를 보내기 위한 쓰레드
    //     Thread sendThread = new Thread(() -> {
    //         try {
    //             while (true) {
    //                 String data = "Hello, Arduino!";
    //                 serialPort.getOutputStream().write((data + "\n").getBytes());
    //                 serialPort.getOutputStream().flush();
    //                 System.out.println("Sent: " + data);
    //                 Thread.sleep(1000); // 1초마다 데이터 전송
    //             }
    //         } catch (Exception e) {
    //             e.printStackTrace();
    //         }
    //     });
        
    //     // 데이터를 받기 위한 쓰레드
    //     Thread receiveThread = new Thread(() -> {
    //         try {
    //             while (true) {
    //                 byte[] buffer = new byte[1024];
    //                 int numRead = serialPort.getInputStream().read(buffer);
    //                 String received = new String(buffer, 0, numRead);
    //                 System.out.println("Received: " + received);
    //             }
    //         } catch (Exception e) {
    //             e.printStackTrace();
    //         }
    //     });
        
    //     sendThread.start();
    //     receiveThread.start();
    // }
// }
