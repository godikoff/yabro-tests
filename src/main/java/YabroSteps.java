import com.google.common.collect.Lists;
import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.WebElement;
import ru.yandex.qatools.allure.annotations.Step;

import java.util.List;


public class YabroSteps {
    private LogReader logReader;

    @Step("Tap on {0}")
    public void click(WebElement element){
        element.click();
    }

    @Step("Input text:'{1}' in '{0}'")
    public void inputText(WebElement element, String textString){
        element.sendKeys(textString);
    }

    @Step("Tap on {1}'th element in suggest")
    public void clickOnSuggest(List<WebElement> element, int suggestIndex){
        List<WebElement> suggestList = Lists.reverse(element);
        suggestList.get(suggestIndex-1).click();
    }

    @Step("Log should contain Tag:'{1}' and String: '{2}'")
    public void shouldBeInLog(AppiumDriver driver, String logTag, String logString) throws Exception {
        logReader = new LogReader();
        logReader.FindString(driver, logTag, logString);
    }
}
