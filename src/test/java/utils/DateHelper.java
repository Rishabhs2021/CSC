package utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class DateHelper {

	public enum DateFormats {
        D_YYMMDD("yy-MM-dd"), D_DDMMyy("dd-MM-yy"),
        D_YYMMDD_N("yy-MMM-dd"), D_DDMMyy_N("dd-MMM-yy"),
        D_YYMMDDHHMMA_N("yy-MMM-dd, hh:mma"), D_DDMMyyHHMMA_N("dd-MMM-yy, hh:mma"),
        S_YYMMDD("yy/MM/dd"), S_DDMMyy("dd/MM/yy"),
        S_YYMMDDHHMMA("yy/MM/dd, hh:mma"), S_DDMMyyHHMMA("dd/MM/yy, hh:mma"),
        S_YYMMDDHHMMA_N("yy/MMM/dd, hh:mma"), S_DDMMyyHHMMA_N("dd/MMM/yy, hh:mma"),
        D_YYYYMMDD("yyyy-MM-dd"), D_DDMMYYYY("dd-MM-yyyy"),
        D_YYYYMMDDHHMMA("yyyy-MM-dd, hh:mma"), D_DDMMYYYYHHMMA("dd-MM-yyyy, hh:mma"),
        D_YYYYMMDD_N("yyyy-MMM-dd"), D_DDMMYYYY_N("dd-MMM-yyyy"),
        D_YYYYMMDDHHMMA_N("yyyy-MMM-dd, hh:mma"), D_DDMMYYYYHHMMA_N("dd-MMM-yyyy, hh:mma"),
        S_YYYYMMDD("yyyy/MM/dd"), S_DDMMYYYY("dd/MM/yyyy"),
        S_YYYYMMDDHHMMA("yyyy/MM/dd, hh:mma"), S_DDMMYYYYHHMMA("dd/MM/yyyy, hh:mma"),
        S_YYYYMMDDHHMMA_N("yyyy/MMM/dd, hh:mma"), S_DDMMYYYYHHMMA_N("dd/MMM/yyyy, hh:mma"),
        D_YYMMDDHHMMSSA_N("yy-MMM-dd, hh:mm:ssa"), D_DDMMyyHHMMSSA_N("dd-MMM-yy, hh:mm:ssa"),
        S_YYMMDDHHMMSSA("yy/MM/dd, hh:mm:ssa"), S_DDMMyyHHMMSSA("dd/MM/yy, hh:mm:ssa"),
        S_YYMMDDHHMMSSA_N("yy/MMM/dd, hh:mm:ssa"), S_DDMMyyHHMMSSA_N("dd/MMM/yy, hh:mm:ssa"),
        D_YYYYMMDDHHMMSSA("yyyy-MM-dd, hh:mm:ssa"), D_DDMMYYYYHHMMSSA("dd-MM-yyyy, hh:mm:ssa"),
        D_YYYYMMDDHHMMSSA_N("yyyy-MMM-dd, hh:mm:ssa"), D_DDMMYYYYHHMMSSA_N("dd-MMM-yyyy, hh:mm:ssa"),
        S_YYYYMMDDHHMMSSA("yyyy/MM/dd, hh:mm:ssa"), S_DDMMYYYYHHMMSSA("dd/MM/yyyy, hh:mm:ssa"),
        S_YYYYMMDDHHMMSSA_N("yyyy/MMM/dd, hh:mm:ssa"), S_DDMMYYYYHHMMSSA_N("dd/MMM/yyyy, hh:mm:ssa"),
        YYMMDDHHMMSS("yyMMddhhmmss"), YYMMDDHHMMSS_24("yyMMddkkmmss"),
        HHMMA("hh:mma"), HHMM("hh:mm"), HHMMSSA("hh:mm:ssa"), HHMMSS("hh:mm:ss"),
        YYYYMMDDHHMMssSSSZ("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"),
        YYYYMMDDTHHMMSSZ ("YYYY-MM-DD'T'HH:mm:ss'Z'"),
        DD("dd"), MM("MM"), MM_N("MMM"), DDMM("ddMM"),MMDD("MMdd"), DDMM_N("dd MMM"), DDMMYYYY("ddMMyyyy"),
        YY("YY"),
        MMDDYYYY("MMddYYY");
        
        private String dateFormat;

        DateFormats(String dateFormat) {this.dateFormat = dateFormat;}

        public String getDateFormat() {
            return dateFormat;
        }
    }
	
	

	 /**
     * @return yyyy-MM-dd HH:mm:ss.SSS
     */
    
	 public static String getpreviousYearinFormat() {
	        try {
	            Calendar calendar = Calendar.getInstance();
	            calendar.setTime(new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).parse(getToday()));
	            calendar.add(Calendar.YEAR, -1);
	            Date tomorrow = calendar.getTime();
	            return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS", Locale.getDefault()).format(tomorrow);
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	        return null;
	    }
	 
	 

    // Return yesterday date in format yyyy-MM-dd'T'HH:mm:ss.SSS'Z'
    public static String getPreviousDayInFormat() {
        try {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).parse(getToday()));
            calendar.add(Calendar.DATE, -1);
            Date tomorrow = calendar.getTime();
            System.out.println(tomorrow);
            return new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault()).format(tomorrow);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    // Return yesterday date in format yyyy-MM-dd'T'HH:mm:ss.SSS'Z'
    public static String getPreviousDayInFormatForDB() {
        try {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).parse(getToday()));
            calendar.add(Calendar.DATE, -1);
            Date tomorrow = calendar.getTime();
            return new SimpleDateFormat("yyyy-MM-dd hh:mm:ss.S", Locale.getDefault()).format(tomorrow);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * @return dd/MM/yyyy
     */
    public static long getDateOnly(String date) {
        SimpleDateFormat sample = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        try {
            return sample.parse(date).getTime();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }
    

    /**
     * @return dd/MM/yyyy
     */
    public static String getDateOnly(long time) {
        return new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(time);
    }

    /**
     * @return dd/MM/yyyy, hh:mm a
     */
    public static String getDateAndTime(long time) {
        SimpleDateFormat sample = new SimpleDateFormat("dd/MM/yyyy, hh:mm a", Locale.getDefault());
        return sample.format(new Date(time));
    }

    /**
     * @return dd/MM/yyyy, hh:mm a
     */
    public static String getDateAndTime(String time) {
        SimpleDateFormat sample = new SimpleDateFormat("dd/MM/yyyy, hh:mm a", Locale.getDefault());
        return sample.format(time);
    }

    /**
     * @return hh:mm a
     */
    public static String getTimeOnly(long time) {
        SimpleDateFormat sample = new SimpleDateFormat("hh:mm a", Locale.getDefault());
        return sample.format(time);
    }

    /**
     * @return today's date in format (dd/MM/yyyy HH:mm:ss)
     */
    public static String getTodayWithTime() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.getDefault());
        return dateFormat.format(new Date());
    }

    /**
     * @return today's date in format (dd/MM/yyyy)
     */
    public static String getToday() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
        return dateFormat.format(new Date());
    }
    
    public static String getTodayYYMMDD() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        return dateFormat.format(new Date());
    }



    /**
     * @return tomorrows's date in format (dd/MM/yyyy)
     */
    public static String getTomorrow() {
        try {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).parse(getToday()));
            calendar.add(Calendar.DATE, 1);
            Date tomorrow = calendar.getTime();
            return new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault()).format(tomorrow);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    
    
    public static String getYesterday() {
        try {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).parse(getToday()));
            calendar.add(Calendar.DATE, -1);
            Date yesterday = calendar.getTime();
            return new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault()).format(yesterday);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    
    public static String getPrevious() {
        try {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).parse(getToday()));
            calendar.add(Calendar.DATE, -1);
            Date yesterday = calendar.getTime();
            return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(yesterday);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    public static String getafternDays(int n) {
        try {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).parse(getToday()));
            calendar.add(Calendar.DATE, n);
            Date actual = calendar.getTime();
            return new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss-SS:00", Locale.getDefault()).format(actual);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * new and old date must equal to {@link DateFormats}
     *
     * @return number of hours
     */
    public static long getDaysBetweenTwoDate(String old, String newDate, DateFormats dateFormats) {
        SimpleDateFormat myFormat = new SimpleDateFormat(dateFormats.getDateFormat(), Locale.getDefault());
        try {
            Date date1 = myFormat.parse(old);
            Date date2 = myFormat.parse(newDate);
            long diff = date1.getTime() - date2.getTime();
            long seconds = diff / 1000;
            long minutes = seconds / 60;
            long hours = minutes / 60;
            long days = hours / 24;
            return days;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * new and old date must equal to {@link DateFormats}
     *
     * @return number of hours
     */
    public static long getHoursBetweenTwoDate(String old, String newDate, DateFormats dateFormats) {
        SimpleDateFormat myFormat = new SimpleDateFormat(dateFormats.getDateFormat(), Locale.getDefault());
        try {
            Date date1 = myFormat.parse(old);
            Date date2 = myFormat.parse(newDate);
            long diff = date1.getTime() - date2.getTime();
            long seconds = diff / 1000;
            long minutes = seconds / 60;
            long hours = minutes / 60;
            long days = hours / 24;
            return hours;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static long getMinutesBetweenTwoDates(String old, String newDate, DateFormats dateFormats) {
        SimpleDateFormat myFormat = new SimpleDateFormat(dateFormats.getDateFormat(), Locale.getDefault());
        try {
            Date date1 = myFormat.parse(old);
            Date date2 = myFormat.parse(newDate);
            long diff = date1.getTime() - date2.getTime();
            long seconds = diff / 1000;
            long minutes = seconds / 60;
            long hours = minutes / 60;
            long days = hours / 24;
            return minutes;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static long getMinutesBetweenTwoDates(long old, long newDate) {
        long diff = old - newDate;
        long seconds = diff / 1000;
        long minutes = seconds / 60;
        long hours = minutes / 60;
        long days = hours / 24;
        return minutes;
    }

    public static boolean isInFuture(long timestamp) {
        Date date = new Date(timestamp);
        return date.getTime() - new Date().getTime() >= 0;
    }

    /**
     */
    public static long parseAnyDate(String date) {
        long time = 0;
        for (DateFormats formats : DateFormats.values()) {
            try {
                SimpleDateFormat format = new SimpleDateFormat(formats.getDateFormat(), Locale.getDefault());
                time = format.parse(date).getTime();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return time;
    }

    public static long parseDate(String date, DateFormats dateFormats) {
        SimpleDateFormat format = new SimpleDateFormat(dateFormats.getDateFormat(), Locale.getDefault());
        try {
            return format.parse(date).getTime();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static String getDate(String date, DateFormats orginalFormat, DateFormats newFormat) {
        SimpleDateFormat sample = new SimpleDateFormat(orginalFormat.getDateFormat(), Locale.getDefault());
        try {
            long time = sample.parse(date).getTime();
            sample = new SimpleDateFormat(newFormat.getDateFormat(), Locale.getDefault());
            return sample.format(time);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return date;
    }

    public static String getDesiredFormat(DateFormats formats) {
        SimpleDateFormat format = new SimpleDateFormat(formats.getDateFormat(), Locale.getDefault());
        return format.format(new Date());
    }

    public static String getPSTToday() {
    	 Date today = new Date();
    	 SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
    	 df.setTimeZone(TimeZone.getTimeZone("America/Los_Angeles"));
    	// return new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault()).format(today);
    	 String PST = df.format(today);
    	 return PST;
    }
    
    public static String getPSTDay() {
    	 Date today = new Date();
    	 SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
    	 df.setTimeZone(TimeZone.getTimeZone("America/Los_Angeles"));
    	 String PST = df.format(today);
    	String PSTDay = PST.substring(8, 10);
    	 return PSTDay;
    }
    
    public static String getPSTMonth() {
   	 Date today = new Date();
   	 SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
   	 df.setTimeZone(TimeZone.getTimeZone("America/Los_Angeles"));
   	 String PST = df.format(today);
 	String PSTMonth = PST.substring(5, 7);
 	 return PSTMonth;
   }
    
    public static String getDesiredFormat(DateFormats formats, long date) {
        if (date == 0) return "";
        SimpleDateFormat format = new SimpleDateFormat(formats.getDateFormat(), Locale.getDefault());
        return format.format(date);
    }
    
    
    
    private static boolean isToday(Date date){
        Calendar today = Calendar.getInstance();
        Calendar specifiedDate  = Calendar.getInstance();
        specifiedDate.setTime(date);

        return today.get(Calendar.DAY_OF_MONTH) == specifiedDate.get(Calendar.DAY_OF_MONTH)
                &&  today.get(Calendar.MONTH) == specifiedDate.get(Calendar.MONTH)
                &&  today.get(Calendar.YEAR) == specifiedDate.get(Calendar.YEAR);
    }
    
    public synchronized static String addnDaysToDate(Date date, int numberOfDays) {
    	DateFormat df = null;
    	Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.add(Calendar.DATE, numberOfDays);
		Date currentDatePlusDays = c.getTime();
		System.out.println("Date after 14 days" + " " + df.format(currentDatePlusDays));
		return df.format(currentDatePlusDays);
	}
}
