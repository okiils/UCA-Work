package VictimPickerGui;

import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.ArrayList;
import java.util.Date;

import static VictimPickerGui.MyDate.parseMyDate;

// Read Victims From A File
public class ReadVictimsFromFile {
    static public int numberOfVictims = 0;
    private static final String fileName = "studentinfo.txt";
    private static final String filePath = "./studentinfo.txt";

    // Failsafe
    public static ArrayList<Victim> readVictimsFromFile(String file_name) throws IOException {
        ArrayList<Victim> victims = new ArrayList<>();

        BufferedReader reader = new BufferedReader(new FileReader(filePath));
        String line = reader.readLine();
        do {
            String[] data = line.split(",");

            // Translate Data Field to the array list
            String name = data[0];                                              // Name
            int section = Integer.parseInt(data[1]);                            // Section
            int score = Integer.parseInt(data[2]);                              // Score

            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");   // Date Formatter

            // Parse data[3] as a string and convert it to a Date object
            Date lastPickedDate = null;
            try {
                lastPickedDate = dateFormat.parse(data[3]);                     // Read last picked
            } catch (ParseException e) {
                e.printStackTrace(); // Handle the exception appropriately
            }



            ArrayList<Date> absences = parseAbsences(data[4]);                   // Last Absence


            int numberOfPicks = Integer.parseInt(data[5]);                        // How many times picked

            // Set Data from list into each individual victim
            Victim victim = new Victim(name, section);
            victim.setScore(score);
            victim.setLastPicked(lastPickedDate);
            victim.setAbsences(absences);
            victim.setNumberOfPicks(numberOfPicks);
            line = reader.readLine();
            victims.add(victim);

            numberOfVictims++;
        } while (line != null);        // Until you run out

        reader.close();
        return victims;                // Returns a list of victims after the reader closes.
    }

    // Write Victims to an output File
    public static void writeVictimsToFile(ArrayList<Victim> victims, String filename) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            for (Victim victim : victims) {
                String victimLine = String.format("%s,%d,%d,%s,%s,%d",
                        victim.name,
                        victim.section,
                        victim.score,
                        victim.lastPicked.toString(),  // Assuming MyDate has a toString method
                        victim.absences,
                        victim.numberOfPicks);

                writer.write(victimLine);
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace(); // Handle the exception appropriately
        }
    }

    // Format Date to String for absences
    private static String formatAbsences(ArrayList<MyDate> absences) {
        StringBuilder absencesString = new StringBuilder();
        for (MyDate date : absences) {
            absencesString.append(date.toString()); // Assuming MyDate has a toString method
            absencesString.append(",");
        }
        return absencesString.toString();
    }

    // Create an array of Absences
    private static ArrayList<Date> parseAbsences(String absencesString) {
        ArrayList<Date> absences = new ArrayList<>();
        if (!absencesString.isEmpty()) {
            String[] absenceDates = absencesString.split(",");
            for (String absenceDate : absenceDates) {
                try {
                    absences.add(new SimpleDateFormat("yyyy-MM-dd").parse(absenceDate));
                } catch (ParseException e) {
                    e.printStackTrace(); // Handle the exception appropriately
                }
            }
        }
        return absences;
    }

    // Getter
    public static int getNumberOfVictims() {
        return numberOfVictims;
    }
}