package examfiles;

import java.io.*;
import java.lang.reflect.Array;
import java.time.format.DateTimeFormatter;
import java.util.*;

import static examfiles.MyDate.parseMyDate;



public class ReadVictimsFromFile {
    static public int numberOfVictims = 0;
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
    static final String filePath = "/C:/Users/owenk/OneDrive//Desktop//Object Oriented Software Development W//Exam_1_Project//src//examfiles//studentinfo.txt/";

    public static ArrayList<Victim> readVictimsFromFile(String file_name) throws IOException {

        ArrayList<Victim> victims = new ArrayList<>();
        Scanner scan = new Scanner(System.in);

        BufferedReader reader = new BufferedReader(new FileReader("studentinfo.txt"));
        String line = reader.readLine();
        do{
            String[] data = line.split(",");

            String name = data[0];
            int section = Integer.parseInt(data[1]);
            int score = Integer.parseInt(data[2]);

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            MyDate lastPickedDate = parseMyDate(data[3], formatter);


            ArrayList<MyDate> absences = new ArrayList<>();
            if (data.length > 4) {
                String[] absenceDateStrings = data[4].split(",");
                for (String absenceDateString : absenceDateStrings) {
                    absences.add(parseMyDate(absenceDateString, formatter));
                }
            }
            int numberOfPicks = Integer.parseInt(data[data.length - 1]);

            // Use this data to create a Victim object or perform other tasks as needed
            Victim victim = new Victim(name, section);  // Create with name and section only
            victim.setScore(score);
            victim.setLastPicked(lastPickedDate);
            victim.setAbsences(absences);
            victim.setNumberOfPicks(numberOfPicks);
            line = reader.readLine();
            victims.add(victim);

            numberOfVictims++;
        }while (line != null);

            reader.close();
            return victims;
        }
    private static ArrayList<Date> parseAbsences(String absencesString) {
        ArrayList<Date> absences = new ArrayList<>();
        if (!absencesString.isEmpty()) {
            String[] absenceDates = absencesString.split(",");
            for (String absenceDate : absenceDates) {
                absences.add(new Date(Long.parseLong(absenceDate))); // Parse as long timestamp
            }
        }
        return absences;
    }
    public static int getNumberOfVictims(){
        return numberOfVictims;
    }
}

