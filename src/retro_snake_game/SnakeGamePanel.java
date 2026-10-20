package retro_snake_game;

import java.awt.*;
import java.awt.event.*;
import java.util.Random;
import javax.swing.*;

public class SnakeGamePanel extends JPanel implements ActionListener, KeyListener {

    // CHANGED: Fit exactly inside your jPanel3 window!
    static final int SCREEN_WIDTH  = 650;
    static final int SCREEN_HEIGHT = 400;
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

    private Game_panel parentWindow;

    public SnakeGamePanel(Game_panel parentWindow) {
        this.parentWindow = parentWindow;
        random = new Random();
        this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
        this.setBackground(new Color(51, 153, 0)); 
        
        this.setFocusable(true);
        this.addKeyListener(this);
        this.requestFocusInWindow();

        startGame();
    }

    public void startGame() {
        for (int i = 0; i < bodyParts; i++) {
            x[i] = 100 - i * UNIT_SIZE;
            y[i] = 200; 
        }

        // SET INITIAL SCORE TO "00"
        if (parentWindow != null && parentWindow.jLabel3 != null) {
            parentWindow.jLabel3.setText("00");
        }

        newApple(); 
        
        running = true;
        timer = new Timer(DELAY, this);
        timer.start();
    }
    
    public void newApple() {
        boolean onSnake = true;
        while (onSnake) {
            appleX = random.nextInt(SCREEN_WIDTH / UNIT_SIZE) * UNIT_SIZE;
            appleY = random.nextInt(SCREEN_HEIGHT / UNIT_SIZE) * UNIT_SIZE;
            
            onSnake = false;
            for (int i = 0; i < bodyParts; i++) {
                if (x[i] == appleX && y[i] == appleY) {
                    onSnake = true; 
                    break;
                }
            }
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        draw(g);
    }

    public void draw(Graphics g) {
        if (running) {
            // Draw Mouse (RED)
            g.setColor(Color.RED);
            g.fillRect(appleX, appleY, UNIT_SIZE, UNIT_SIZE);

            // Draw Snake (BLACK)
            g.setColor(Color.BLACK);
            for (int i = 0; i < bodyParts; i++) {
                g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
            }
        } else {
            gameOver(g);
        }
    }

    public void gameOver(Graphics g) {                  
        g.setColor(Color.RED);
        g.setFont(new Font("Arial", Font.BOLD, 50));
        FontMetrics metrics = getFontMetrics(g.getFont());
        g.drawString("GAME OVER", 
            (SCREEN_WIDTH - metrics.stringWidth("GAME OVER")) / 2, 
            SCREEN_HEIGHT / 2);
            
        // SHOW THE SCORE ON GAME OVER
        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 30));
        FontMetrics metrics2 = getFontMetrics(g.getFont());
        String finalScore = "Score: " + String.format("%02d", applesEaten);
        g.drawString(finalScore, 
            (SCREEN_WIDTH - metrics2.stringWidth(finalScore)) / 2, 
            SCREEN_HEIGHT / 2 + 50);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (running) {
            move();
            checkApple();
            checkCollisions();
        }
        repaint();
    }

    public void move() {                                  
        for (int i = bodyParts - 1; i > 0; i--) {
            x[i] = x[i - 1];
            y[i] = y[i - 1];
        }

        switch (direction) {
            case 'U': 
                y[0] -= UNIT_SIZE; 
                if (y[0] < 0) y[0] = SCREEN_HEIGHT - UNIT_SIZE;
                break;
            case 'D': 
                y[0] += UNIT_SIZE; 
                if (y[0] >= SCREEN_HEIGHT) y[0] = 0;
                break;
            case 'L': 
                x[0] -= UNIT_SIZE; 
                if (x[0] < 0) x[0] = SCREEN_WIDTH - UNIT_SIZE;
                break;
            case 'R': 
                x[0] += UNIT_SIZE; 
                if (x[0] >= SCREEN_WIDTH) x[0] = 0;
                break;
        }
    }
    
    public void checkApple() {
        if (x[0] == appleX && y[0] == appleY) {
            bodyParts++;
            applesEaten++;
            
            // UPDATE THE JLABLE3 WITH "00" FORMAT
            if (parentWindow != null && parentWindow.jLabel3 != null) {
                parentWindow.jLabel3.setText(String.format("%02d", applesEaten));
            }
            
            newApple();
        }
    }

    public void checkCollisions() {
        for (int i = bodyParts; i > 0; i--) {
            if (x[0] == x[i] && y[0] == y[i]) {
                running = false;
                break;
            }
        }
        if (!running) timer.stop();
    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_LEFT:
                if (direction != 'R') direction = 'L';
                break;
            case KeyEvent.VK_RIGHT:
                if (direction != 'L') direction = 'R';
                break;
            case KeyEvent.VK_UP:
                if (direction != 'D') direction = 'U';
                break;
            case KeyEvent.VK_DOWN:
                if (direction != 'U') direction = 'D';
                break;
        }
    }
    
    @Override public void keyReleased(KeyEvent e) { }
    @Override public void keyTyped(KeyEvent e) { }
}