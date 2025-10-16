package Game;

import java.io.*;

import javax.sound.sampled.*;

public class BackgroundMusicPlayer implements Runnable {
    private String filePath;

    public BackgroundMusicPlayer(String filePath) {
        this.filePath = filePath;
    }

    @Override
    public void run() {
        playBackgroundMusic(filePath);
    }

    private void playBackgroundMusic(String filePath) {
        try {
            File audioFile = new File(filePath);
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(audioFile);

            // Clip 열기
            Clip bgmClip = AudioSystem.getClip();
            bgmClip.open(audioStream);

            // 반복 재생
            bgmClip.loop(Clip.LOOP_CONTINUOUSLY);
            bgmClip.start(); // 재생 시작
        } catch (UnsupportedAudioFileException | LineUnavailableException | IOException e) {
            e.printStackTrace();
        }
    }
}