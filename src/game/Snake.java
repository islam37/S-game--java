package game;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Snake extends GameObject implements Movable {
    private List<Point> body;
    private Direction direction;
    private int growth;

    public Snake(int x, int y) {
        super(x, y, Color.GREEN);
        this.body = new ArrayList<>();
        this.body.add(new Point(x, y));
        this.direction = Direction.RIGHT;
        this.growth = 0;
    }

    @Override
    public void move() {
        Point head = body.get(0);
        Point newHead = new Point(head);

        switch (direction) {
            case UP -> newHead.y--;
            case DOWN -> newHead.y++;
            case LEFT -> newHead.x--;
            case RIGHT -> newHead.x++;
        }

        body.add(0, newHead);
        
        if (growth > 0) {
            growth--;
        } else {
            body.remove(body.size() - 1);
        }
    }

    public void grow() {
        growth += 3;
    }

    public void setDirection(Direction direction) {
        if ((this.direction == Direction.UP && direction != Direction.DOWN) ||
            (this.direction == Direction.DOWN && direction != Direction.UP) ||
            (this.direction == Direction.LEFT && direction != Direction.RIGHT) ||
            (this.direction == Direction.RIGHT && direction != Direction.LEFT)) {
            this.direction = direction;
        }
    }

    public boolean checkCollision() {
        Point head = body.get(0);
        
        if (head.x < 0 || head.x >= GameConstants.GRID_WIDTH || 
            head.y < 0 || head.y >= GameConstants.GRID_HEIGHT) {
            return true;
        }
        
        for (int i = 1; i < body.size(); i++) {
            if (head.equals(body.get(i))) {
                return true;
            }
        }
        
        return false;
    }

    public boolean eats(Food food) {
        return body.get(0).equals(new Point(food.getX(), food.getY()));
    }

    @Override
    public void draw(Graphics g) {
        g.setColor(color);
        for (Point p : body) {
            g.fillRect(p.x * GameConstants.TILE_SIZE, 
                      p.y * GameConstants.TILE_SIZE, 
                      GameConstants.TILE_SIZE, 
                      GameConstants.TILE_SIZE);
        }
        
        g.setColor(Color.BLACK);
        Point head = body.get(0);
        int eyeSize = GameConstants.TILE_SIZE / 5;
        
        if (direction == Direction.RIGHT || direction == Direction.LEFT) {
            g.fillOval(head.x * GameConstants.TILE_SIZE + (direction == Direction.RIGHT ? GameConstants.TILE_SIZE - eyeSize * 2 : eyeSize), 
                      head.y * GameConstants.TILE_SIZE + eyeSize, 
                      eyeSize, eyeSize);
            g.fillOval(head.x * GameConstants.TILE_SIZE + (direction == Direction.RIGHT ? GameConstants.TILE_SIZE - eyeSize * 2 : eyeSize), 
                      head.y * GameConstants.TILE_SIZE + GameConstants.TILE_SIZE - eyeSize * 2, 
                      eyeSize, eyeSize);
        } else {
            g.fillOval(head.x * GameConstants.TILE_SIZE + eyeSize, 
                      head.y * GameConstants.TILE_SIZE + (direction == Direction.DOWN ? GameConstants.TILE_SIZE - eyeSize * 2 : eyeSize), 
                      eyeSize, eyeSize);
            g.fillOval(head.x * GameConstants.TILE_SIZE + GameConstants.TILE_SIZE - eyeSize * 2, 
                      head.y * GameConstants.TILE_SIZE + (direction == Direction.DOWN ? GameConstants.TILE_SIZE - eyeSize * 2 : eyeSize), 
                      eyeSize, eyeSize);
        }
    }

    public int getLength() {
        return body.size();
    }
}