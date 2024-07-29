/*This is the Victim Picker Class
This Class handles the logic of choosing victims, assigning scores,
    marking absent, and selecting volunteers
 Works hand in hand with PickerGUI class to make buttons and GUI components effective
 */


package VictimPickerGui;

import java.sql.Array;
import java.time.Instant;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
public class VictimPicker {
    // States
    public static ArrayList<Victim> victims = new ArrayList<>();
    private ArrayList<Victim> pickedToday = new ArrayList<>();
    private static ArrayList<Victim> absentToday = new ArrayList<>();
    public ArrayList<Victim> chosen = new ArrayList<>();

    // Random choosing method
    public Victim chooseOne() {
        boolean bool = false;
        // Sort the victims by numberOfPicks in ascending order
        Collections.sort(victims, Comparator.comparingInt(Victim::getNumberOfPicks));    // Prioritize those picked less

        // Random object to ensure random selection
        Random random = new Random();

        // Select one random victim from the sorted list, prioritizing those with the lowest picks
        Victim chosenVictim = null;
        if (!victims.isEmpty()) {
            int index = random.nextInt(victims.size());
            chosenVictim = victims.get(index);
            for (Victim victim : pickedToday){
                if (victim.getName() == chosenVictim.getName()){
                    bool = true;
                }
            }
            if (!bool){
                pickedToday.add(chosenVictim);
            }
        }

        return chosenVictim;
    }

    // Scores the picked victims, updates picks and lastPicked
    public void score(int points) {
        {
            for (Victim victim : pickedToday) {

                // Score Victim
                victim.setScore(victim.getScore() + points);
                victim.numberOfPicks++;

                // Create a MyDate object representing the current date
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");  // Assuming date format
                LocalDate currentDate = LocalDate.now();
                Date currentMyDate = new Date(currentDate.getMonthValue(), currentDate.getDayOfMonth(), currentDate.getYear());

                // Set the lastPicked date using the MyDate object
                victim.setLastPicked(currentMyDate);
            }
        }
        // Clear the array
        pickedToday.clear();
    }
    // Marks a victim as absent and adds the date to their absences list
    public static void markAbsent(Victim absentVictim) {
        Date today = Date.from(Instant.now());
        absentToday.add(absentVictim);
        absentVictim.getAbsences().add(new Date());
    }
    // Loads the victim list
    public void loadList(ArrayList<Victim> victims) {
        this.victims.addAll(victims);
    }

    // Getter
    public Victim getVolunteer(String name) {
        Victim chosen = null;
        boolean bool = false;
        for (Victim victim : victims) {
            if (Objects.equals(victim.getName(), name))
                chosen = victim;
        }

        for (Victim victim : pickedToday){
            if (victim.getName() == chosen.getName()){
                bool = true;
            }
        }
        if (!bool){
            pickedToday.add(chosen);
        }

        return chosen;
    }

    // Update Victim vile
    public static void updateFile(){
        ReadVictimsFromFile.writeVictimsToFile(victims, "studentupdate.txt");
    }
}