import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class AsteroidPanel extends JPanel {
    private static final int PANEL_WIDTH = 1000;
    private static final int PANEL_HEIGHT = 500;
    private static final int DELAY = 10;
    private boolean gameOver = false;
    private List<Asteroid> roids;
    private Rocket rocket;
    private List<Laser> lasers;
    private int SPAWN_DELAY = 1000;

    public AsteroidPanel() {
        this.setPreferredSize(new Dimension(PANEL_WIDTH, PANEL_HEIGHT));
        this.setBackground(Color.BLACK);

        roids = new ArrayList<>();
        rocket = new Rocket(this, 50, PANEL_HEIGHT / 2);
        lasers = new ArrayList<>();

        // Asteroid Timer
        Timer asteroidTimer = new Timer(SPAWN_DELAY, new ActionListener() {
            private long startTime = System.currentTimeMillis(); // Record the start time
            @Override
            public void actionPerformed(ActionEvent e) {
                // Calculate the elapsed time
                long elapsedTime = System.currentTimeMillis() - startTime;

                // Adjust the SPAWN_DELAY based on elapsed time
                SPAWN_DELAY = Math.max(100, (int) (1000 - elapsedTime / 100));

                int maxAsteroidHeight = AsteroidSize.LARGE.getHeight();
                int tempY = (int) (Math.random() * (PANEL_HEIGHT - maxAsteroidHeight));
                Asteroid newAsteroid = new Asteroid(AsteroidPanel.this, PANEL_WIDTH, tempY, AsteroidSize.getRandomSize());
                roids.add(newAsteroid);
            }
        });
        asteroidTimer.start();

        //Game Timer
        Timer gameTimer = new Timer(DELAY, e -> {
            if (!gameOver) {
                moveAsteroids();
                if (rocket != null) {
                    rocket.move();
                    checkCollisions();
                }
                moveLasers();
                AsteroidCollisions();
                repaint();
            }
        });
        gameTimer.start();

        this.setFocusable(true);
        this.addKeyListener(new AsteroidKeyAdapter());
        this.addMouseListener(new AsteroidMouseListener());
    }

    public void addLaser(Laser laser) {
        lasers.add(laser);
    }

    private void moveAsteroids() {
        Iterator<Asteroid> iterator = roids.iterator();
        while (iterator.hasNext()) {
            Asteroid asteroid = iterator.next();
            asteroid.translate(-4, 0);

            // Remove asteroids out of bounds
            if (asteroid.getX() + asteroid.getSize().getWidth() < 0) {
                iterator.remove();
            }
        }
    }

    private void moveLasers() {
        Iterator<Laser> iterator = lasers.iterator();
        while (iterator.hasNext()) {
            Laser laser = iterator.next();
            laser.translate(5, 0);

            // Remove lasers out of bounds
            if (laser.getX() > PANEL_WIDTH) {
                iterator.remove();
            }
        }
    }

    private class AsteroidKeyAdapter extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            if (e.getKeyCode() == KeyEvent.VK_UP) {
                if (rocket != null) {
                    rocket.moveUp();
                }
            } else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
                if (rocket != null) {
                    rocket.moveDown();
                }
            } else if (e.getKeyCode() == KeyEvent.VK_SPACE) {
                if (rocket != null) {
                    rocket.shoot();
                }
            }
        }

        @Override
        public void keyReleased(KeyEvent e) {
            if (e.getKeyCode() == KeyEvent.VK_UP || e.getKeyCode() == KeyEvent.VK_DOWN) {
                if (rocket != null) {
                    rocket.stopMoving();
                }
            }
        }
    }

    private class AsteroidMouseListener extends MouseAdapter {
        @Override
        public void mouseClicked(MouseEvent e) {
            if (rocket != null) {
                rocket.shoot();
            }
        }
    }
    private void checkCollisions() {
        Rectangle rocketBounds = new Rectangle(rocket.getX(), rocket.getY(), rocket.getWidth(), rocket.getHeight());

        for (Asteroid asteroid : roids) {
            Rectangle asteroidBounds = new Rectangle(asteroid.getX(), asteroid.getY(), asteroid.getSize().getWidth(), asteroid.getSize().getHeight());

            if (rocketBounds.intersects(asteroidBounds)) {
                handleCollision();
                break;
            }
        }
    }
    private void AsteroidCollisions() {
        Iterator<Laser> laserIterator = lasers.iterator();
        while (laserIterator.hasNext()) {
            Laser laser = laserIterator.next();
            Rectangle laserBounds = new Rectangle(laser.getX(), laser.getY(), laser.getLength(), 1);

            Iterator<Asteroid> asteroidIterator = roids.iterator();
            while (asteroidIterator.hasNext()) {
                Asteroid asteroid = asteroidIterator.next();
                Rectangle asteroidBounds = new Rectangle(asteroid.getX(), asteroid.getY(),
                        asteroid.getSize().getWidth(), asteroid.getSize().getHeight());

                if (laserBounds.intersects(asteroidBounds)) {
                    laserIterator.remove();
                    asteroidIterator.remove();
                }
            }
        }
    }
    private void handleCollision(){
        gameOver = true;
    }
    private void displayGameOver(Graphics2D g2) {
        g2.setColor(Color.RED);
        g2.setFont(new Font("Arial", Font.BOLD, 36));

        String gameOverMessage = "Game Over";
        FontMetrics fontMetrics = g2.getFontMetrics();
        int messageWidth = fontMetrics.stringWidth(gameOverMessage);

        int centerX = (PANEL_WIDTH - messageWidth) / 2;
        int centerY = PANEL_HEIGHT / 2;

        g2.drawString(gameOverMessage, centerX, centerY);
    }
    public void paint(Graphics g) {
        super.paint(g);
        Graphics2D g2 = (Graphics2D) g;

        if (gameOver) {
            displayGameOver(g2);
        } else {for (Asteroid asteroid : roids) {
            asteroid.draw(g2);
        }

            if (rocket != null) {
                rocket.draw(g2);
            }

            for (Laser laser : lasers) {
                laser.draw(g2);
            }
        }
    }
}
