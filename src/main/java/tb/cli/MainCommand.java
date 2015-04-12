package tb.cli;

import com.beust.jcommander.Parameter;
import com.beust.jcommander.Parameters;

@Parameters(separators = "=")
public class MainCommand {
    @Parameter(names = "--help", description = "Display help", help = true)
    public boolean help;

    @Parameter(names = "--driver", description = "WebDriver to use")
    public String driver = "";

    @Parameter(names = "--username", description = "Username")
    public String username;

    @Parameter(names = "--password", description = "Password")
    public String password;
}