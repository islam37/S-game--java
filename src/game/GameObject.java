package game;

import java.awt.*;

public abstract class GameObject {
    protected int x;
    protected int y;
    protected Color color;

    public GameObject(int x, int y, Color color) {
        this.x = x;
        this.y = y;
        this.color = color;
    }

    public int getX() { return x; }
    public int getY() { return y; }

    public abstract void draw(Graphics g);
}