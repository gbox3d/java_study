package com.example;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import org.opencv.core.Mat;
import org.opencv.imgproc.Imgproc;
import org.opencv.videoio.VideoCapture;
import org.opencv.videoio.Videoio;
import org.opencv.core.Core;
import javafx.scene.image.Image;
import java.io.ByteArrayInputStream;
import org.opencv.core.MatOfByte;
import org.opencv.imgcodecs.Imgcodecs;
import nu.pattern.OpenCV;

public class RTSPPlayer extends Application {

    private static final String RTSP_URL = "rtsp://gbox3d:71021707@gears001.iptime.org:21028/stream_ch00_0";
    private ImageView imageView;
    private VideoCapture capture;

    @Override
    public void start(Stage primaryStage) {
        // OpenCV.loadShared(); // OpenCV 네이티브 라이브러리 로드
        nu.pattern.OpenCV.loadLocally();
        System.out.println("OpenCV version: " + Core.VERSION); // OpenCV 버전 출력

        imageView = new ImageView();
        StackPane root = new StackPane(imageView);
        Scene scene = new Scene(root, 640, 480);

        primaryStage.setTitle("RTSP Player");
        primaryStage.setScene(scene);
        primaryStage.show();

        startVideoStream();
    }

    private void startVideoStream() {
        capture = new VideoCapture();
        capture.set(Videoio.CAP_PROP_OPEN_TIMEOUT_MSEC, 5000);
        boolean isOpened = capture.open(RTSP_URL);
        System.out.println("RTSP stream opened: " + isOpened); // 스트림 열기 여부 출력

        if (!isOpened) {
            System.out.println("Error opening RTSP stream.");
            return;
        }

        Thread videoThread = new Thread(() -> {
            Mat frame = new Mat();
            while (true) {
                boolean hasFrame = capture.read(frame);
                System.out.println("Frame captured: " + hasFrame); // 프레임 캡처 여부 출력
                if (hasFrame) {
                    Image image = mat2Image(frame);
                    Platform.runLater(() -> imageView.setImage(image));
                } else {
                    System.out.println("No frame captured");
                }
            }
        });
        videoThread.setDaemon(true);
        videoThread.start();
    }

    private Image mat2Image(Mat frame) {
        MatOfByte buffer = new MatOfByte();
        Imgcodecs.imencode(".png", frame, buffer);
        return new Image(new ByteArrayInputStream(buffer.toArray()));
    }

    @Override
    public void stop() {
        if (capture != null) {
            capture.release();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
