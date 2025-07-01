package org.example.atpprojectpartc.View;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

import java.io.File;
import java.util.HashMap;

public class MediaManager {
    private static HashMap<String , MediaPlayer> mediaPlayers = new HashMap<>();

    public static void playAudioRepeat(Media media) {
        if (media != null) {
            if(mediaPlayers.containsKey(media.toString())){
                MediaPlayer mediaPlayer = mediaPlayers.get(media.toString());
                mediaPlayer.play();
            }else {
                MediaPlayer mediaPlayer = new MediaPlayer(media);
                mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
                mediaPlayers.put(media.toString(), mediaPlayer);
                mediaPlayer.play();
            }
        }
    }

    public static void playAudio(Media media) {
        if (media != null) {
            if(mediaPlayers.containsKey(media.toString())){
                MediaPlayer mediaPlayer = mediaPlayers.get(media.toString());
                mediaPlayer.seek(Duration.ZERO);
                mediaPlayer.play();
            }else {
                MediaPlayer mediaPlayer = new MediaPlayer(media);
                mediaPlayers.put(media.toString(), mediaPlayer);
                mediaPlayer.play();
            }
        }
    }

    public static void stop(Media media) {
        if(mediaPlayers.containsKey(media.toString()))
            mediaPlayers.get(media.toString()).stop();
    }

    public static void stopAll() {
        for(MediaPlayer mediaPlayer : mediaPlayers.values()){
            mediaPlayer.stop();
        }
    }
}
