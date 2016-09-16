import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.logging.LogEntry;

import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

class LogReader {

    private List<LogEntry> logEntryList;
    Date logTime = new Date();

    public boolean FindString(AppiumDriver driver, String logTag, String logString) throws Exception {

        Pattern pattern = Pattern.compile(logTag + "(.*)" + logString);

        for (int i=0;i<15;i++) {
            if (Parser(driver, pattern))
                return true;
            Thread.sleep(1000);
        }
        return false;
    }

    // Парсер логов (построчный перебор и сравнение с паттерном)
    private boolean Parser(AppiumDriver driver, Pattern pattern) {
        logEntryList = driver.manage().logs().get("logcat").getAll();
        for (LogEntry logEntry : logEntryList) {
            if (logEntry.getTimestamp() > logTime.getTime()) {
                Matcher matcher = pattern.matcher(logEntry.getMessage());
                if (matcher.find())
                    return true;
            }
        }
        return false;
    }
}
