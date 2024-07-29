
package examfiles;

import java.sql.Array;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
public class VictimPicker {

    private ArrayList<Victim> victims = new ArrayList<>();
    private ArrayList<Victim> pickedToday = new ArrayList<>();
    private ArrayList<Victim> absentToday = new ArrayList<>();

    public ArrayList<Victim> chooseTwo() {

        //Sort the victims by numberOfPicks in ascending order
        Collections.sort(victims, (v1, v2) -> v1.getNumberOfPicks() - v2.getNumberOfPicks());

        //Random object to ensure random selection
        Random random = new Random();

        //Select two random victims from the sorted list, prioritizing those with the lowest picks
        ArrayList<Victim> chosen = new ArrayList<>();
        if (victims.isEmpty()) {
            return new ArrayList<>();}

            while (chosen.size() < 2 && !victims.isEmpty()) {
                int index = random.nextInt(victims.size());
                Victim victim = victims.get(index);
                if (!pickedToday.contains(victim) && !absentToday.contains(victim)) {
                    chosen.add(victim);
                    pickedToday.add(victim);
                }
            }
            return chosen;
        }
    // Scores the picked victims, updates picks and lastPicked
    public void score(int points) {
        {
            for (Victim victim : pickedToday) {

                victim.setScore(victim.getScore() + points);
                victim.numberOfPicks++;

                // Create a MyDate object representing the current date
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");  // Assuming date format
                LocalDate currentDate = LocalDate.now();
                MyDate currentMyDate = new MyDate(currentDate.getMonthValue(), currentDate.getDayOfMonth(), currentDate.getYear());

                // Set the lastPicked date using the MyDate object
                victim.setLastPicked(currentMyDate);
            }
        }
    }
    // Marks a victim as absent and adds the date to their absences list
    public void markAbsent(Victim absentVictim) {
        LocalDate currentDate = LocalDate.now();
        absentToday.add(absentVictim);
        absentVictim.getAbsences().add(new MyDate(currentDate.getMonthValue(), currentDate.getDayOfMonth(), currentDate.getYear()));
    }
    // Loads the victim list
    public void loadList(ArrayList<Victim> victims) {
        this.victims.addAll(victims);
    }
}
