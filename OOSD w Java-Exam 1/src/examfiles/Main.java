/*Owen Kiilsgaard, Dr Baarsch, O.O.S.D W/Java, 2/1/24
This is the main driver class for Exam 1
The Structure is as Follows:
Ask the user whether they have a file or not
If they do have a file, read in the information
If the user wishes to manually input user information, prompt them for all victim information
Randomly select the victims and display their names
Desired Input File Format:
Each Victim has their own line in the following format:Name,Section,Score,lastPicked,absences,numberofPicks
An example of this file is included  in the package as "studentinfo.txt"
 */
package examfiles;

import java.time.format.DateTimeFormatter;
import java.util.*;
import java.io.*;
import java.text.*;
import java.util.concurrent.TimeUnit;

public class Main
{
    public static void main(String[] args) throws IOException {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
            Scanner in = new Scanner(System.in);
            int choice = 0;
            // Create an ArrayList to store the victims
            List<Victim> victims = new ArrayList<>();
            ReadVictimsFromFile reader = new ReadVictimsFromFile();

            try {
                System.out.println("Welcome to the Victim Picker Program, MUAHAHAHA!");
                TimeUnit.SECONDS.sleep(3);
                System.out.println("Select one of the following options:");
                System.out.println("1: I have a data file\n2: I would like to enter the names of the students manually, like a maniac.\n3:I would like to see sample data\n");

                do {
                    System.out.print("Enter 1, 2, or 3: ");
                    try {
                        choice = in.nextInt();
                    } catch (NumberFormatException e) {
                        // Handle invalid input (not a number)
                        System.out.println("Invalid input. Please enter a number.");
                        continue;
                    }
                    in.nextLine();
                    if (choice != 1 && choice != 2 && choice != 3) {
                        System.out.println("Invalid choice. Please enter 1, 2, or 3.");
                    }
                } while (choice != 1 && choice != 2 && choice != 3);

                switch (choice) {
                    case 1:
                        try {
                            Victim victimToUpdate = null;
                            int update_victim = 0;
                            System.out.print("Enter the name of your file for reading: ");
                            String fileName = in.nextLine();

                            ArrayList<Victim> filevictims = reader.readVictimsFromFile(fileName);
                            VictimPicker picker = new VictimPicker();

                            picker.loadList(filevictims);

                            List<Victim> chosenVictims = picker.chooseTwo();

                            System.out.println("\nAnd the randomly chosen victims are:");
                            // Build suspense with a sleep timer
                            TimeUnit.SECONDS.sleep(5);
                            for (Victim victim : chosenVictims) {
                                System.out.println(victim.getName());
                                TimeUnit.SECONDS.sleep(3);
                            }
                        } catch (IOException e) {
                            System.out.println("Error reading file: " + e.getMessage());
                        } catch (InterruptedException error2) {
                            System.out.println("Sleep interrupted: " + error2.getMessage());
                        }
                        System.exit(0);
                    case 2:
                        try {
                            Victim victimToUpdate = null;
                            String update_victim = "No";
                            ArrayList<Victim> enteredVictims = new ArrayList<>();
                            VictimPicker picker = new VictimPicker();
                            String moreinfo = "";
                            int numStudents = 0;
                            Date lastPicked = null;
                            System.out.println("You selected choice 2, please enter the number of students:");
                            numStudents = in.nextInt();
                            in.nextLine();
                            if (numStudents > 10) {
                                System.out.println("Just write all this in a file instead, and send that!\nHere we go...");
                            }
                            for (int i = 0; i < numStudents; i++) {
                                System.out.println("Enter name for student " + (i + 1) + ":");
                                String name = in.nextLine();

                                System.out.println("Enter section for student " + (i + 1) + ":");
                                int section = in.nextInt();
                                in.nextLine(); // Consume the remaining newline character

                                // Create a new Victim object and add it to the list
                                Victim victim = new Victim(name, section); // Assuming a constructor that takes name and section
                                enteredVictims.add(victim);
                            }
                            picker.loadList(enteredVictims);

                            List<Victim> chosenVictims = picker.chooseTwo();
                            // Build suspense with a sleep timer
                            System.out.println("\nAnd the randomly chosen victims are:");
                            TimeUnit.SECONDS.sleep(5);
                            for (Victim vic : chosenVictims) {
                                System.out.println(vic.getName());
                                TimeUnit.SECONDS.sleep(3);
                            }
                        } catch (InterruptedException error2) {
                            System.out.println("Sleep interrupted: " + error2.getMessage());
                        }
                        System.exit(0);
                    case 3:
                        ArrayList<Victim> examples = new ArrayList<>();


                        // Create 5 victims with sample data
                        examples.add(new Victim("Alice", 1));
                        examples.add(new Victim("Bob", 1));
                        examples.add(new Victim("Charlie", 1));
                        examples.add(new Victim("David", 1));
                        examples.add(new Victim("Emily", 1));

                        VictimPicker picker = new VictimPicker();
                        picker.loadList(examples);
                        List<Victim> chosenVictims = picker.chooseTwo();

                        System.out.println("\nAnd the randomly chosen victims are:");
                        TimeUnit.SECONDS.sleep(5);
                        for (Victim victim : chosenVictims) {
                            System.out.println(victim.getName());
                            TimeUnit.SECONDS.sleep(3);
                        }
                }
            } catch (InterruptedException error) {
                System.out.println("Sleep interrupted: " + error.getMessage());
            }
            System.exit(0);
    }
}
