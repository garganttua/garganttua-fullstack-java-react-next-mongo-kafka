package com.garganttua.noghost.backend.api.enterprises;

import org.springframework.stereotype.Service;

@Service
public class LegalNatures implements ILegalNatures {

    @Override
    public String getLabelFromCode(int code) {
        return LegalNaturesLoader.legalNatures.get(code);
    }

}
