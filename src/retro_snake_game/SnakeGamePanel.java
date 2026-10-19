package retro_snake_game;

import java.awt.*;
import java.awt.event.*;
import java.util.Random;
import javax.swing.*;

public class SnakeGamePanel extends JPanel implements ActionListener, KeyListener {

    static final int SCREEN_WIDTH  = 600;
    static final int SCREEN_HEIGHT = 600;
    static final int UNIT_SIZE     = 25;         
    static final int GAME_UNITS    = (SCREEN_WIDTH * SCREEN_HEIGHT) / (UNIT_SIZE * UNIT_SIZE);
    static final int DELAY         = 100; 
    
    final int[] x = new int[GAME_UNITS];
    final int[] y = new int[GAME_UNITS];
    int bodyParts = 3;       
    int applesEaten = 0;
    int appleX;
    int appleY;
    char direction = 'R';    
    boolean running = false;
    Timer timer;
    Random random;
    
    public SnakeGamePanel() {
        random = new Random();
        this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
        this.setBackground(Color.BLACK);
        this.setFocusable(true);
        this.addKeyListener(this);
        startGame();
    }

    public void startGame() {
        running = true;
        timer = new Timer(DELAY, this);
        timer.start();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        draw(g);
    }

    public void draw(Graphics g) {
        g.setColor(Color.WHITE);
        g.drawString("Game starting...", 20, 20);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
    }

    @Override public void keyPressed(KeyEvent e) { }
    @Override public void keyReleased(KeyEvent e) { }
    @Override public void keyTyped(KeyEvent e) { }
}
