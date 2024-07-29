import javax.swing.*;
import java.awt.*;

public class Laser implements MoveableShape {
    private int x, y;
    private JPanel canvas;
    private static final int LENGTH = 50; // Fixed length of the laser

    public Laser(JPanel canvas, int x, int y) {
        this.canvas = canvas;
        this.x = x;
        this.y = y;
    }
    @Override
    public void draw(Graphics2D g2) {
        g2.setColor(Color.RED);
        g2.drawLine(x, y, x + LENGTH, y); // Draw a line of fixed length
    }
    @Override
    public void translate(int dx, int dy) {
        this.x += dx;
        this.y += dy;
    }
    public int getX() {
        return x;
    }
    public int getY() {
        return y;
    }
    public int getLength(){return LENGTH;}
}
