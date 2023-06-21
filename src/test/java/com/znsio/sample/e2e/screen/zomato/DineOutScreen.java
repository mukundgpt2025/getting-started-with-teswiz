package com.znsio.sample.e2e.screen.zomato;

import com.znsio.sample.e2e.screen.web.zomato.DineOutScreenWeb;
import com.znsio.teswiz.entities.Platform;
import com.znsio.teswiz.runner.Driver;
import com.znsio.teswiz.runner.Drivers;
import com.znsio.teswiz.runner.Runner;
import com.znsio.teswiz.runner.Visual;
import org.apache.commons.lang3.NotImplementedException;
import org.apache.log4j.Logger;

public abstract class DineOutScreen {
    private static final String SCREEN_NAME = DineOutScreen.class.getSimpleName();
    private static final Logger LOGGER = Logger.getLogger(SCREEN_NAME);

    public static DineOutScreen get() {
        Driver driver = Drivers.getDriverForCurrentUser(Thread.currentThread().getId());
        Platform platform = Runner.fetchPlatform(Thread.currentThread().getId());
        LOGGER.info(SCREEN_NAME + ": Driver type: " + driver.getType() + ": Platform: " + platform);
        Visual visually = Drivers.getVisualDriverForCurrentUser(Thread.currentThread().getId());

        switch (platform) {
            case web:
                return new DineOutScreenWeb(driver, visually);
        }
        throw new NotImplementedException(
                SCREEN_NAME + " is not implemented in " + Runner.getPlatform());
    }

    public abstract boolean verifyRedirectionToDineoutPage();

    public abstract DineOutScreen selectLocation();

    public abstract boolean verifyLocationDisplayed();

    public abstract DineOutScreen selectSpecificRestaurant();
}
