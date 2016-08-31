import com.google.common.collect.Lists;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

import java.util.List;


public class YabroObjects {

    public YabroObjects(AppiumDriver driver){
        PageFactory.initElements(new AppiumFieldDecorator(driver), this);
    }

    @AndroidFindBy(id = "bro_sentry_bar_fake_text")
    public WebElement omnibox;


    @AndroidFindBy(id = "bro_sentry_bar_input_edittext")
    public WebElement omniboxTextField;


    @AndroidFindBy(id = "bro_common_omnibox_text_layout")
    public List<WebElement> reversedSuggestList;
    public List<WebElement> suggestList() {
        List<WebElement> suggestList = Lists.reverse(reversedSuggestList);
        return suggestList;
    }

    @AndroidFindBy(className = "android.webkit.WebView")
    public WebElement webView;
}
