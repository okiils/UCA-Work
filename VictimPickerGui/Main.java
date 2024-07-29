/*Owen Kiilsgaard, Canon Shaw, Bret Kagebein

Desired Input File Format:
Each Victim has their own line in the following format:Name,Section,Score,lastPicked,absences,numberofPicks
An example of this file is included  in the package as "studentinfo.txt"

Also note, when operating the GUI, you must click "Pick Victim" to be able to award points, and you can only
award points one time before you must "Pick Victim" again.  If you want to award someone multiple points or questions,
you must "pick" them as a new victim every time, however their total score will accumulate.



 */
package VictimPickerGui;

import javax.swing.*;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.io.*;
import java.text.*;
import java.util.concurrent.TimeUnit;
import java.util.ArrayList;

public class Main {
    // Read in a file
    static final String filename = "studentupdate.txt";
    public static void main(String[] args) {
        // Read victims data from file to an array
        ArrayList<Victim> victims = null;
        try {
            victims = ReadVictimsFromFile.readVictimsFromFile("studentinfo.txt");
        } catch (IOException e) {
            e.printStackTrace();
            // Handle the exception as needed (e.g., show an error message)
        }

        // Check if victims data is successfully read
        if (victims != null) {
            // Create an instance of VictimPickerGUI and load the victim data
            PickerGUI gui = new PickerGUI();
            gui.victimPicker.loadList(victims);

        } else {
            // Handle the case where victims data couldn't be read
            System.out.println("Failed to read victims data from file.");
        }

    }

}