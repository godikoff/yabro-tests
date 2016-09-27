import io.appium.java_client.AppiumDriver;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

import java.util.List;


public class YabroObjects {

    public YabroObjects(AppiumDriver driver) {
        PageFactory.initElements(new AppiumFieldDecorator(driver), this);
    }

    @AndroidFindBy(id = "bro_sentry_bar_fake_text")
    public WebElement omniboxInNewTab;

    @AndroidFindBy(id = "bro_omnibar_address_title_view")
    public WebElement omniboxInCurrentTab;

    @AndroidFindBy(id = "activity_tutorial_close_button")
    public WebElement tutorialCloseButton;

    @AndroidFindBy(id = "import_checkbox_mark")
    public WebElement welcomeScreenCheckbox;

    @AndroidFindBy(id = "activity_import_next_button")
    public WebElement welcomeScreenNextButton;

    @AndroidFindBy(id = "bro_sentry_bar_input_edittext")
    public WebElement omniboxTextField;

    @AndroidFindBy(id = "bro_common_omnibox_text_layout")
    public List<WebElement> reversedSuggestList;

    @AndroidFindBy(id = "bro_suggest_search_history")
    public WebElement historySearchSuggest;
}
