import io.appium.java_client.AppiumDriver;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebElement;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class PixelCollector {
    private AppiumDriver driver;

    public PixelCollector(AppiumDriver driver) {
        this.driver = driver;
    }

    public List<Color> collector(WebElement element) throws Exception {
        List<Color> colorArray = new ArrayList<Color>();

        File scFile = driver.getScreenshotAs(OutputType.FILE);
        BufferedImage fullScreenshot = ImageIO.read(scFile);

        Point elementLocation = element.getLocation();
        int elementWidth = element.getSize().getWidth();
        int elementHeight = element.getSize().getHeight();

        BufferedImage elementScreenshot = fullScreenshot.getSubimage(elementLocation.getX(), elementLocation.getY(), elementWidth,
                elementHeight);

        //ImageIO.write(elementScreenshot, "png", scFile);
        //FileUtils.copyFile(scFile, new File("screenshots/screen.png"));

        for (int x = 0; x < elementWidth; x++) {
            for (int y = 0; y < elementHeight; y++) {
                int rgb = elementScreenshot.getRGB(x, y);
                Color pixelColor = new Color(rgb);
                colorArray.add(pixelColor);
            }
        }
        return colorArray;
    }
}
