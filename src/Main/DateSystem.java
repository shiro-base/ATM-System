/*
A date system containing static methods to record and move date
 */

package Main;

import Database.BankReservesDatabase;
import bank.BankReserved;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.Period;

import static Utils.Constants.formatter;

public class DateSystem {
    private static LocalDate startDate = LocalDate.now();



    private static LocalDate today = LocalDate.parse(BankReservesDatabase.getDate(), formatter);

    public static void setToday(String dateString)
    {
        today = LocalDate.parse(dateString, formatter);
    }

    public static String getToday()
    {
        return today.toString();
    }

    public static void incrementDays(int days) throws SQLException {
        today = today.plusDays(days);
        BankReservesDatabase.changeDate(today.toString());
    }

    public static int dateDifference(String dateString)
    {
        LocalDate newDate = LocalDate.parse(dateString, formatter);
        Period diff = today.until(newDate);;
        return diff.getDays();
    }

}
