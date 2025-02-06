package com.verix.apm.domain.model;

import com.verix.apm.domain.model.Apm;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class ApmTest {
    private static final String EMPTY_STRING = "";
    private static final String INVALID_CHAR = "!¡'¿+ç´%*";

    @Test
    void Given_a_valid_data_When_try_to_create_Then_create_a_valid_apm() {

        // Arrange
        String apmCode = "B3TT";
        String apmName = "Ahorro Leasing";
        String isCompliant = "Yes";
        String cia = "No";
        String lcState = "Production";
        String productionDate = "01-Oct-2009";
        String retirementDate = "01-Jan-2050";
        String dbrRating = "Medium: Operational within 10 days";
        String applicationTested = "Yes";
        String applicationContact = "Juan Oyaneder Bello";
        String manager = "Gerardo Mc Cabe Rodriguez";
        String vp = "Jorge Emilio Onate Bernal";
        String svp = "Francisco Palma Maturana";
        String portfolioOwner = "Francisco Palma Maturana";
        String iso = "Alvaro Mauricio Taricco Lopez";
        String country = "CL";

        //Act
        Apm apm = new Apm(
                apmCode,
                apmName,
                isCompliant,
                cia,
                lcState,
                LifeDate.create(productionDate),
                LifeDate.create(retirementDate),
                dbrRating,
                applicationTested,
                applicationContact,
                manager,
                vp,
                svp,
                portfolioOwner,
                iso,
                country);

        //Assert
        assertNotNull(apm.getApmCode());
        assertNotEquals(EMPTY_STRING, apm.getApmCode());
        assertEquals(apmCode, apm.getApmCode());

        assertNotNull(apm.getApmName());
        assertNotEquals(EMPTY_STRING, apm.getApmName());
        assertEquals(apmName, apm.getApmName());

        assertNotNull(apm.getIsCompliant());
        assertNotEquals(EMPTY_STRING, apm.getIsCompliant());
        assertEquals(Boolean.TRUE, apm.getIsCompliant()); // Si "Yes" debe mapearse a true

        assertNotNull(apm.getCia());
        assertNotEquals(EMPTY_STRING, apm.getCia());
        assertEquals(Boolean.FALSE, apm.getCia());

        assertNotNull(apm.getLcState());
        assertNotEquals(EMPTY_STRING, apm.getLcState());
        assertEquals(lcState, apm.getLcState());

        assertNotNull(apm.getProductionDate().getValue());
        assertNotEquals(EMPTY_STRING, apm.getProductionDate().getValue());
        assertEquals("2009-10-01", apm.getProductionDate().getValue());

        assertNotNull(apm.getRetirementDate().getValue());
        assertNotEquals(EMPTY_STRING, apm.getRetirementDate().getValue());
        assertEquals("2050-01-01", apm.getRetirementDate().getValue());

        assertNotNull(apm.getDbrRating());
        assertNotEquals(EMPTY_STRING, apm.getDbrRating());
        assertEquals(dbrRating, apm.getDbrRating());

        assertNotNull(apm.getApplicationTested());
        assertNotEquals(EMPTY_STRING, apm.getApplicationTested());
        assertEquals(Boolean.TRUE, apm.getApplicationTested());

        assertNotNull(apm.getApplicationContact());
        assertNotEquals(EMPTY_STRING, apm.getApplicationContact());
        assertEquals(applicationContact, apm.getApplicationContact());

        assertNotNull(apm.getManager());
        assertNotEquals(EMPTY_STRING, apm.getManager());
        assertEquals(manager, apm.getManager());

        assertNotNull(apm.getVp());
        assertNotEquals(EMPTY_STRING, apm.getVp());
        assertEquals(vp, apm.getVp());

        assertNotNull(apm.getSvp());
        assertNotEquals(EMPTY_STRING, apm.getSvp());
        assertEquals(svp, apm.getSvp());

        assertNotNull(apm.getPortfolioOwner());
        assertNotEquals(EMPTY_STRING, apm.getPortfolioOwner());
        assertEquals(portfolioOwner, apm.getPortfolioOwner());

        assertNotNull(apm.getIso());
        assertNotEquals(EMPTY_STRING, apm.getIso());
        assertEquals(iso, apm.getIso());

        assertNotNull(apm.getCountry());
        assertNotEquals(EMPTY_STRING, apm.getCountry());
        assertEquals(country, apm.getCountry());
    }

    //create an apm object with options values
    @Test
    void Given_data_with_optional_values_When_try_to_create_Then_create_a_valid_apm() {
        //Arrange
        String apmCode = "B3WG";
        String apmName = "Leasing Financiero";
        String isCompliant = "Yes";
        String cia = "No";
        String lcState = EMPTY_STRING;
        String productionDate = EMPTY_STRING;
        String retirementDate = "01-Jan-2050";
        String dbrRating = EMPTY_STRING;
        String applicationTested = EMPTY_STRING;
        String applicationContact = "Emily Dadashev";
        String manager = EMPTY_STRING;
        String vp = "David Conboy";
        String svp = EMPTY_STRING;
        String portfolioOwner = "Judith Fuentes";
        String iso = EMPTY_STRING;
        String country = EMPTY_STRING;


        //Act
        Apm apm = new Apm(
                apmCode,
                apmName,
                isCompliant,
                cia,
                lcState,
                LifeDate.create(productionDate),
                LifeDate.create(retirementDate),
                dbrRating,
                applicationTested,
                applicationContact,
                manager,
                vp,
                svp,
                portfolioOwner,
                iso,
                country);

        //Assert
        assertNotNull(apm.getApmCode());
        assertNotEquals(EMPTY_STRING, apm.getApmCode());
        assertEquals(apmCode, apm.getApmCode());

        assertNotNull(apm.getApmName());
        assertNotEquals(EMPTY_STRING, apm.getApmName());
        assertEquals(apmName, apm.getApmName());

        assertNotNull(apm.getIsCompliant());
        assertNotEquals(EMPTY_STRING, apm.getIsCompliant());
        assertEquals(Boolean.TRUE, apm.getIsCompliant()); // Si "Yes" debe mapearse a true

        assertNotNull(apm.getCia());
        assertNotEquals(EMPTY_STRING, apm.getCia());
        assertEquals(Boolean.FALSE, apm.getCia());

        assertNotNull(apm.getRetirementDate().getValue());
        assertNotEquals(EMPTY_STRING, apm.getRetirementDate().getValue());
        assertEquals("2050-01-01", apm.getRetirementDate().getValue());

        assertNotNull(apm.getApplicationContact());
        assertNotEquals(EMPTY_STRING, apm.getApplicationContact());
        assertEquals(applicationContact, apm.getApplicationContact());

        assertNotNull(apm.getVp());
        assertNotEquals(EMPTY_STRING, apm.getVp());
        assertEquals(vp, apm.getVp());

        assertNotNull(apm.getPortfolioOwner());
        assertNotEquals(EMPTY_STRING, apm.getPortfolioOwner());
        assertEquals(portfolioOwner, apm.getPortfolioOwner());

        assertNull(apm.getLcState());
        assertNull(apm.getDbrRating());
        assertNull(apm.getProductionDate().getValue());
        assertNull(apm.getManager());
        assertNull(apm.getSvp());
        assertNull(apm.getIso());

    }

    @Test
    void Given_data_with_optional_and_invalid_values_When_try_to_create_Then_create_a_valid_apm() {
        //Arrange
        String apmCode = "B3WG";
        String apmName = "Leasing Financiero";
        String isCompliant = "Yes";
        String cia = "No";
        String lcState = INVALID_CHAR;
        String productionDate = INVALID_CHAR;
        String retirementDate = "01-Jan-2050";
        String dbrRating = INVALID_CHAR;
        String applicationTested = INVALID_CHAR;
        String applicationContact = "Emily Dadashev";
        String manager = INVALID_CHAR;
        String vp = "David Conboy";
        String svp = INVALID_CHAR;
        String portfolioOwner = "Judith Fuentes";
        String iso = INVALID_CHAR;
        String country = "CL";

        //Act
        Apm apm = new Apm(
                apmCode,
                apmName,
                isCompliant,
                cia,
                lcState,
                LifeDate.create(productionDate),
                LifeDate.create(retirementDate),
                dbrRating,
                applicationTested,
                applicationContact,
                manager,
                vp,
                svp,
                portfolioOwner,
                iso,
                country);

        //Assert
        assertNotNull(apm.getApmCode());
        assertNotEquals(EMPTY_STRING, apm.getApmCode());
        assertEquals(apmCode, apm.getApmCode());

        assertNotNull(apm.getApmName());
        assertNotEquals(EMPTY_STRING, apm.getApmName());
        assertEquals(apmName, apm.getApmName());

        assertNotNull(apm.getIsCompliant());
        assertNotEquals(EMPTY_STRING, apm.getIsCompliant());
        assertEquals(Boolean.TRUE, apm.getIsCompliant());

        assertNotNull(apm.getCia());
        assertNotEquals(EMPTY_STRING, apm.getCia());
        assertEquals(Boolean.FALSE, apm.getCia());

        assertNull(apm.getLcState());

        assertNull(apm.getProductionDate().getValue());

        assertNotNull(apm.getRetirementDate().getValue());
        assertNotEquals(EMPTY_STRING, apm.getRetirementDate().getValue());
        assertEquals("2050-01-01", apm.getRetirementDate().getValue());

        assertNull(apm.getDbrRating());

        assertNull(apm.getApplicationTested());

        assertNotNull(apm.getApplicationContact());
        assertNotEquals(EMPTY_STRING, apm.getApplicationContact());
        assertEquals(applicationContact, apm.getApplicationContact());

        assertNull(apm.getManager());

        assertNotNull(apm.getVp());
        assertNotEquals(EMPTY_STRING, apm.getVp());
        assertEquals(vp, apm.getVp());

        assertNull(apm.getSvp());

        assertNotNull(apm.getPortfolioOwner());
        assertNotEquals(EMPTY_STRING, apm.getPortfolioOwner());
        assertEquals(portfolioOwner, apm.getPortfolioOwner());

        assertNull(apm.getIso());

        assertEquals(country, apm.getCountry());
    }
}
