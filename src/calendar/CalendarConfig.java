package calendar;

import log.SRALogger;

import java.text.ParseException;
import java.text.SimpleDateFormat;

public class CalendarConfig {
    public static String date_pattern = "yyyy/MM/dd";
    public static SimpleDateFormat sdf = new SimpleDateFormat(date_pattern);
    public static String datefordiff = "1900/01/01";
    public static enum Workday{
        FIVE(1), SIX(2), SEVEN(3);
        private int value;
        private Workday(int value){
            this.value = value;
        }

    }
    public static double[] getHolidays(Workday workday){
        if(workday.compareTo(Workday.FIVE)==0){
            return null;
        }else if(workday.compareTo(Workday.SIX)==0){
            return null;
        }else{
            return null;
        }
    }

    public static int diffdate(String d1, String d2){
        try{
            SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd");
            long FirstDate = format.parse(d1).getTime();
            long SecondDate = format.parse(d2).getTime();
            long calDateDays = (FirstDate-SecondDate)/(24*60*60*1000);
            calDateDays = Math.abs(calDateDays);
            return (int) calDateDays;
        }
        catch(ParseException e){
            SRALogger.Logger("calendar", "calendar", "diffdate");
            e.printStackTrace();
            return 0;
        }
    }
}
