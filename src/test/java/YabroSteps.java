import com.google.common.collect.Lists;
import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.WebElement;
import ru.yandex.qatools.allure.annotations.Attachment;
import ru.yandex.qatools.allure.annotations.Step;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;


public class YabroSteps {
    private LogReader logReader;
    private YabroObjects yabroObjects;
    private AppiumDriver driver;

    public YabroSteps(AppiumDriver driver) {
        this.driver = driver;
        logReader = new LogReader(driver);
        yabroObjects = new YabroObjects(driver);

    }

    @Attachment
    public byte[] saveScreenshot() {
        byte[] screenshot = driver.getScreenshotAs(OutputType.BYTES);
        return screenshot;
    }

    @Step("Tap on {0}")
    public void click(WebElement element){
        element.click();
    }

    @Step("Input text: {1} in {0}")
    public void inputText(WebElement element, String textString){
        element.sendKeys(textString);
    }

    @Step("Tap on {0}th element in suggest")
    public void clickOnSuggest(int suggestIndex){
        List<WebElement> suggestList = Lists.reverse(yabroObjects.reversedSuggestList);
        suggestList.get(suggestIndex-1).click();
    }

    @Step("Log should contain Tag: {0} and String: {1}")
    public void shouldBeInLog(String logTag, String logString) throws Exception {
        assertThat("\"" + logTag + "\" and \"" + logString + "\"" + " not found in logs", logReader.FindString(logTag, logString));
    }
}
