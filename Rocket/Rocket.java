import javax.swing.*;
import java.awt.*;

public class Rocket implements MoveableShape {
    private int x, y;
    private int dy;
    private AsteroidPanel canvas;
    private ImageIcon rocketIcon;
    private static final int ROCKET_WIDTH = 75;  // Adjust this width as needed
    private static final int ROCKET_HEIGHT = 50; // Adjust this height as needed
    public Rocket(AsteroidPanel panel, int x, int y) {
        this.canvas = panel;
        this.rocketIcon = new ImageIcon("rocketship.jpg");

        if (rocketIcon.getImageLoadStatus() == MediaTracker.ERRORED) {
            System.err.println("Error loading rocketship.jpg");
        } else {
            // Directly set the dimensions
            this.rocketIcon.setImage(this.rocketIcon.getImage().getScaledInstance(
                    ROCKET_WIDTH,
                    ROCKET_HEIGHT,
                    Image.SCALE_SMOOTH));
        }

        this.x = x;
        this.y = y;
        this.dy = 0;
    }
    @Override
    public void draw(Graphics2D g2) {
        rocketIcon.paintIcon(canvas, g2, x, y);
    }
    @Override
    public void translate(int dx, int dy) {
        this.x += dx;
        this.y += dy;

        // Ensure the rocket stays within the vertical bounds of the canvas
        if (this.y < 0) {
            this.y = 0;
        } else if (this.y + getHeight() > canvas.getHeight()) {
            this.y = canvas.getHeight() - getHeight();
        }
    }
    public void move() {
        translate(0, dy);
    }
    public void moveUp() {
        dy = -3; // Adjust the vertical speed for moving up
    }
    public void moveDown() {
        dy = 3; // Adjust the vertical speed for moving down
    }
    public void stopMoving() {
        dy = 0;
    }
    public void shoot() {
        Laser newLaser = new Laser(canvas, getX() + getWidth(), getY() + getHeight() / 2);
        canvas.addLaser(newLaser);
    }
    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getWidth() {
        return rocketIcon.getIconWidth();
    }

    public int getHeight() {
        return rocketIcon.getIconHeight();
    }
}
