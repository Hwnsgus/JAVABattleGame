package Game;

import java.io.*;

import javax.sound.sampled.*;

public class SoundEffectPlayer implements Runnable {
    private String filePath;

    public SoundEffectPlayer(String filePath) {
        this.filePath = filePath;
    }

    @Override
    public void run() {
        playHitSound(filePath);
    }

    public void playHitSound(String filePath) {
        try {
            File audioFile = new File(filePath);
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(audioFile);

            Clip clip = AudioSystem.getClip();
            clip.open(audioStream);

            clip.start(); // 소리 재생
        } catch (UnsupportedAudioFileException | LineUnavailableException | IOException e) {
            e.printStackTrace();
        }
    }
}