import javax.swing.*;

public class Tester {

    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });
    }
    private static void createAndShowGUI() {
        JFrame frame = new JFrame("Blast them");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        AsteroidPanel panel = new AsteroidPanel();
        frame.getContentPane().add(panel);

        // Set up the keyboard focus
        panel.setFocusable(true);
        panel.requestFocusInWindow();

        frame.pack();
        frame.setVisible(true);
    }
}
