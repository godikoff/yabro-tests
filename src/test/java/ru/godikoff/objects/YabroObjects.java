package ru.godikoff.objects;

import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

import java.util.List;
import java.util.concurrent.TimeUnit;


public class YabroObjects {
    public YabroObjects(WebDriver driver) {
        PageFactory.initElements(new AppiumFieldDecorator(driver, 5, TimeUnit.SECONDS), this);
    }

    public HistorySuggest historySuggest;
    public WizardSuggest wizardSuggest;

    @AndroidFindBy(id = "bro_sentry_bar_fake_text")
    public AndroidElement omniboxInNewTab;

    @AndroidFindBy(id = "bro_omnibar_address_title_view")
    public AndroidElement omniboxInCurrentTab;

    @AndroidFindBy(id = "activity_tutorial_close_button")
    public AndroidElement tutorialCloseButton;

    @AndroidFindBy(id = "import_checkbox_mark")
    public AndroidElement welcomeScreenCheckbox;

    @AndroidFindBy(id = "activity_import_next_button")
    public AndroidElement welcomeScreenNextButton;

    @AndroidFindBy(id = "bro_sentry_bar_input_edittext")
    public AndroidElement omniboxTextField;

    @AndroidFindBy(id = "bro_common_omnibox_text_layout")
    public List<AndroidElement> reversedSuggestList;

    @AndroidFindBy(id = "bro_sentry_bar_input_blue_link")
    public AndroidElement omniNavigationLink;

    @AndroidFindBy(id = "bro_root_layout")
    public AndroidElement broRootLayout;

    @AndroidFindBy(id = "bro_zen_recyclerview")
    public AndroidElement zenView;

    @AndroidFindBy(id = "bro_zen_sentry_iceboarding_card_more_button")
    public AndroidElement iceMoreButton;
}
