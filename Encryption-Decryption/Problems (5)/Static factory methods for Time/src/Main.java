import java.util.Scanner;

class Time {

    int hour;
    int minute;
    int second;

    public Time() { }

    public Time(int hour, int minute, int second) {
        this.hour = hour;
        this.minute = minute;
        this.second = second;
    }

    public static Time noon() {
        return new Time(12, 0, 0);
    }

    public static Time midnight() {
        return new Time(0, 0, 0);
    }

    public static Time ofSeconds(long seconds) {
        long hour = 0;
        long minute = 0;
        long second = 0;

        hour = seconds / 3600L - seconds / 3600L / 24L * 24L;
        minute = seconds / 60L - seconds / 60L / 1440L * 1440L - hour * 60;
        second = seconds - seconds / 3600L * 3600L - ((seconds - seconds / 3600L * 3600L) / 60L * 60L);

        return new Time((int) hour, (int) minute, (int) second);
    }

    public static Time of(int hour, int minute, int second) {
        if ((hour < 24 && minute < 60 && second < 60) && (hour >= 0 && minute >= 0 && second >= 0)) {
            return new Time(hour, minute, second);
        } else {
            return null;
        }
    }
}

/* Do not change code below */
public class Main {

    public static void main(String[] args) {
        final Scanner scanner = new Scanner(System.in);

        final String type = scanner.next();
        Time time = null;

        switch (type) {
            case "noon":
                time = Time.noon();
                break;
            case "midnight":
                time = Time.midnight();
                break;
            case "hms":
                int h = scanner.nextInt();
                int m = scanner.nextInt();
                int s = scanner.nextInt();
                time = Time.of(h, m, s);
                break;
            case "seconds":
                time = Time.ofSeconds(scanner.nextInt());
                break;
            default:
                time = null;
                break;
        }

        if (time == null) {
            System.out.println(time);
        } else {
            System.out.println(String.format("%s %s %s", time.hour, time.minute, time.second));
        }
    }
}