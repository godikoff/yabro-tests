import com.google.common.collect.Lists;
import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.WebElement;
import ru.yandex.qatools.allure.annotations.Attachment;
import ru.yandex.qatools.allure.annotations.Step;

import java.awt.*;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.both;
import static org.hamcrest.Matchers.hasItem;


public class YabroSteps {
    private LogReader logReader;
    private YabroObjects yabroObjects;
    private AppiumDriver driver;
    private PixelCollector pixelCollector;

    public YabroSteps(AppiumDriver driver) {
        this.driver = driver;
        logReader = new LogReader(driver);
        yabroObjects = new YabroObjects(driver);
        pixelCollector = new PixelCollector(driver);

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

    @Step("Input and send text: {1} in {0}")
    public void inputAndSendText(WebElement element, String textString){
        element.sendKeys(textString+"\n");
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

    @Step("Element should be displayed")
    public void shouldBeDisplayed(WebElement element) {
        assertThat(element + " not found", element.isDisplayed());
    }

    @Step("{0} has more than {1} element")
    public void hasMoreElementsThan(List<WebElement> list, int count) {
        assertThat(list + " size equals or less than " + count, list.size()>count);
    }

    @Step("{0} should contain {1} and {2} colors")
    public void shouldContainColors(WebElement element, Color color1, Color color2) throws Exception {
        assertThat(color1 + " and " + color2 + " not found in " + element, pixelCollector.collector(element), both(hasItem(color1)).and(hasItem(color2)));
    }

    @Step("Start browser")
    public void browserStart() {
        try {
            yabroObjects.omniboxInNewTab.isDisplayed();
        }
        catch (Exception e)
        {
            try {
                yabroObjects.tutorialCloseButton.isDisplayed();
            }
            catch (Exception e1) {
                yabroObjects.welcomeScreenCheckbox.click();
                yabroObjects.welcomeScreenNextButton.click();
            }
            finally {
                yabroObjects.tutorialCloseButton.click();
            }
        }
        finally {
            assertThat("Browser not started", yabroObjects.omniboxInNewTab.isDisplayed());
        }
    }
}
