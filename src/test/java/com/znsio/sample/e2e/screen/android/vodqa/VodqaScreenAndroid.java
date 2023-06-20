package com.znsio.sample.e2e.screen.android.vodqa;

import com.applitools.eyes.appium.Target;
import com.znsio.sample.e2e.screen.vodqa.NativeViewScreen;
import com.znsio.sample.e2e.screen.vodqa.VodqaScreen;
import com.znsio.sample.e2e.screen.vodqa.WebViewScreen;
import com.znsio.teswiz.runner.Driver;
import com.znsio.teswiz.runner.Runner;
import com.znsio.teswiz.runner.Visual;
import com.znsio.teswiz.tools.cmd.CommandLineExecutor;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import io.appium.java_client.AppiumBy;
import org.openqa.selenium.Point;


public class VodqaScreenAndroid extends VodqaScreen {
    private final Driver driver;
    private final Visual visually;
    private final String SCREEN_NAME = VodqaScreenAndroid.class.getSimpleName();
    private static final Logger LOGGER = Logger.getLogger(VodqaScreenAndroid.class.getName());

    private final By byLoginButton = AppiumBy.xpath("//android.view.ViewGroup[@content-desc='login']/android.widget.Button");
    private final By byWebViewSectionOptionXpath = AppiumBy.xpath("//android.view.ViewGroup[@content-desc='webView']");
    private final By byNativeViewSectionXpath = AppiumBy.xpath("//android.view.ViewGroup[@content-desc='chainedView']");
    private final By byVerticalSwipeViewGroup = AppiumBy.xpath("//android.view.ViewGroup[@content-desc='verticalSwipe']");
    private final By byCLanguageTextView = AppiumBy.xpath("//android.widget.TextView[@text=' C']");
    private final By byRubyLanguageTextView = AppiumBy.xpath("//android.widget.TextView[@text=' Ruby']");
    private final String languageTextView = "//android.widget.TextView[@text=' %s']";

    public VodqaScreenAndroid(Driver driver, Visual visually) {
        this.driver = driver;
        this.visually = visually;
    }

    @Override
    public VodqaScreen login() {
        driver.waitTillElementIsPresent(byLoginButton);
        visually.checkWindow(SCREEN_NAME, "Login Screen");
        driver.findElement(byLoginButton).click();
        return this;
    }

    @Override
    public VodqaScreen putAppInTheBackground(int time) {
        driver.putAppInBackgroundFor(time);
        visually.checkWindow(SCREEN_NAME, "App screen should visible after putting app in background");
        return this;
    }

    @Override
    public boolean isAppWorkingInBackground() {
        LOGGER.info("Validating current app package to know app work in background");
        String adbCommand = "adb shell dumpsys window | grep -E 'mCurrentFocus'";
        LOGGER.info(adbCommand);
        String currentOpenApp = CommandLineExecutor.execCommand(new String[]{adbCommand}).toString();
        String currentAppPackageName = Runner.getAppPackageName();
        return currentOpenApp.contains(currentAppPackageName);
    }

    public WebViewScreen enterIntoNewsWebViewSection() {
        LOGGER.info("Enter into news web view section");
        visually.checkWindow(SCREEN_NAME, "Sample List Screen");
        driver.waitTillElementIsVisible(byWebViewSectionOptionXpath).click();
        return WebViewScreen.get();
    }

    @Override
    public NativeViewScreen enterIntoNativeViewSection() {
        LOGGER.info("Enter into native view section");
        visually.checkWindow(SCREEN_NAME, "Sample List Screen");
        driver.waitTillElementIsVisible(byNativeViewSectionXpath).click();
        return NativeViewScreen.get();
    }

    @Override
    public VodqaScreen scrollFromOneElementPointToAnother() {
        driver.waitTillElementIsPresent(byVerticalSwipeViewGroup);
        visually.checkWindow(SCREEN_NAME, "Home Screen");
        driver.findElement(byVerticalSwipeViewGroup).click();
        driver.waitTillElementIsPresent(byCLanguageTextView);
        visually.checkWindow(SCREEN_NAME, "Vertical Swiping Screen Before Scroll");
        Point fromPoint = driver.findElement(byRubyLanguageTextView).getLocation();
        Point toPoint = driver.findElement(byCLanguageTextView).getLocation();
        driver.scroll(fromPoint, toPoint);
        visually.checkWindow(SCREEN_NAME, "Vertical Swiping Screen After Scroll");
        return this;
    }

    @Override
    public boolean isElementWithTextVisible(String elementText) {
        By byLanguageTextView = AppiumBy.xpath(String.format(this.languageTextView, elementText));
        visually.check(SCREEN_NAME, String.format("%s language element text view", elementText), Target.region(byLanguageTextView));
        return driver.isElementPresent(byLanguageTextView);
    }
}