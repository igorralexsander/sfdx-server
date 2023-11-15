package com.igorralexsander.sfdxserver.model.cliresult;

public record OrgSF(
        String id,
        String username,
        String accessToken,
        String instanceUrl,
        String clientId,
        String apiVersion,
        String connectedStatus,
        String alias) { }
