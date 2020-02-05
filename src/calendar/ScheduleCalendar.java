package calendar;

import log.SRALogger;
import org.apache.poi.ss.formula.atp.WorkdayCalculator;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class ScheduleCalendar {

    public String standardDate;
    public int diff_date;
    public CalendarConfig.Workday workday;
    private WorkdayCalculator wc = WorkdayCalculator.instance;

    public ScheduleCalendar(String standardDate, CalendarConfig.Workday workday){
        this.standardDate = standardDate;
        this.workday = workday;
        this.diff_date = get_diff_date(standardDate);
    }

    private int get_diff_date(String standardDate){
        return CalendarConfig.diffdate(CalendarConfig.datefordiff, standardDate);
    }


    public String get7weekDate(int workday){
        SimpleDateFormat df = new SimpleDateFormat(CalendarConfig.date_pattern);
        try {
            Date date = df.parse(standardDate);

            // 날짜 더하기
            Calendar cal = Calendar.getInstance();
            cal.setTime(date);
            cal.add(Calendar.DATE, workday);
            return df.format(cal.getTime());

        } catch (ParseException e) {
            e.printStackTrace();
            SRALogger.Logger("calendar","ScheduleCalendar","get7weekDate");
            //System.exit(1);
            return null;
        }


    }
    public String get5weekDate(int workday){
        double[] holidays = {11.0, 12.0};
        Date d = wc.calculateWorkdays(diff_date, workday, holidays);
        return CalendarConfig.sdf.format(d);
    }

}
