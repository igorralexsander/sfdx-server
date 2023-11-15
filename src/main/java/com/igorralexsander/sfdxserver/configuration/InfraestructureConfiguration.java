package com.igorralexsander.sfdxserver.configuration;

import com.igorralexsander.sfdxserver.infrastructure.filereader.FileReaderImpl;
import com.igorralexsander.sfdxserver.model.CommandRunner;
import com.igorralexsander.sfdxserver.infrastructure.commandrunner.CommandRunnerImpl;
import com.igorralexsander.sfdxserver.model.FileReader;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class InfraestructureConfiguration {

    @Bean
    public CommandRunner getCommandRunner(){
        return new CommandRunnerImpl();
    }

    @Bean
    public FileReader getFileReader(){
        return new FileReaderImpl();
    }
}
