package com.igorralexsander.sfdxserver.model.cliresult;

import java.util.List;

public record RefreshOutput(Integer status, ResultOutput result) { }

record ResultOutput(List<OrgSF> nonScratchOrgs, List<OrgSF> scratchOrgs){ }
