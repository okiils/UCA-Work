import java.util.Random;

public enum AsteroidSize {
    SMALL(20, 20),
    MEDIUM(40, 40),
    LARGE(60, 60);

    private int width;
    private int height;

    AsteroidSize(int width, int height) {
        this.width = width;
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    //method to get a random size
    public static AsteroidSize getRandomSize() {
        AsteroidSize[] sizes = values();
        return sizes[new Random().nextInt(sizes.length)];
    }
}

