/*
Owen Kiilsgaard, Dr. Baarsch, O.O.S.D w/Java, 2/1/24
This Class is the Victim Class
Responsible for storing student(victim) information
Includes:
Constructor
Getters for all instance data
Setters for all instance data
 */

package examfiles;

import java.util.*;

public class Victim {
    public String name;
    public int section;
    public int score;
    public MyDate lastPicked;
    public ArrayList<MyDate> absences;
    public int numberOfPicks;

    public int numOfVictims;

    //Default Constructor used when reading in the data
    public Victim(){
        this.name = "";
        this.section = 0;
        this.absences = new ArrayList<>();
        this.lastPicked = null;
        this.numberOfPicks = 0;

    }
    //Constructor with name and section as arguments
    public Victim(String name, int section){
        this.name = name;
        this.section = section;
        this.absences = new ArrayList<>();
        this.score = 0;
        this.lastPicked = null;
        this.numberOfPicks = 0;
    }

    //All getters for instance data
    public String getName() {
        return name;
    }
    public int getSection() {
        return section;
    }
    public int getScore() {
        return score;
    }
    public MyDate getLastPicked() {
        return lastPicked;
    }
    public ArrayList<MyDate> getAbsences() {
        return absences;
    }
    public int getNumberOfPicks() {
        return numberOfPicks;
    }

    //All setters for instance data, and setters used to update
    public void setName(String name) {
        this.name = name;
    }
    public void setScore(int score) {
        this.score = score;
    }
    public void setLastPicked(MyDate lastPicked) {
        this.lastPicked = lastPicked;
    }

    public void setAbsences(ArrayList<MyDate> absences) {
        this.absences = absences;
    }

    public void setNumberOfPicks(int numberOfPicks) {
        this.numberOfPicks = numberOfPicks;
    }

    public void setSection(int newSection) {this.section = newSection;
    }
}
