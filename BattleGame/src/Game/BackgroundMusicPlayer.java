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

            // Clip ����
            Clip bgmClip = AudioSystem.getClip();
            bgmClip.open(audioStream);

            // �ݺ� ���
            bgmClip.loop(Clip.LOOP_CONTINUOUSLY);
            bgmClip.start(); // ��� ����
        } catch (UnsupportedAudioFileException | LineUnavailableException | IOException e) {
            e.printStackTrace();
        }
    }
}