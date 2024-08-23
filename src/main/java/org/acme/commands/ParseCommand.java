package org.acme.commands;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import picocli.CommandLine.*;

@Command(description="Processes binary downloads data", subcommands = {
        GetListOfMostDownloadedLayers.class, GetArtifactsDetailsFromName.class
})
@Component
public class ParseCommand implements Runnable{
    /**
     * Logger
     */
    private static final Logger log = LoggerFactory.getLogger(ParseCommand.class);
    /**
     * Property for the Help menu
     */
    @Option(names = { "-h", "--help" }, usageHelp = true, description = "Usage")
    private boolean helpRequested = false;
    /**
     * Does nothing. Just prints a message
     */
    @Override
    public void run() {
        log.info("Parse command does nothing. Add -h option to list the available subcommands");
    }
}
