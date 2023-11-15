package com.igorralexsander.sfdxserver.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.igorralexsander.sfdxserver.model.CommandRunner;
import com.igorralexsander.sfdxserver.model.FileReader;
import com.igorralexsander.sfdxserver.model.cliresult.AliasOrgFile;
import com.igorralexsander.sfdxserver.model.cliresult.OrgSF;
import com.igorralexsander.sfdxserver.model.cliresult.RefreshOutput;
import com.igorralexsander.sfdxserver.model.cliresult.RefreshOutputSingle;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.Optional;

@Service
public class OrgService {
    private final String SFDX_PATH;
    private final ObjectMapper jsonMapper;
    private final CommandRunner commandRunner;
    private final FileReader fileReader;

    public OrgService(final ObjectMapper jsonMapper, final CommandRunner commandRunner, final FileReader fileReader) {
        this.jsonMapper=jsonMapper;
        this.commandRunner = commandRunner;
        this.fileReader = fileReader;
        String USER_HOME = System.getProperty("user.home");
        SFDX_PATH= USER_HOME + "/.sfdx/";
    }

    public AliasOrgFile getAllOrgs() throws IOException {
        return jsonMapper.readValue(fileReader.getFile(SFDX_PATH.concat("alias.json")), AliasOrgFile.class);
    }

    public Optional<OrgSF> getOrgByAlias(String alias) throws Exception {
        var command = "sfdx org display --target-org "
                .concat(alias)
                .concat(" --json");
        var output = runCommand(command);
        var refreshOutputSingle=jsonMapper.readValue(output, RefreshOutputSingle.class);
        if(refreshOutputSingle.status().equals(0)) {
            OrgSF orgSF = refreshOutputSingle.result();
            return Optional.of(orgSF);
        }
        return Optional.empty();
    }

    public RefreshOutput renewSessions() throws Exception {
        final String sfdxOrgListCommand="sfdx org list --json";
        var output = runCommand(sfdxOrgListCommand);
        return jsonMapper.readValue(output, RefreshOutput.class);
    }

    private String runCommand(String command) throws Exception{
        return commandRunner.runCommand(command);
    }
}
