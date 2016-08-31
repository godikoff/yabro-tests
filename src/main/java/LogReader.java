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

        // Поиск в логах, если триггер (android.webkit.WebView) найден (срабатывает в 90% случаев)
        if (trigger.isDisplayed()) {
            if (Parser(driver, pattern)) {
                return;
            }
        }
        // Остальные 10%, при которых url opened логируется позже отображения триггера (android.webkit.WebView)
        Thread.sleep(5000);
        if (Parser(driver, pattern)) {
            return;
        }
        throw new Exception(logString + " not found");
    }

    // Парсер логов (построчный перебор и сравнение с паттерном)
    private boolean Parser(AppiumDriver driver, Pattern pattern) {
        logEntryList = driver.manage().logs().get("logcat").getAll();
        for (LogEntry logEntry : logEntryList) {
            if (logEntry.getTimestamp() > logTime.getTime()) {
                Matcher matcher = pattern.matcher(logEntry.getMessage());
                if (matcher.find()) ;
                {
                    return true;
                }
            }
        }
        return false;
    }
}
