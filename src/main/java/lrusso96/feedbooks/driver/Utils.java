package lrusso96.feedbooks.driver;

import java.time.*;

public class Utils
{
    public static LocalDate parseUTC(String date){
        Instant instant = Instant.parse(date);
        return LocalDateTime.ofInstant(instant, ZoneId.of(ZoneOffset.UTC.getId())).toLocalDate();
    }
}
