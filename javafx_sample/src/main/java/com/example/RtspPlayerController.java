package com.example;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import org.bytedeco.javacv.FFmpegFrameGrabber;
import org.bytedeco.javacv.Frame;
import org.bytedeco.javacv.Java2DFrameConverter;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import javax.imageio.ImageIO;

public class RtspPlayerController {

    private static final String STREAM_URL = "rtsp://gbox3d:71021707@gears001.iptime.org:21028/stream_ch00_0";

    private FFmpegFrameGrabber grabber;
    private Java2DFrameConverter converter;

    @FXML
    private Pane paneVideo;

    private ImageView imageView;

    @FXML
    private void initialize() {
        imageView = new ImageView();
        paneVideo.getChildren().add(imageView);

        grabber = new FFmpegFrameGrabber(STREAM_URL);
        converter = new Java2DFrameConverter();
    }

    @FXML
    private void onClickPlay() {
        System.out.println("Play button clicked");
        startStream();
    }

    private void startStream() {
        new Thread(() -> {
            try {
                grabber.start();
                while (true) {
                    Frame frame = grabber.grabImage();
                    if (frame == null) {
                        break;
                    }

                    BufferedImage bufferedImage = converter.convert(frame);
                    Image image = bufferedImageToFXImage(bufferedImage);

                    Platform.runLater(() -> imageView.setImage(image));
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    grabber.stop();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private Image bufferedImageToFXImage(BufferedImage bufferedImage) {
        try {
            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(((java.awt.image.DataBufferByte) bufferedImage.getRaster().getDataBuffer()).getData());
            return new Image(byteArrayInputStream);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
