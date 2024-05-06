package views;

import animations.RepaintThread;

import javax.sound.sampled.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;

public class Window extends JFrame {
    private int titleBarHeight;
    private int width;
    private int height;
    public AnimatedScreen bgPanel;
    private Clip clip;

    public Window() throws LineUnavailableException, UnsupportedAudioFileException, IOException, InterruptedException {
        this.clip = AudioSystem.getClip();
        playMusic("avatar");
        initComponents();

        setVisible(true);
        RepaintThread thread = new RepaintThread(bgPanel);
        thread.start();

        bgPanel.transition(false, 1, 0);
    }

    private void initComponents() {
        titleBarHeight = 28;
        width = 1001;
        height = 501;

        setSize(width, height + titleBarHeight);
        setLocationRelativeTo(null);
        setBackground(Color.white);


        bgPanel = new AnimatedScreen(width, height);
        bgPanel.setBounds(0, 0, width, height);
        bgPanel.setLayout(null);
        bgPanel.setBackground(new Color(39, 43, 76));
        this.getContentPane().add(bgPanel);


       /* JButton button = new JButton();
        button.setBounds(100, 100, 100, 30);
        button.setText("Transition");
        button.addActionListener(e -> transition(e));
        bgPanel.add(button);*/
    }

    public void transition(ActionEvent e) {

    }

    public void playMusic(String song) throws UnsupportedAudioFileException, IOException, LineUnavailableException, InterruptedException {
        File songPath = new File(System.getProperty("user.dir") + "/src/audio/" + song + ".wav");

        if(songPath.exists()) {
            AudioInputStream audioInput = AudioSystem.getAudioInputStream(songPath);

            this.clip.open(audioInput);
            this.clip.start();
        }
        else {
            System.out.println(System.getProperty("user.dir"));
        }
    }

    public void stopMusic() {
        clip.close();
    }
}
