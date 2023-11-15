package com.igorralexsander.sfdxserver.service;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.igorralexsander.sfdxserver.infrastructure.commandrunner.CommandRunnerImpl;
import com.igorralexsander.sfdxserver.infrastructure.filereader.FileReaderImpl;
import com.igorralexsander.sfdxserver.model.CommandRunner;
import com.igorralexsander.sfdxserver.model.FileReader;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

@ExtendWith(MockitoExtension.class)
public class OrgServiceTest {
    @Mock
    private FileReader fileReader;

    @Mock
    private CommandRunner commandRunner;

    private final ObjectMapper jsonMapper;

    public OrgServiceTest(){
        jsonMapper=new ObjectMapper();
        jsonMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    @Test
    public void shoudReturnAllOrgAliasesInFile() throws IOException {
        ClassLoader classLoader = getClass().getClassLoader();
        var aliasFilePath = classLoader.getResource("alias.json").getPath();

        Mockito.when(fileReader.getFile(Mockito.anyString())).thenReturn(Path.of(aliasFilePath).toFile());

        var orgService = new OrgService(jsonMapper, commandRunner, fileReader);
        var orgResult = orgService.getAllOrgs();

        Assertions.assertNotNull(orgResult);
        Assertions.assertEquals(3, orgResult.orgs().size());
    }

    @Test
    public void shoudReturnAnOrgByAlias() throws Exception {
        final String orgAlias="my-awesome-org-01";
        final String sfdxCommand = "sfdx org display --target-org " + orgAlias + " --json";

        ClassLoader classLoader=getClass().getClassLoader();
        var orgFilePath = classLoader.getResource("sfdxOrg.json").getPath();
        var orgJsonString =  Files.readString(Path.of(orgFilePath), StandardCharsets.UTF_8);

        Mockito.when(commandRunner.runCommand(sfdxCommand)).thenReturn(orgJsonString);

        var orgService = new OrgService(jsonMapper, commandRunner, fileReader);

        var myOrg=orgService.getOrgByAlias(orgAlias);

        Assertions.assertTrue(myOrg.isPresent());
        Assertions.assertEquals(orgAlias, myOrg.get().alias());
    }

    @Test
    public void givenInexistentAliasShouldReturnEmpty() throws Exception {
        final String orgAlias="my-awesome-org-12";
        final String sfdxCommand = "sfdx org display --target-org " + orgAlias + " --json";

        ClassLoader classLoader=getClass().getClassLoader();
        var orgFilePath = classLoader.getResource("sfdxOrgNotFound.json").getPath();
        var orgJsonString =  Files.readString(Path.of(orgFilePath), StandardCharsets.UTF_8);

        Mockito.when(commandRunner.runCommand(sfdxCommand)).thenReturn(orgJsonString);

        var orgService = new OrgService(jsonMapper, commandRunner, fileReader);
        var myOrg=orgService.getOrgByAlias(orgAlias);

        Assertions.assertTrue(myOrg.isEmpty());
    }
}
