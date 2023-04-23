package course.concurrency.m3_shared.threadLocal;

import java.time.*;
import java.time.temporal.ChronoUnit;
import java.util.HashSet;

public class DatabaseWrapper
{
    static String url = "jdbc://derby://localhost:1527//mydb";
    static DatabaseWrapper getDatabase()
    {
        System.out.println("Getting DB");
        return null;
    }
    public static void main(String[] args) {
        LocalDateTime ld1 = LocalDateTime.of(2022, Month.NOVEMBER, 6, 2, 0);
        ZonedDateTime zd1 = ZonedDateTime.of(ld1, ZoneId.of("US/Eastern"));
        LocalDateTime ld2 = LocalDateTime.of(2022, Month.NOVEMBER, 6, 1, 0);
        ZonedDateTime zd2 = ZonedDateTime.of(ld2, ZoneId.of("US/Eastern"));
        long x = ChronoUnit.HOURS.between(zd1, zd2);
        System.out.println(x);

    }
}