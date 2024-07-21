package com.example;

import javafx.fxml.FXML;
import javafx.scene.layout.Pane;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
// import javafx.scene.Scene;
// import javafx.stage.Stage;
import uk.co.caprica.vlcj.factory.MediaPlayerFactory;
import uk.co.caprica.vlcj.player.base.MediaPlayer;
import uk.co.caprica.vlcj.player.base.MediaPlayerEventAdapter;
import uk.co.caprica.vlcj.player.embedded.EmbeddedMediaPlayer;
import uk.co.caprica.vlcj.player.embedded.videosurface.VideoSurface;
import uk.co.caprica.vlcj.javafx.videosurface.ImageViewVideoSurface;

// import java.io.IOException;

public class mainController {

    @FXML
    private Pane videoPane;


    private static final String STREAM_URL = "rtsp://gbox3d:71021707@gears001.iptime.org:21028/stream_ch00_0";

    private MediaPlayerFactory mediaPlayerFactory;
    private EmbeddedMediaPlayer embeddedMediaPlayer;

    @FXML
    public void initialize() {
        // Initialize the media player factory and the embedded media player
        mediaPlayerFactory = new MediaPlayerFactory();
        embeddedMediaPlayer = mediaPlayerFactory.mediaPlayers().newEmbeddedMediaPlayer();

        // Create and configure the ImageView for video playback
        ImageView imageView = new ImageView();
        imageView.fitWidthProperty().bind(videoPane.widthProperty());
        imageView.fitHeightProperty().bind(videoPane.heightProperty());

        // Add the ImageView to the videoPane
        videoPane.getChildren().add(imageView);

        // Setup the video surface to render the video onto the ImageView
        VideoSurface videoSurface = new ImageViewVideoSurface(imageView);
        embeddedMediaPlayer.videoSurface().set(videoSurface);

        // 이벤트 리스너 추가
        embeddedMediaPlayer.events().addMediaPlayerEventListener(new MediaPlayerEventAdapter() {
            @Override
            public void playing(MediaPlayer mediaPlayer) {
                System.out.println("Media is playing");
                btnPlay.setDisable(false);
                // 여기서 재생 시작 시 작업을 처리할 수 있습니다.
            }

            @Override
            public void paused(MediaPlayer mediaPlayer) {
                System.out.println("Media is paused");
                // 여기서 일시 정지 시 작업을 처리할 수 있습니다.
            }

            @Override
            public void stopped(MediaPlayer mediaPlayer) {
                System.out.println("Media is stopped");
                // 여기서 정지 시 작업을 처리할 수 있습니다.
            }

            @Override
            public void finished(MediaPlayer mediaPlayer) {
                System.out.println("Media is finished");
                // 여기서 재생 완료 시 작업을 처리할 수 있습니다.
            }

            @Override
            public void error(MediaPlayer mediaPlayer) {
                System.out.println("An error occurred");
                // 여기서 오류 발생 시 작업을 처리할 수 있습니다.
            }
        });
    }

    @FXML
    private Button btnPlay;

    @FXML
    private void onClickPlay() {
        if(embeddedMediaPlayer.status().isPlaying()) {
            // System.out.println("Media is already playing");
            // return;
            embeddedMediaPlayer.controls().stop();
            btnPlay.setText("Play");
        }
        else {
            // System.out.println("Play button clicked");
            embeddedMediaPlayer.media().play(STREAM_URL);
            btnPlay.setDisable(true);
            btnPlay.setText("Stop");
        }

        // System.out.println("Play button clicked");
        // embeddedMediaPlayer.media().play(STREAM_URL);
    }

    public void stop() {
        // Release media player and factory resources
        if (embeddedMediaPlayer != null) {
            embeddedMediaPlayer.release();
        }
        if (mediaPlayerFactory != null) {
            mediaPlayerFactory.release();
        }
    }
    
}
