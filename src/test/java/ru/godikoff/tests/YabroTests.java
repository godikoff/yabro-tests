package ru.godikoff.tests;

import io.appium.java_client.android.AndroidDriver;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestWatcher;
import org.junit.runner.Description;
import ru.godikoff.objects.YabroObjects;
import ru.godikoff.steps.YabroSteps;
import ru.godikoff.utils.AndroidDriverInitializer;
import ru.yandex.qatools.allure.annotations.Title;

import java.awt.Color;

public class YabroTests {
    private AndroidDriver driver;
    private YabroObjects yabroObjects;
    private YabroSteps yabroSteps;

    @Before
    public void before() throws Exception{
        AndroidDriverInitializer androidDriverInitializer = new AndroidDriverInitializer();
        yabroObjects = new YabroObjects(driver);
        yabroSteps = new YabroSteps(driver);
        driver = androidDriverInitializer.getDriver();
        yabroSteps.browserStart();
    }

    @Rule
    public TestWatcher testWatcher = new TestWatcher() {
        @Override
        protected void failed(Throwable e, Description description) {
            yabroSteps.saveScreenshot();
        }

        @Override
        protected void finished(Description description) {
            driver.quit();
        }
    };


    @Title("Тап по 3 элементу саджеста и ожидание загрузки страницы")
    @Test
    public void searchFromSuggestWithSteps() throws Exception {
        yabroSteps.click(yabroObjects.omniboxInNewTab);
        yabroSteps.inputText(yabroObjects.omniboxTextField, "qwe");
        yabroSteps.clickOnSuggest(3);
        yabroSteps.shouldBeInLog("Ya:ReportManager", "url opened");
    }

    @Title("Фейлящийся тап по 3 элементу саджеста и ожидание")
    @Test
    public void failSearchFromSuggestWithSteps() throws Exception {
        yabroSteps.click(yabroObjects.omniboxInNewTab);
        yabroSteps.inputText(yabroObjects.omniboxTextField, "qwe");
        yabroSteps.clickOnSuggest(3);
        yabroSteps.shouldBeInLog("Ya:ReportManager", "urlopened");
    }

    @Title("Проверка, что в саджесте больше 1 элемента")
    @Test
    public void checkCountOfSuggestElements() {
        yabroSteps.click(yabroObjects.omniboxInNewTab);
        yabroSteps.inputText(yabroObjects.omniboxTextField, "qwe");
        yabroSteps.hasMoreElementsThan(yabroObjects.reversedSuggestList, 1);
    }

    @Title("Проверка цвета текста в историческом саджесте")
    @Test
    public void checkColorOfHistorySuggest() throws Exception {
        Color historySuggestColor1 = new Color(148, 148, 148);
        Color historySuggestColor2 = new Color(86, 28, 140);
        yabroSteps.click(yabroObjects.omniboxInNewTab);
        yabroSteps.inputAndSendText(yabroObjects.omniboxTextField, "cat");
        yabroSteps.click(yabroObjects.omniboxInCurrentTab);
        yabroSteps.inputText(yabroObjects.omniboxTextField, "cat");
        yabroSteps.shouldContainColors(yabroObjects.historySuggest.suggestText, historySuggestColor1,
                historySuggestColor2);
    }

    @Title("Проверка текста и элементов в колдунщике саджеста")
    @Test
    public void checkTextInWizardSuggest() throws Exception {
        yabroSteps.click(yabroObjects.omniboxInNewTab);
        yabroSteps.inputText(yabroObjects.omniboxTextField, "pogoda");
        yabroSteps.shouldNotBeDisplayed(yabroObjects.historySuggest.clockIcon);
        yabroSteps.shouldContainText(yabroObjects.wizardSuggest.wizardText, "°C");
    }
}
