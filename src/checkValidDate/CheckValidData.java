package checkValidate;

import java.util.List;
import java.util.Scanner;
import java.util.regex.Pattern;

public class CheckValidData {
    public static int inputAndCheckValidYear(){
        int year;
        do{
            System.out.print("> Year (number): ");
            String inputYear = new Scanner(System.in).nextLine();

            // Improved regular expression for year validation (1000 to 9999)
            String yearRegex = "^(\\d{4})$";
            if (Pattern.matches(yearRegex, inputYear)) {
                try {
                    year = Integer.parseInt(inputYear);
                    break;
                } catch (NumberFormatException e) {
                    System.out.println("Error: Invalid year format. Please enter digits only (e.g., 2024).");
                }
            } else {
                System.out.println(inputYear + " is not a valid year format.\nPlease enter a year in YYYY format (e.g., 2024).");
            }
        }while(true);
        return year;
    }
    public static int checkValidDayOfMonth(int year, int month) {
        Scanner scanner = new Scanner(System.in);
        int day;
        do {
            System.out.print("> Day (number): ");
            String dayInput = scanner.nextLine();
            if (dayInput.matches("^(?:[1-9]|0[1-9]|[12][0-9]|3[01])$")) {
                day = Integer.parseInt(dayInput);
                // Check day based on month (considering leap year for February)
                if (month == 2) {
                    if (isLeapYear(year) && (day > 29 || day < 1)) {
                        System.out.println("Invalid day for February in a leap year. Enter a day between 1 and 29.");
                        continue;
                    } else if (!isLeapYear(year) && (day > 28 || day < 1)) {
                        System.out.println("Invalid day for February in a non-leap year. Enter a day between 1 and 28.");
                        continue;
                    }
                } else if ((month == 4 || month == 6 || month == 9 || month == 11) && (day > 30 || day < 1)) {
                    System.out.println("Invalid day for this month (April, June, September, November). Enter a day between 1 and 30.");
                    continue;
                } else if ((day > 31 || day < 1)) {
                    System.out.println("Invalid day. Enter a day between 1 and 31.");
                    continue;
                }
                break;
            } else {
                System.out.println("Invalid day format. Enter a number between 1 and 31.");
            }
        } while (true);

        return day;
    }
    public static boolean isLeapYear(int year) {
        return (year % 4 == 0 && year % 100 != 0) || year % 400 == 0;
    }
    public static String formatMonthOrDay(int value){
        String monthOrDay = String.valueOf(value);
        if (monthOrDay.length() < 2){
            monthOrDay = "0" + monthOrDay;
        }
        return monthOrDay;
    }
}
