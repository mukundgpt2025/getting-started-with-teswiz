package com.znsio.sample.e2e.screen.android.vodqa;

import com.znsio.sample.e2e.screen.vodqa.NativeViewScreen;
import com.znsio.teswiz.runner.Driver;
import com.znsio.teswiz.runner.Visual;
import io.appium.java_client.AppiumBy;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;

public class NativeViewScreenAndroid extends NativeViewScreen {
    private final Driver driver;
    private final Visual visually;
    private static final String SCREEN_NAME = NativeViewScreenAndroid.class.getSimpleName();
    private static final Logger LOGGER = Logger.getLogger(SCREEN_NAME);

    private static final By byNativeViewScreenHeaderXpath = AppiumBy.xpath("//android.widget.TextView[@text = 'Native View Demo']");

    public NativeViewScreenAndroid(Driver driver, Visual visually) {
        this.driver = driver;
        this.visually = visually;
    }

    @Override
    public boolean isUserOnNativeViewScreen() {
        LOGGER.info("Verify user is on native view screen");
        visually.checkWindow(SCREEN_NAME, "Native View Screen");
        return driver.findElement(byNativeViewScreenHeaderXpath).isDisplayed();
    }
}
