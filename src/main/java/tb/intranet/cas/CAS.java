package tb.intranet.cas;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import tb.intranet.cas.exceptions.InvalidCredentialsException;
import tb.intranet.cas.exceptions.UserAlreadyLoggedException;
import tb.intranet.cas.exceptions.UserNotLoggedException;

/**
 * Central Authentication Service (CAS) Helper.
 *
 * Provides login and logout methods to any classes that uses a WebDriver.
 *
 * Classes that needs authentication to access Télécom Bretagne intranet
 * can extend CAS to automatically includes login and logout methods.
 */
public class CAS {
    protected WebDriver driver;
    private boolean userIsLogged;

    /**
     * Constructs a CAS Helper with the specified driver.
     * @param driver the WebDriver to use for authentication.
     */
    public CAS(WebDriver driver) {
        this.driver = driver;
        this.userIsLogged = false;
    }

    /**
     * Authenticate the user with CAS.
     * @param username the username.
     * @param password the password.
     * @throws InvalidCredentialsException if the username and/or password are invalid.
     * @throws UserAlreadyLoggedException if a user is already logged.
     */
    public void login(String username, String password) throws InvalidCredentialsException, UserAlreadyLoggedException {
        driver.get("https://login.telecom-bretagne.eu/cas/login?locale=en");

        updateLoginStatus();
        if (userIsLogged) { throw new UserAlreadyLoggedException(); }

        WebElement usernameField = driver.findElement(By.name("username"));
        usernameField.sendKeys(username);

        WebElement passwordField = driver.findElement(By.name("password"));
        passwordField.sendKeys(password);

        passwordField.submit();

        updateLoginStatus();
        if (!userIsLogged) { throw new InvalidCredentialsException(); }
    }

    /**
     * Logout the user from CAS (not implemented yet).
     * @return true if the user is successfully logged out. false if the user could not be logged out.
     */
    public boolean logout() {
        // TODO: Add logout support when CAS will have the ability.
        return false;
    }

    /**
     * Throws an exception if the user is not logged.
     * @throws UserNotLoggedException if the user is not logged.
     */
    public void ensureUserIsLogged() throws UserNotLoggedException {
        if (!userIsLogged) {
            throw new UserNotLoggedException();
        }
    }

    /**
     * Determine if the user is logged and update the instance variable.
     * Note: this should be called while on the login page, the behavior
     * is undetermined otherwise.
     */
    private void updateLoginStatus() {
        try {
            WebElement successMessage = driver.findElement(By.cssSelector("#msg.success"));
            userIsLogged = true;
        } catch (NoSuchElementException e) {
            userIsLogged = false;
        }
    }
}
