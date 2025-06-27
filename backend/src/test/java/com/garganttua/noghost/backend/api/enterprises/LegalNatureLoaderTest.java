package com.garganttua.noghost.backend.api.enterprises;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;

public class LegalNatureLoaderTest {

    @Test
    public void testLegalNaturesLoading() {

        assertNotNull(LegalNaturesLoader.loadCsvToMap("legalNatures.csv"));
        assertNotNull(LegalNaturesLoader.legalNatures);

    }

}
