package com.igorralexsander.sfdxserver.controller;

import com.igorralexsander.sfdxserver.model.cliresult.AliasOrgFile;

import com.igorralexsander.sfdxserver.model.cliresult.OrgSF;
import com.igorralexsander.sfdxserver.model.cliresult.RefreshOutput;
import com.igorralexsander.sfdxserver.service.OrgService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping( "/auth")
public class OrgController {
    private final OrgService orgService;

    public OrgController(OrgService orgService) {
        this.orgService = orgService;
    }

    @GetMapping
    public ResponseEntity<AliasOrgFile> getAll(){
        try {
            var orgs = orgService.getAllOrgs();
            return ResponseEntity.ok(orgs);
        }catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/{alias}")
    public ResponseEntity<OrgSF> getOrgByAlias(@PathVariable("alias") String alias){
        try {
            return orgService.getOrgByAlias(alias)
                    .map(ResponseEntity::ok)
                    .orElse(ResponseEntity.notFound().build());
        }catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PutMapping()
    public ResponseEntity<RefreshOutput> refreshSessions(){
        try {
            var org = orgService.renewSessions();
            return ResponseEntity.ok(org);
        }catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
