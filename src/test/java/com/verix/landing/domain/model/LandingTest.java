package com.verix.landing.domain.model;

import com.verix.landing.domain.model.exception.InvalidPropertyException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


public class LandingTest {
    public static final String EMPTY_STRING = "";
    public static final String INVALID_CHARS = "!¡'¿+ç´%*";

    @Test
    public void Given_all_fields_data_When_try_to_create_Then_create_a_valid_result()
    {
        //Arrange
        Landing expected = new Landing(
                "10036/45578",
                "B8RW",
                "Intranet Portal - SBP",
                "Microsoft",
                "Operating System",
                "Windows Server",
                "45578",
                "Windows Server 2016 Standard 10.0.14393.5502",
                ">36 Months",
                "Francisco Aristeguieta Silva",
                "Peru",
                "Tier 2",
                "Yes",
                "No",
                "Draft",
                "38860",
                "DES-WS2016-PG",
                "11/01/2025",
                "10/31/2026",
                "Yes",
                "WS2016",
                "En revisión.",
                "0",
                "0",
                "0",
                "01/12/2027",
                "01/12/2027",
                "01/12/2027",
                "No",
                "Peru",
                "No",
                "false",
                "Production",
                "Prod,DR,UAT/QAT/Pre-PROD,IST,Dev"
        );

        //Act
        Landing result = LandingStub.allFields();

        //Assert
        assertNotNull(result.getUniqueComponentId());
        assertEquals(expected.getUniqueComponentId(), result.getUniqueComponentId());

        assertNotNull(result.getApmCode());
        assertEquals(expected.getApmCode(), result.getApmCode());

        assertNotNull(result.getAppName());
        assertEquals(expected.getAppName(), result.getAppName());

        assertNotNull(result.getVendor());
        assertEquals(expected.getVendor(), result.getVendor());

        assertNotNull(result.getSwType());
        assertEquals(expected.getSwType(), result.getSwType());

        assertNotNull(result.getSwName());
        assertEquals(expected.getSwName(), result.getSwName());

        assertNotNull(result.getSwId());
        assertEquals(expected.getSwId(), result.getSwId());

        assertNotNull(result.getSwVersion());
        assertEquals(expected.getSwVersion(), result.getSwVersion());

        assertNotNull(result.getSwExpireIn());
        assertEquals(expected.getSwExpireIn(), result.getSwExpireIn());

        assertNotNull(result.getGroupHeadName());
        assertEquals(expected.getGroupHeadName(), result.getGroupHeadName());

        assertNotNull(result.getBusinessLines());
        assertEquals(expected.getBusinessLines(), result.getBusinessLines());

        assertNotNull(result.getDbrTier());
        assertEquals(expected.getDbrTier(), result.getDbrTier());

        assertNotNull(result.getSwValidPlan());
        assertTrue(result.getSwValidPlan());

        assertNotNull(result.getAppValidPlan());
        assertFalse(result.getAppValidPlan());

        assertNotNull(result.getSwPlanStatus());
        assertEquals(expected.getSwPlanStatus(), result.getSwPlanStatus());

        assertNotNull(result.getPlanNo());
        assertEquals(expected.getPlanNo(), result.getPlanNo());

        assertNotNull(result.getPlanName());
        assertEquals(expected.getPlanName(), result.getPlanName());

        assertNotNull(result.getPlanStartDate());
        assertEquals(expected.getPlanStartDate(), result.getPlanStartDate());

        assertNotNull(result.getPlanEndDate());
        assertEquals(expected.getPlanEndDate(), result.getPlanEndDate());

        assertNotNull(result.getPlanFunded());
        assertTrue(result.getPlanFunded());

        assertNotNull(result.getRefNumber());
        assertEquals(expected.getRefNumber(), result.getRefNumber());

        assertNotNull(result.getPlanComments());
        assertEquals(expected.getPlanComments(), result.getPlanComments());

        assertNotNull(result.getPlanExternalCost());
        assertEquals(expected.getPlanExternalCost(), result.getPlanExternalCost());

        assertNotNull(result.getPlanInternalCost());
        assertEquals(expected.getPlanInternalCost(), result.getPlanInternalCost());

        assertNotNull(result.getPlanLicenseCost());
        assertEquals(expected.getPlanLicenseCost(), result.getPlanLicenseCost());

        assertNotNull(result.getEosDate());
        assertEquals(expected.getEosDate(), result.getEosDate());

        assertNotNull(result.getExtendedDate());
        assertEquals(expected.getExtendedDate(), result.getExtendedDate());

        assertNotNull(result.getExtendedCustomDate());
        assertEquals(expected.getExtendedCustomDate(), result.getExtendedCustomDate());

        assertNotNull(result.getLocalRCMP());
        assertFalse(result.getLocalRCMP());

        assertNotNull(result.getCountryName());
        assertEquals(expected.getCountryName(), result.getCountryName());

        assertNotNull(result.getInternetFacing());
        assertFalse(result.getInternetFacing());

        assertNotNull(result.getUsFlag());
        assertFalse(result.getUsFlag());

        assertNotNull(result.getLifecycle());
        assertEquals(expected.getLifecycle(), result.getLifecycle());

        assertNotNull(result.getEnvironments());
        assertEquals(expected.getEnvironments(), result.getEnvironments());
    }

    @Test
    public void Given_only_required_data_When_try_to_create_Then_create_a_valid_result() {
        //Arrange
        Landing expected = new Landing(
                "10036/45578",
                "B8RW",
                "Intranet Portal - SBP",
                "Microsoft",
                "Operating System",
                "Windows Server",
                "45578",
                "Windows Server 2016 Standard 10.0.14393.5502",
                ">36 Months",
                "Francisco Aristeguieta Silva",
                "Peru",
                EMPTY_STRING,
                "Yes",
                "No",
                "Missing",
                EMPTY_STRING,
                EMPTY_STRING,
                EMPTY_STRING,
                EMPTY_STRING,
                EMPTY_STRING,
                EMPTY_STRING,
                EMPTY_STRING,
                EMPTY_STRING,
                EMPTY_STRING,
                EMPTY_STRING,
                EMPTY_STRING,
                EMPTY_STRING,
                EMPTY_STRING,
                "No",
                "Peru",
                "No",
                "false",
                "Production",
                "Prod,DR,UAT/QAT/Pre-PROD,IST,Dev");

        //Act
        Landing result = LandingStub.onlyRequiredFields();

        //Assert
        assertNotNull(result.getUniqueComponentId());
        assertEquals(expected.getUniqueComponentId(), result.getUniqueComponentId());

        assertNotNull(result.getApmCode());
        assertEquals(expected.getApmCode(), result.getApmCode());

        assertNotNull(result.getAppName());
        assertEquals(expected.getAppName(), result.getAppName());

        assertNotNull(result.getVendor());
        assertEquals(expected.getVendor(), result.getVendor());

        assertNotNull(result.getSwType());
        assertEquals(expected.getSwType(), result.getSwType());

        assertNotNull(result.getSwName());
        assertEquals(expected.getSwName(), result.getSwName());

        assertNotNull(result.getSwId());
        assertEquals(expected.getSwId(), result.getSwId());

        assertNotNull(result.getSwVersion());
        assertEquals(expected.getSwVersion(), result.getSwVersion());

        assertNotNull(result.getSwExpireIn());
        assertEquals(expected.getSwExpireIn(), result.getSwExpireIn());

        assertNotNull(result.getGroupHeadName());
        assertEquals(expected.getGroupHeadName(), result.getGroupHeadName());

        assertNotNull(result.getBusinessLines());
        assertEquals(expected.getBusinessLines(), result.getBusinessLines());

        assertNull(result.getDbrTier());

        assertNotNull(result.getSwValidPlan());
        assertTrue(result.getSwValidPlan());

        assertNotNull(result.getAppValidPlan());
        assertFalse(result.getAppValidPlan());

        assertNotNull(result.getSwPlanStatus());
        assertEquals(expected.getSwPlanStatus(), result.getSwPlanStatus());

        assertNull(result.getPlanNo());

        assertNull(result.getPlanName());

        assertNull(result.getPlanStartDate());

        assertNull(result.getPlanEndDate());

        assertNull(result.getPlanFunded());

        assertNull(result.getRefNumber());

        assertNull(result.getPlanComments());

        assertNull(result.getPlanExternalCost());

        assertNull(result.getPlanInternalCost());

        assertNull(result.getPlanLicenseCost());

        assertNull(result.getEosDate());

        assertNull(result.getExtendedDate());

        assertNull(result.getExtendedCustomDate());

        assertNotNull(result.getLocalRCMP());
        assertFalse(result.getLocalRCMP());

        assertNotNull(result.getCountryName());
        assertEquals(expected.getCountryName(), result.getCountryName());

        assertNotNull(result.getInternetFacing());
        assertFalse(result.getInternetFacing());

        assertNotNull(result.getUsFlag());
        assertFalse(result.getUsFlag());

        assertNotNull(result.getLifecycle());
        assertEquals(expected.getLifecycle(), result.getLifecycle());

        assertNotNull(result.getEnvironments());
        assertEquals(expected.getEnvironments(), result.getEnvironments());
    }

    @Test
    public void Given_invalid_values_for_optional_data_When_try_to_create_Then_create_a_valid_result() {

        //Arrange
        Landing expected = new Landing(
                "10036/45578",
                "B8RW",
                "Intranet Portal - SBP",
                "Microsoft" ,
                "Operating System" ,
                "Windows Server" ,
                "45578" ,
                "Windows Server 2016 Standard 10.0.14393.5502" ,
                ">36 Months" ,
                "Francisco Aristeguieta Silva" ,
                "Peru" ,
                INVALID_CHARS ,
                "Yes" ,
                "No" ,
                "Missing" ,
                INVALID_CHARS,
                INVALID_CHARS ,
                INVALID_CHARS,
                INVALID_CHARS ,
                INVALID_CHARS,
                INVALID_CHARS ,
                INVALID_CHARS,
                INVALID_CHARS ,
                INVALID_CHARS,
                INVALID_CHARS ,
                INVALID_CHARS,
                INVALID_CHARS ,
                INVALID_CHARS ,
                "No" ,
                "Peru" ,
                "No" ,
                "false" ,
                "Production" ,
                "Prod,DR,UAT/QAT/Pre-PROD,IST,Dev");

        // Act
        Landing result = LandingStub.invalidCharsForOptional();

        //Assert
        assertNotNull(result.getUniqueComponentId());
        assertEquals(expected.getUniqueComponentId(), result.getUniqueComponentId());

        assertNotNull(result.getApmCode());
        assertEquals(expected.getApmCode(), result.getApmCode());

        assertNotNull(result.getAppName());
        assertEquals(expected.getAppName(), result.getAppName());

        assertNotNull(result.getVendor());
        assertEquals(expected.getVendor(), result.getVendor());

        assertNotNull(result.getSwType());
        assertEquals(expected.getSwType(), result.getSwType());

        assertNotNull(result.getSwName());
        assertEquals(expected.getSwName(), result.getSwName());

        assertNotNull(result.getSwId());
        assertEquals(expected.getSwId(), result.getSwId());

        assertNotNull(result.getSwVersion());
        assertEquals(expected.getSwVersion(), result.getSwVersion());

        assertNotNull(result.getSwExpireIn());
        assertEquals(expected.getSwExpireIn(), result.getSwExpireIn());

        assertNotNull(result.getGroupHeadName());
        assertEquals(expected.getGroupHeadName(), result.getGroupHeadName());

        assertNotNull(result.getBusinessLines());
        assertEquals(expected.getBusinessLines(), result.getBusinessLines());

        assertNull(result.getDbrTier());

        assertNotNull(result.getSwValidPlan());
        assertTrue(result.getSwValidPlan());

        assertNotNull(result.getAppValidPlan());
        assertFalse(result.getAppValidPlan());

        assertNotNull(result.getSwPlanStatus());
        assertEquals(expected.getSwPlanStatus(), result.getSwPlanStatus());

        assertNull(result.getPlanNo());

        assertNull(result.getPlanName());

        assertNull(result.getPlanStartDate());

        assertNull(result.getPlanEndDate());

        assertNull(result.getPlanFunded());

        assertNull(result.getRefNumber());

        assertNull(result.getPlanComments());

        assertNull(result.getPlanExternalCost());

        assertNull(result.getPlanInternalCost());

        assertNull(result.getPlanLicenseCost());

        assertNull(result.getEosDate());

        assertNull(result.getExtendedDate());

        assertNull(result.getExtendedCustomDate());

        assertNotNull(result.getLocalRCMP());
        assertFalse(result.getLocalRCMP());

        assertNotNull(result.getCountryName());
        assertEquals(expected.getCountryName(), result.getCountryName());

        assertNotNull(result.getInternetFacing());
        assertFalse(result.getInternetFacing());

        assertNotNull(result.getUsFlag());
        assertFalse(result.getUsFlag());

        assertNotNull(result.getLifecycle());
        assertEquals(expected.getLifecycle(), result.getLifecycle());

        assertNotNull(result.getEnvironments());
        assertEquals(expected.getEnvironments(), result.getEnvironments());
    }

    @Test
    public void Given_invalid_values_for_required_data_When_try_to_create_Then_throw_an_exception() {
        //Arrange, Act and Assert
        assertThrows(InvalidPropertyException.class,
                LandingStub::invalidCharsForRequiredFields);
    }

    @Test
    public void Given_only_optional_data_When_try_to_create_Then_throw_an_exception() {
        //Arrange, Act and Assert
        assertThrows(InvalidPropertyException.class,
                LandingStub::onlyOptionalFields);

    }

    @Test
    public void Given_null_values_for_required_data_When_try_to_create_Then_throw_an_exception() {
        //Arrange, Act and Assert
        assertThrows(InvalidPropertyException.class,
                LandingStub::nullForRequiredFields);
    }

}
