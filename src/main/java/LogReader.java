import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;


public class LogReader {
    public static void logClear() throws IOException {
        Runtime.getRuntime().exec("adb logcat -c");
    }

    public static void findString(String log) throws IOException {
        Process logcat = Runtime.getRuntime().exec("adb logcat");

        BufferedReader reader = new BufferedReader(new InputStreamReader(logcat.getInputStream()));
        String logString;
        long endTime = System.currentTimeMillis()+15000;

        while ((logString = reader.readLine()) != null && System.currentTimeMillis() < endTime) {
            if (logString.contains(log)) {
                return;
                }
            }
        throw new IOException(log + " not found");
    }
}
