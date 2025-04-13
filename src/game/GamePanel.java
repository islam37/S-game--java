package game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class GamePanel extends JPanel implements ActionListener, GameConstants {
    private Snake snake;
    private Food food;
    private Timer timer;
    private boolean isRunning;
    private int score;

    public GamePanel() {
        setPreferredSize(new Dimension(GRID_WIDTH * TILE_SIZE, GRID_HEIGHT * TILE_SIZE));
        setBackground(Color.BLACK);
        setFocusable(true);
        
        snake = new Snake(GRID_WIDTH / 2, GRID_HEIGHT / 2);
        food = new Food();
        timer = new Timer(GAME_SPEED, this);
        isRunning = true;
        score = 0;
        
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_UP -> snake.setDirection(Direction.UP);
                    case KeyEvent.VK_DOWN -> snake.setDirection(Direction.DOWN);
                    case KeyEvent.VK_LEFT -> snake.setDirection(Direction.LEFT);
                    case KeyEvent.VK_RIGHT -> snake.setDirection(Direction.RIGHT);
                    case KeyEvent.VK_SPACE -> {
                        if (!isRunning) {
                            resetGame();
                        }
                    }
                }
            }
        });
        
        timer.start();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        // Draw grid (optional)
        g.setColor(new Color(30, 30, 30));
        for (int x = 0; x < GRID_WIDTH; x++) {
            for (int y = 0; y < GRID_HEIGHT; y++) {
                g.drawRect(x * TILE_SIZE, y * TILE_SIZE, TILE_SIZE, TILE_SIZE);
            }
        }
        
        // Draw game objects
        snake.draw(g);
        food.draw(g);
        
        // Draw score
        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 20));
        g.drawString("Score: " + score, 10, 20);
        
        // Draw game over message
        if (!isRunning) {
            g.setColor(Color.RED);
            g.setFont(new Font("Arial", Font.BOLD, 40));
            String message = "Game Over! Score: " + score;
            int messageWidth = g.getFontMetrics().stringWidth(message);
            g.drawString(message, (getWidth() - messageWidth) / 2, getHeight() / 2);
            
            g.setFont(new Font("Arial", Font.PLAIN, 20));
            String restartMsg = "Press SPACE to restart";
            int restartWidth = g.getFontMetrics().stringWidth(restartMsg);
            g.drawString(restartMsg, (getWidth() - restartWidth) / 2, getHeight() / 2 + 40);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (isRunning) {
            snake.move();
            
            if (snake.checkCollision()) {
                isRunning = false;
                timer.stop();
            }
            
            if (snake.eats(food)) {
                snake.grow();
                food.respawn();
                score += 10;
            }
            
            repaint();
        }
    }

    private void resetGame() {
        snake = new Snake(GRID_WIDTH / 2, GRID_HEIGHT / 2);
        food.respawn();
        isRunning = true;
        score = 0;
        timer.start();
        repaint();
    }
}