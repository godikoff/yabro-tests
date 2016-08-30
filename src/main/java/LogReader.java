import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.logging.LogEntry;

import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

class LogReader {

    private List<LogEntry> logEntryList;
    Date logTime = new Date();

    public void FindString(AppiumDriver driver, String logTag, String logString, WebElement trigger) throws Exception {
        driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
        Pattern pattern = Pattern.compile(logTag + "(.*)" + logString);
        if (trigger.isDisplayed()) {
            logEntryList = driver.manage().logs().get("logcat").getAll();
            for (LogEntry logEntry : logEntryList) {
                if (logEntry.getTimestamp() > logTime.getTime()) {
                    Matcher matcher = pattern.matcher(logEntry.getMessage());
                    if (matcher.find())
                        return;
                }
            }
            throw new Exception(logString + " not found");
        }
    }
}