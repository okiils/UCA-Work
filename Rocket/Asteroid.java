import javax.swing.*;
import java.awt.*;

public class Asteroid implements MoveableShape {
    private int x, y;
    private AsteroidSize size;
    private JPanel canvas;
    private ImageIcon asteroidIcon;

    //Constructor
    public Asteroid(AsteroidPanel panel, int x, int y, AsteroidSize size) {
        this.canvas = panel;
        this.x = x;
        this.y = y;
        this.size = size;

        // Load the meteor image
        this.asteroidIcon = new ImageIcon("meteor.jpg");

        if (asteroidIcon.getImageLoadStatus() == MediaTracker.ERRORED) {
            System.err.println("Error loading meteor.jpg");
            // Handle the error, e.g., use a default image
        }

        // Scale the image to match the asteroid size
        this.asteroidIcon.setImage(this.asteroidIcon.getImage().getScaledInstance(
                size.getWidth(),
                size.getHeight(),
                Image.SCALE_SMOOTH));
    }

    @Override
    public void draw(Graphics2D g2) {
        asteroidIcon.paintIcon(canvas, g2, x, y);
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

    public AsteroidSize getSize() {
        return size;
    }
}
