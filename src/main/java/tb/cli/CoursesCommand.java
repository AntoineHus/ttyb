package tb.cli;

import com.beust.jcommander.Parameter;
import com.beust.jcommander.Parameters;

@Parameters(commandDescription = "Display registered courses and associated grades")
public class CoursesCommand {
    @Parameter(names = "--json", description = "Output results in JSON format")
    public boolean jsonOutput;
}
