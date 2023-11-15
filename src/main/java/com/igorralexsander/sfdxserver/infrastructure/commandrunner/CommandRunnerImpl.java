package com.igorralexsander.sfdxserver.infrastructure.commandrunner;

import com.igorralexsander.sfdxserver.model.CommandRunner;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class CommandRunnerImpl implements CommandRunner {
    @Override
    public String runCommand(String command) throws Exception {
        Process proc = Runtime.getRuntime().exec(command);
        var bufferedReader = new BufferedReader(new InputStreamReader(proc.getInputStream()));
        String line;
        var output = new StringBuilder();
        while((line = bufferedReader.readLine()) != null) {
            output.append(line);
        }
        proc.waitFor();
        return output.toString();
    }
}
