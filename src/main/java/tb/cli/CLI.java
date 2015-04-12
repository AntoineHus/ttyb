package tb.cli;

import com.beust.jcommander.JCommander;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriverService;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.util.Scanner;

public class CLI {
    private static JCommander jc;

    public static void main(String[] args) {
        MainCommand mainCommand = new MainCommand();
        jc = new JCommander(mainCommand);

        CoursesCommand coursesCommand = new CoursesCommand();
        jc.addCommand("courses", coursesCommand);

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

        switch (jc.getParsedCommand()) {
            case "courses":
                String[] credentials = getCredentials(mainCommand);
                CoursesOutput coursesOutput = new CoursesOutput(getWebDriver(mainCommand.driver), credentials[0], credentials[1]);
                System.out.println(coursesOutput.toString(coursesCommand.jsonOutput));
                break;

            default:
                displayUsage();
                System.exit(1);
        }

        System.exit(0);
    }

    private static void displayUsage() {
        System.out.println("usage: ttyb [--help] [--driver=<driver>] [--username=<username>]\n            [--password=<password>] <command> [<args>]");

        System.out.println("\nAvailable commands are:");
        for (String name : jc.getCommands().keySet()) {
            String[] row = { name, jc.getCommandDescription(name) };
            System.out.format("%-10s%s\n", row);
        }

        System.out.println("\nCredentials can be specified on the command line or in the\nTTYB_{USERNAME,PASSWORD} environment variables, otherwise\nthey will be asked interactively.");
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
            Scanner sc = new Scanner(System.in);
            password = sc.nextLine();
        }

        return new String[] { username, password };
    }

    private static WebDriver getWebDriver(String preference) {
        WebDriver driver;

        switch(preference.toLowerCase()) {
            case "firefox":
                System.out.println("Using Firefox driver");
                driver = new FirefoxDriver();
                break;

            case "phantomjs":
                System.out.println("Using PhantomJS driver");
                driver = silentPhantomJSDriver();
                break;

            default:
                System.out.println("Using PhantomJS driver");
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
}
