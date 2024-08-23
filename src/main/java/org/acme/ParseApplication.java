package org.acme;

import org.acme.commands.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import picocli.CommandLine;

/**
 * Acme Application main class
 *
 * Acme Flight is an application that processes some flight data to provide the different results.
 */
@SpringBootApplication
public class ParseApplication implements CommandLineRunner{
    /**
     * Logger
     */
    private static final Logger log = LoggerFactory.getLogger(ParseApplication.class);
    /**
     * Parent command that does nothing
     */
    private ParseCommand parseCommand;
    /**
     * Command that gets the list of most downloaded layers
     */
    private GetListOfMostDownloadedLayers mostDownloadedRunCommand;

    private GetArtifactsDetailsFromName artifactsDetails;

    /**
     * Constructor
     *
     * @param parseCommand Parent command that does nothing
     * @param mostDownloadedRunCommand List of most downloaded layers
     */
    @Autowired
    public ParseApplication(ParseCommand parseCommand, GetListOfMostDownloadedLayers mostDownloadedRunCommand, GetArtifactsDetailsFromName artifactsDetails) {
        this.parseCommand = parseCommand;
        this.mostDownloadedRunCommand = mostDownloadedRunCommand;
        this.artifactsDetails = artifactsDetails;
    }

    /**
     * @param args Application arguments
     * @throws Exception
     */
    public static void main(String[] args) {
        try {
            SpringApplication.run(ParseApplication.class, args);
        } catch(Throwable th){
            th.printStackTrace();
        }
    }

    /**
     * @param args Arguments to pass to the commands
     * @throws Exception
     */
    @Override
    public void run(String... args) throws Exception {

        CommandLine commandLine = new CommandLine(parseCommand);
        commandLine.parseWithHandler(new CommandLine.RunLast(), args);
    }

}
