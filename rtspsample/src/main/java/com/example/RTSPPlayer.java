package com.example;

// ... (나머지 코드)
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import uk.co.caprica.vlcj.factory.MediaPlayerFactory;
import uk.co.caprica.vlcj.player.embedded.EmbeddedMediaPlayer;
// import uk.co.caprica.vlcj.player.embedded.videosurface.VideoSurface;
// import uk.co.caprica.vlcj.javafx.videosurface.ImageViewVideoSurface;

import java.io.IOException;

public class RTSPPlayer extends Application {

    // private static final String STREAM_URL = "rtsp://gbox3d:71021707@gears001.iptime.org:21028/stream_ch00_0";

    // private final MediaPlayerFactory mediaPlayerFactory;
    // private final EmbeddedMediaPlayer embeddedMediaPlayer;

    public RTSPPlayer() {
        // this.mediaPlayerFactory = new MediaPlayerFactory();
        // this.embeddedMediaPlayer = mediaPlayerFactory.mediaPlayers().newEmbeddedMediaPlayer();
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(RTSPPlayer.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        StackPane root = new StackPane();
        // Scene scene = new Scene(root, 640, 480);

        Scene scene = new Scene(loadFXML("main"), 600, 480);
        primaryStage.setScene(scene);
        primaryStage.show();

        // javafx.scene.image.ImageView imageView = new javafx.scene.image.ImageView();
        // imageView.fitWidthProperty().bind(scene.widthProperty());
        // imageView.fitHeightProperty().bind(scene.heightProperty());
        // root.getChildren().add(imageView);

        // VideoSurface videoSurface = new ImageViewVideoSurface(imageView);
        // embeddedMediaPlayer.videoSurface().set(videoSurface);

        // primaryStage.setTitle("RTSP Player");
        // primaryStage.setScene(scene);
        // primaryStage.show();

        // embeddedMediaPlayer.media().play(STREAM_URL);
    }

    @Override
    public void stop() {
        // embeddedMediaPlayer.release();
        // mediaPlayerFactory.release();
    }

    public static void main(String[] args) {
        launch(args);
    }
}