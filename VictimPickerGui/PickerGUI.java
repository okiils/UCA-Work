package VictimPickerGui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

public class PickerGUI extends JFrame {

    // Initiate Victim Picker
    public VictimPicker victimPicker;

    // Initiate Victim Wheel
    private JComboBox<String> volunteerComboBox;

    // Initiate GUI Controls
    private JTextField volunteerTextField;
    private JButton pickButton;
    private JButton pickVolunteerButton;
    private JButton victimAbsentButton;
    private JButton startTimerButton;
    private JLabel timerLabel; // New JLabel to display the timer
    private ArrayList<JButton> scoreButtons;
    private Timer timer;
    private int remainingTime; // Remaining time in seconds
    private Victim chosenVictim;
    private JLabel dataLabel;
    private JLabel dataLabel2;

    // Constructor
    public PickerGUI() {  // Fix constructor name to match class name
        victimPicker = new VictimPicker();  // Instantiate the victimPicker
        initializeUI();
    }

    private void initializeUI() {

        // Build GUI
        volunteerComboBox = new JComboBox<>();
        dataLabel = new JLabel();
        dataLabel2 = new JLabel();
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                // Call the method to write victims to a file
                VictimPicker.updateFile();
            }
        });
        try {
            // Read victims from the file and add their names to the combo box
            ArrayList<Victim> victims = ReadVictimsFromFile.readVictimsFromFile("studentinfo.txt");
            for (Victim victim : victims) {
                volunteerComboBox.addItem(victim.getName());
            }
        } catch (IOException e) {
            e.printStackTrace(); // Handle the exception appropriately
        }
        // Customize GUI
        setTitle("Victim Picker GUI");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(15, 1)); // Increase the number of rows for the timer label

        // Button Function
        pickButton = new JButton("Pick random");
        pickVolunteerButton = new JButton("Pick volunteer");
        victimAbsentButton = new JButton("Victim Absent");
        startTimerButton = new JButton("Start Timer");
        timerLabel = new JLabel("Time remaining: 5:00"); // Initial timer label

        // Score Buttons
        scoreButtons = new ArrayList<>();
        for (int i = -2; i <= 5; i++) {
            JButton scoreButton = new JButton(String.valueOf(i));
            scoreButton.setPreferredSize(new Dimension(40, 40));
            scoreButtons.add(scoreButton);
        }

        addComponentsToPane();

        pack();
        setLocationRelativeTo(null);
        setVisible(true);


    }

    // Update Labels
    private void updatePickedLabel(String newData) {
        dataLabel.setText(newData);
    }

    private void updateAbsentLabel(String newData) {
        dataLabel2.setText(newData);
    }

    private void addComponentsToPane() {

        // Write Labels
        add(new JLabel("Volunteer:"));
        add(volunteerComboBox);
        add(pickVolunteerButton);
        add(pickButton);

        add(new JLabel("Selected victim: "));
        add(dataLabel);
        add(new JLabel());
        add(victimAbsentButton);
        add(new JLabel("Absent victim: "));
        add(dataLabel2);


        add(new JLabel("add/remove score"));
        for (JButton scoreButton : scoreButtons) {
            add(new JLabel());
            add(scoreButton);
        }

        // Add timer button
        add(new JLabel());
        add(startTimerButton);
        add(timerLabel);

        // Set Up Button Responses
        pickButton.addActionListener(e -> pickVictim());
        pickVolunteerButton.addActionListener(e -> pickVolunteer());
        victimAbsentButton.addActionListener(e -> markVictimAbsent());
        startTimerButton.addActionListener(e -> startTimer());

        // Set up Score Responses
        for (int i = 0; i <= 7; i++) {
            int finalI = i;
            scoreButtons.get(i).addActionListener(e -> scoreVictims(finalI - 2));
        }
    }

    // Choose a victim at random
    private void pickVictim() {
        chosenVictim = victimPicker.chooseOne();
        updatePickedLabel(getVictim().getName());
    }

    // Manually choose a Victim
    private void pickVolunteer() {
        chosenVictim = victimPicker.getVolunteer((String) volunteerComboBox.getSelectedItem());
        updatePickedLabel(getVictim().getName());
    }

    // Victim Getter
    public Victim getVictim(){
        return chosenVictim;
    }

    // Mark Absent
    private void markVictimAbsent() {
        VictimPicker.markAbsent(getVictim());
        updateAbsentLabel(getVictim().getName());
    }

    // Keep score
    private void scoreVictims(int points) {
        victimPicker.score(points);
    }

    // Handle Timer
    private void startTimer() {
        // Set the initial time to 5 minutes
        remainingTime = 5 * 60;
        updateTimerLabel();

        // Create a Timer that updates the label every second
        timer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                remainingTime--;
                updateTimerLabel();

                // Buzzer
                if (remainingTime == 0) {
                    // Stop the timer when it reaches 0
                    timer.stop();
                    JOptionPane.showMessageDialog(PickerGUI.this, "Timer expired!");
                }
            }
        });

        timer.start();
    }

    // Translate time to be easily read
    private void updateTimerLabel() {
        int minutes = remainingTime / 60;
        int seconds = remainingTime % 60;
        timerLabel.setText("Time remaining: " + String.format("%d:%02d", minutes, seconds));
    }

    // Create a New Picker
    public static void main(String[] args) {
        new PickerGUI();  // Create an instance of PickerGUI directly
    }
}