package bin;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

/**
 * Created by Josue on 8/11/2016.
 */
public class Helpers {
    public static HashMap<String, String> createDictionary(String key, String value) {
        HashMap<String, String> dic = new HashMap<>();
        dic.put(key, value);
        return dic;
    }

    public static String parseHashMapKey(HashMap<String, String> hashMap) {
        return hashMap.keySet().toString().replace("[", "").replace("]", "");
    }

    public static long toMicro(String date) {
        date = date.split(" ")[0];
//        System.out.println(date);

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd");

        try {
            Date d = dateFormat.parse(date);
            return d.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return 0;
    }

    public static int getDaysDifference(long microStart, long microEnd) {
//        System.out.println("start: " + microStart);
//        System.out.println("ends: " + microEnd);
        long time = microEnd - microStart;
        int days = (int) (time / (1000 * 60 * 60 * 24));
        return Math.abs(days);
    }

    public static int differenceDays(long StartDate, long EndDate) {
//        System.out.println("start: " + StartDate);
//        System.out.println("ends: " + EndDate);
        long diff = EndDate - StartDate;
//        System.out.println("DIFF: " + diff);
//        System.out.println();
        return Math.abs((int) TimeUnit.MILLISECONDS.toDays(diff));
    }

}
