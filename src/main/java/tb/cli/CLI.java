package tb.cli;

import ch.qos.logback.classic.Level;
import com.beust.jcommander.JCommander;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriverService;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import tb.intranet.cas.exceptions.InvalidCredentialsException;
import tb.intranet.cas.exceptions.UserAlreadyLoggedException;
import tb.intranet.cas.exceptions.UserNotLoggedException;
import tb.intranet.portal.Portal;
import tb.intranet.portal.models.AcademicYear;
import tb.intranet.portal.models.Course;

import java.util.ArrayList;
import java.util.Scanner;

public class CLI {
    private final static Logger LOGGER = LoggerFactory.getLogger(CLI.class);
    private static JCommander jc;

    public static void main(String[] args) {
        setLoggingLevel(Level.ERROR);

        MainCommand mainCommand = new MainCommand();
        jc = new JCommander(mainCommand);

        CoursesCommand coursesCommand = new CoursesCommand();
        jc.addCommand("courses", coursesCommand);

        // For debugging
        // args = new String[] { "--verbose", "--username", "foo", "--password", "bar", "courses" };

        try {
            // Throws an exception if there is unknown args
            jc.parse(args);

            // Throws an exception if no command is specified
            if (jc.getParsedCommand() == null) {
                throw new Exception();
            }
        } catch (Exception e) {
            displayUsage();
            System.exit(1);
        }
	
        if (mainCommand.help) {
            displayUsage();
            System.exit(0);
        }

        if (mainCommand.verbose) {
            setLoggingLevel(Level.INFO);
        }

        if (mainCommand.debug) {
            setLoggingLevel(Level.ALL);
        }

        WebDriver driver = getWebDriver(mainCommand.driver);

        switch (jc.getParsedCommand()) {
            case "courses":
                String[] credentials = getCredentials(mainCommand);
                displayCourses(coursesCommand, driver, credentials[0], credentials[1]);
                break;

            default:
                LOGGER.warn("Unknown command specified.");
                break;
        }

        driver.quit();
        System.exit(0);
    }

    private static void displayUsage() {
        System.out.println("usage: ttyb [--help] [--verbose] [--driver=<driver>] [--username=<username>]\n            [--password=<password>] <command> [<args>]");

        System.out.println("\nAvailable commands are:");
        for (String name : jc.getCommands().keySet()) {
            String[] row = { name, jc.getCommandDescription(name) };
            System.out.format("%-10s%s\n", row);
        }

        System.out.println("\nCredentials can be specified on the command line or in the TTYB_{USERNAME,PASSWORD}\nenvironment variables, otherwise they will be asked interactively.");
    }

    private static String[] getCredentials(MainCommand mainCommand) {
        String envUsername = System.getenv("TTYB_USERNAME");
        String envPasword  = System.getenv("TTYB_PASSWORD");

        String username = mainCommand.username == null ? envUsername : mainCommand.username;
        String password = mainCommand.password == null ? envPasword  : mainCommand.password;

        if (username == null) {
            System.out.print("Username: ");
            Scanner sc = new Scanner(System.in);
            username = sc.nextLine();
        }

        if (password == null) {
            System.out.print("Password: ");
            password = new String(System.console().readPassword());
        }

        return new String[] { username, password };
    }

    private static void displayCourses(CoursesCommand coursesCommand, WebDriver driver, String username, String password) {
        ArrayList<Course> courses = new ArrayList<Course>();
        Portal portal = new Portal(driver);

        try {
            LOGGER.info("Authenticating with CAS...");
            portal.login(username, password);

            // Select the most recent year available.
            LOGGER.info("Parsing academic years...");
            AcademicYear academicYear = portal.getAcademicYears().get(0);

            LOGGER.info("Parsing courses...");
            CoursesFormatter coursesFormatter = new CoursesFormatter(portal.getCourses(academicYear));

            System.out.println(coursesFormatter.toString(coursesCommand.jsonOutput));
        } catch (InvalidCredentialsException e) {
            LOGGER.error("Invalid username and/or password: " + e.toString());
        } catch (UserAlreadyLoggedException e) {
            LOGGER.error("An user is already authenticated: " + e.toString());
        } catch (UserNotLoggedException e) {
            LOGGER.error("The user is not authenticated: " + e.toString());
        }
    }

    private static WebDriver getWebDriver(String preference) {
        WebDriver driver;

        switch(preference.toLowerCase()) {
            case "firefox":
                LOGGER.info("Using Firefox driver.");
                driver = new FirefoxDriver();
                break;

            case "phantomjs":
                LOGGER.info("Using PhantomJS driver.");
                driver = silentPhantomJSDriver();
                break;

            default:
                LOGGER.info("Using PhantomJS driver.");
                driver = silentPhantomJSDriver();
                break;
        }

        return driver;
    }

    private static WebDriver silentPhantomJSDriver() {
        DesiredCapabilities dcap = DesiredCapabilities.phantomjs();

        String[] phantomArgs = new  String[] {
                "--webdriver-loglevel=NONE"
        };
        dcap.setCapability(PhantomJSDriverService.PHANTOMJS_CLI_ARGS, phantomArgs);

        return new PhantomJSDriver(dcap);
    }

    private static void setLoggingLevel(ch.qos.logback.classic.Level level) {
        ch.qos.logback.classic.Logger root = (ch.qos.logback.classic.Logger) org.slf4j.LoggerFactory.getLogger(ch.qos.logback.classic.Logger.ROOT_LOGGER_NAME);
        root.setLevel(level);
    }
}
