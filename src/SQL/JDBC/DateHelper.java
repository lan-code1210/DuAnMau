
package SQL.JDBC;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateHelper {
    static SimpleDateFormat sdf = new SimpleDateFormat();
    
    public static Date dt(String date, String pattern){
        try {
            sdf.applyPattern(pattern);
            return sdf.parse(date);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    
    public static String stringa(Date date, String pattern){
        sdf.applyPattern(pattern);
        return sdf.format(date);
    }
    
    public static Date aDays(Date date, long days){
        date.setTime(date.getTime() + days * 24 * 60 * 60 * 1000);
        return date;
    }
}
