package game;

import java.awt.*;
import java.util.Random;

public class Food extends GameObject {
    public Food() {
        super(0, 0, Color.RED);
        respawn();
    }

    public void respawn() {
        Random random = new Random();
        this.x = random.nextInt(GameConstants.GRID_WIDTH);
        this.y = random.nextInt(GameConstants.GRID_HEIGHT);
    }

    @Override
    public void draw(Graphics g) {
        g.setColor(color);
        g.fillOval(x * GameConstants.TILE_SIZE, 
                   y * GameConstants.TILE_SIZE, 
                   GameConstants.TILE_SIZE, 
                   GameConstants.TILE_SIZE);
    }
}