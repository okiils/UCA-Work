package examfiles;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class MyDate {
    private int month;
    private int day;
    private int year;

    public MyDate(int month, int day, int year) {
        this.month = month;
        this.day = day;
        this.year = year;
    }

    public int getMonth() {
        return month;
    }

    public int getDay() {
        return day;
    }

    public int getYear() {
        return year;
    }
    public static MyDate parseMyDate(String dateString, DateTimeFormatter formatter) {
        LocalDate localDate = LocalDate.parse(dateString, formatter);
        return new MyDate(localDate.getMonthValue(), localDate.getDayOfMonth(), localDate.getYear());
    }
}
