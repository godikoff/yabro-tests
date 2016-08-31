import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.WebElement;

import java.util.List;


public class yabroObjects {

    @AndroidFindBy(id = "bro_sentry_bar_fake_text")
    public WebElement omnibox;


    @AndroidFindBy(id = "bro_sentry_bar_input_edittext")
    public WebElement omniboxTextField;


    @AndroidFindBy(id = "bro_common_omnibox_text_layout")
    public List<WebElement> suggestList;



    @AndroidFindBy(id = "android.webkit.WebView")
    public WebElement webView;
}
