package com.verix.landing.domain.model;

import com.verix.landing.domain.model.exception.InvalidPropertyException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


import java.util.Map;

public class LandingTest {

    @Test
    public void Given_all_fields_data_When_try_to_create_Then_create_a_valid_landing() {
        //Arrange
        Map<String, String> validValues = LandingValidStub.allFields();

        //Act
        Landing landing = new Landing(
                validValues.get("uniqueComponentId"),
                validValues.get("apmCode"),
                validValues.get("appName"),
                validValues.get("vendor"),
                validValues.get("swType"),
                validValues.get("swName"),
                validValues.get("swId"),
                validValues.get("swVersion"),
                validValues.get("swExpireIn"),
                validValues.get("groupHeadName"),
                validValues.get("businessLines"),
                validValues.get("dbrTier"),
                validValues.get("swValidPlan"),
                validValues.get("appValidPlan"),
                validValues.get("swPlanStatus"),
                validValues.get("planNo"),
                validValues.get("planName"),
                validValues.get("planStartDate"),
                validValues.get("planEndDate"),
                validValues.get("planFunded"),
                validValues.get("refNumber"),
                validValues.get("planComments"),
                validValues.get("planExternalCost"),
                validValues.get("planInternalCost"),
                validValues.get("planLicenseCost"),
                validValues.get("eosDate"),
                validValues.get("extendedDate"),
                validValues.get("extendedCustomDate"),
                validValues.get("localRCMP"),
                validValues.get("countryName"),
                validValues.get("internetFacing"),
                validValues.get("usFlag"),
                validValues.get("lifecycle"),
                validValues.get("environments")
        );

        //Assert
        assertNotNull(landing.getUniqueComponentId());
        assertEquals(validValues.get("uniqueComponentId"), landing.getUniqueComponentId());

        assertNotNull(landing.getApmCode());
        assertEquals(validValues.get("apmCode"), landing.getApmCode());

        assertNotNull(landing.getAppName());
        assertEquals(validValues.get("appName"), landing.getAppName());

        assertNotNull(landing.getVendor());
        assertEquals(validValues.get("vendor"), landing.getVendor());

        assertNotNull(landing.getSwType());
        assertEquals(validValues.get("swType"), landing.getSwType());

        assertNotNull(landing.getSwName());
        assertEquals(validValues.get("swName"), landing.getSwName());

        assertNotNull(landing.getSwId());
        assertEquals(Integer.parseInt(validValues.get("swId")), landing.getSwId());

        assertNotNull(landing.getSwVersion());
        assertEquals(validValues.get("swVersion"), landing.getSwVersion());

        assertNotNull(landing.getSwExpireIn());
        assertEquals(validValues.get("swExpireIn"), landing.getSwExpireIn());

        assertNotNull(landing.getGroupHeadName());
        assertEquals(validValues.get("groupHeadName"), landing.getGroupHeadName());

        assertNotNull(landing.getBusinessLines());
        assertEquals(validValues.get("businessLines"), landing.getBusinessLines());

        assertNotNull(landing.getDbrTier());
        assertEquals(validValues.get("dbrTier"), landing.getDbrTier());

        assertNotNull(landing.getSwValidPlan());
        assertTrue(landing.getSwValidPlan());

        assertNotNull(landing.getAppValidPlan());
        assertFalse(landing.getAppValidPlan());

        assertNotNull(landing.getSwPlanStatus());
        assertEquals(validValues.get("swPlanStatus"), landing.getSwPlanStatus());

        assertNotNull(landing.getPlanNo());
        assertEquals(Integer.parseInt(validValues.get("planNo")), landing.getPlanNo());

        assertNotNull(landing.getPlanName());
        assertEquals(validValues.get("planName"), landing.getPlanName());

        assertNotNull(landing.getPlanStartDate());
        assertEquals(LandingValidStub.EXPECTED_START_PLAN_DATE, landing.getPlanStartDate());

        assertNotNull(landing.getPlanEndDate());
        assertEquals(LandingValidStub.EXPECTED_END_PLAN_DATE, landing.getPlanEndDate());

        assertNotNull(landing.getPlanFunded());
        assertTrue(landing.getPlanFunded());

        assertNotNull(landing.getRefNumber());
        assertEquals(validValues.get("refNumber"), landing.getRefNumber());

        assertNotNull(landing.getPlanComments());
        assertEquals(validValues.get("planComments"), landing.getPlanComments());

        assertNotNull(landing.getPlanExternalCost());
        assertEquals(Integer.parseInt(validValues.get("planExternalCost")), landing.getPlanExternalCost());

        assertNotNull(landing.getPlanInternalCost());
        assertEquals(Integer.parseInt(validValues.get("planInternalCost")), landing.getPlanInternalCost());

        assertNotNull(landing.getPlanLicenseCost());
        assertEquals(Integer.parseInt(validValues.get("planLicenseCost")), landing.getPlanLicenseCost());

        assertNotNull(landing.getEosDate());
        assertEquals(LandingValidStub.EXPECTED_DATE, landing.getEosDate());

        assertNotNull(landing.getExtendedDate());
        assertEquals(LandingValidStub.EXPECTED_DATE, landing.getExtendedDate());

        assertNotNull(landing.getExtendedCustomDate());
        assertEquals(LandingValidStub.EXPECTED_DATE, landing.getExtendedCustomDate());

        assertNotNull(landing.getLocalRCMP());
        assertFalse(landing.getLocalRCMP());

        assertNotNull(landing.getCountryName());
        assertEquals(validValues.get("countryName"), landing.getCountryName());

        assertNotNull(landing.getInternetFacing());
        assertFalse(landing.getInternetFacing());

        assertNotNull(landing.getUsFlag());
        assertFalse(landing.getUsFlag());

        assertNotNull(landing.getLifecycle());
        assertEquals(validValues.get("lifecycle"), landing.getLifecycle());

        assertNotNull(landing.getEnvironments());
        assertEquals(validValues.get("environments"), landing.getEnvironments());
    }

    @Test
    public void Given_only_required_data_When_try_to_create_Then_create_a_valid_landing() {
        //Arrange
        Map<String, String> validValues = LandingValidStub.onlyRequiredFields();

        //Act
        Landing landing = new Landing(
                validValues.get("uniqueComponentId"),
                validValues.get("apmCode"),
                validValues.get("appName"),
                validValues.get("vendor"),
                validValues.get("swType"),
                validValues.get("swName"),
                validValues.get("swId"),
                validValues.get("swVersion"),
                validValues.get("swExpireIn"),
                validValues.get("groupHeadName"),
                validValues.get("businessLines"),
                validValues.get("dbrTier"),
                validValues.get("swValidPlan"),
                validValues.get("appValidPlan"),
                validValues.get("swPlanStatus"),
                validValues.get("planNo"),
                validValues.get("planName"),
                validValues.get("planStartDate"),
                validValues.get("planEndDate"),
                validValues.get("planFunded"),
                validValues.get("refNumber"),
                validValues.get("planComments"),
                validValues.get("planExternalCost"),
                validValues.get("planInternalCost"),
                validValues.get("planLicenseCost"),
                validValues.get("eosDate"),
                validValues.get("extendedDate"),
                validValues.get("extendedCustomDate"),
                validValues.get("localRCMP"),
                validValues.get("countryName"),
                validValues.get("internetFacing"),
                validValues.get("usFlag"),
                validValues.get("lifecycle"),
                validValues.get("environments")
        );

        //Assert
        assertNotNull(landing.getUniqueComponentId());
        assertEquals(validValues.get("uniqueComponentId"), landing.getUniqueComponentId());

        assertNotNull(landing.getApmCode());
        assertEquals(validValues.get("apmCode"), landing.getApmCode());

        assertNotNull(landing.getAppName());
        assertEquals(validValues.get("appName"), landing.getAppName());

        assertNotNull(landing.getVendor());
        assertEquals(validValues.get("vendor"), landing.getVendor());

        assertNotNull(landing.getSwType());
        assertEquals(validValues.get("swType"), landing.getSwType());

        assertNotNull(landing.getSwName());
        assertEquals(validValues.get("swName"), landing.getSwName());

        assertNotNull(landing.getSwId());
        assertEquals(Integer.parseInt(validValues.get("swId")), landing.getSwId());

        assertNotNull(landing.getSwVersion());
        assertEquals(validValues.get("swVersion"), landing.getSwVersion());

        assertNotNull(landing.getSwExpireIn());
        assertEquals(validValues.get("swExpireIn"), landing.getSwExpireIn());

        assertNotNull(landing.getGroupHeadName());
        assertEquals(validValues.get("groupHeadName"), landing.getGroupHeadName());

        assertNotNull(landing.getBusinessLines());
        assertEquals(validValues.get("businessLines"), landing.getBusinessLines());

        assertNull(landing.getDbrTier());

        assertNotNull(landing.getSwValidPlan());
        assertTrue(landing.getSwValidPlan());

        assertNotNull(landing.getAppValidPlan());
        assertFalse(landing.getAppValidPlan());

        assertNotNull(landing.getSwPlanStatus());
        assertEquals(validValues.get("swPlanStatus"), landing.getSwPlanStatus());

        assertNull(landing.getPlanNo());

        assertNull(landing.getPlanName());

        assertNull(landing.getPlanStartDate());

        assertNull(landing.getPlanEndDate());

        assertNull(landing.getPlanFunded());

        assertNull(landing.getRefNumber());

        assertNull(landing.getPlanComments());

        assertNull(landing.getPlanExternalCost());

        assertNull(landing.getPlanInternalCost());

        assertNull(landing.getPlanLicenseCost());

        assertNull(landing.getEosDate());

        assertNull(landing.getExtendedDate());

        assertNull(landing.getExtendedCustomDate());

        assertNotNull(landing.getLocalRCMP());
        assertFalse(landing.getLocalRCMP());

        assertNotNull(landing.getCountryName());
        assertEquals(validValues.get("countryName"), landing.getCountryName());

        assertNotNull(landing.getInternetFacing());
        assertFalse(landing.getInternetFacing());

        assertNotNull(landing.getUsFlag());
        assertFalse(landing.getUsFlag());

        assertNotNull(landing.getLifecycle());
        assertEquals(validValues.get("lifecycle"), landing.getLifecycle());

        assertNotNull(landing.getEnvironments());
        assertEquals(validValues.get("environments"), landing.getEnvironments());
    }

    @Test
    public void Given_invalid_values_for_optional_data_When_try_to_create_Then_create_a_valid_landing() {
        //Arrange
        Map<String, String> validValues = LandingValidStub.invalidCharsForOptional();

        //Act
        Landing landing = new Landing(
                validValues.get("uniqueComponentId"),
                validValues.get("apmCode"),
                validValues.get("appName"),
                validValues.get("vendor"),
                validValues.get("swType"),
                validValues.get("swName"),
                validValues.get("swId"),
                validValues.get("swVersion"),
                validValues.get("swExpireIn"),
                validValues.get("groupHeadName"),
                validValues.get("businessLines"),
                validValues.get("dbrTier"),
                validValues.get("swValidPlan"),
                validValues.get("appValidPlan"),
                validValues.get("swPlanStatus"),
                validValues.get("planNo"),
                validValues.get("planName"),
                validValues.get("planStartDate"),
                validValues.get("planEndDate"),
                validValues.get("planFunded"),
                validValues.get("refNumber"),
                validValues.get("planComments"),
                validValues.get("planExternalCost"),
                validValues.get("planInternalCost"),
                validValues.get("planLicenseCost"),
                validValues.get("eosDate"),
                validValues.get("extendedDate"),
                validValues.get("extendedCustomDate"),
                validValues.get("localRCMP"),
                validValues.get("countryName"),
                validValues.get("internetFacing"),
                validValues.get("usFlag"),
                validValues.get("lifecycle"),
                validValues.get("environments")
        );

        //Assert
        assertNotNull(landing.getUniqueComponentId());
        assertEquals(validValues.get("uniqueComponentId"), landing.getUniqueComponentId());

        assertNotNull(landing.getApmCode());
        assertEquals(validValues.get("apmCode"), landing.getApmCode());

        assertNotNull(landing.getAppName());
        assertEquals(validValues.get("appName"), landing.getAppName());

        assertNotNull(landing.getVendor());
        assertEquals(validValues.get("vendor"), landing.getVendor());

        assertNotNull(landing.getSwType());
        assertEquals(validValues.get("swType"), landing.getSwType());

        assertNotNull(landing.getSwName());
        assertEquals(validValues.get("swName"), landing.getSwName());

        assertNotNull(landing.getSwId());
        assertEquals(Integer.parseInt(validValues.get("swId")), landing.getSwId());

        assertNotNull(landing.getSwVersion());
        assertEquals(validValues.get("swVersion"), landing.getSwVersion());

        assertNotNull(landing.getSwExpireIn());
        assertEquals(validValues.get("swExpireIn"), landing.getSwExpireIn());

        assertNotNull(landing.getGroupHeadName());
        assertEquals(validValues.get("groupHeadName"), landing.getGroupHeadName());

        assertNotNull(landing.getBusinessLines());
        assertEquals(validValues.get("businessLines"), landing.getBusinessLines());

        assertNull(landing.getDbrTier());

        assertNotNull(landing.getSwValidPlan());
        assertTrue(landing.getSwValidPlan());

        assertNotNull(landing.getAppValidPlan());
        assertFalse(landing.getAppValidPlan());

        assertNotNull(landing.getSwPlanStatus());
        assertEquals(validValues.get("swPlanStatus"), landing.getSwPlanStatus());

        assertNull(landing.getPlanNo());

        assertNull(landing.getPlanName());

        assertNull(landing.getPlanStartDate());

        assertNull(landing.getPlanEndDate());

        assertNull(landing.getPlanFunded());

        assertNull(landing.getRefNumber());

        assertNull(landing.getPlanComments());

        assertNull(landing.getPlanExternalCost());

        assertNull(landing.getPlanInternalCost());

        assertNull(landing.getPlanLicenseCost());

        assertNull(landing.getEosDate());

        assertNull(landing.getExtendedDate());

        assertNull(landing.getExtendedCustomDate());

        assertNotNull(landing.getLocalRCMP());
        assertFalse(landing.getLocalRCMP());

        assertNotNull(landing.getCountryName());
        assertEquals(validValues.get("countryName"), landing.getCountryName());

        assertNotNull(landing.getInternetFacing());
        assertFalse(landing.getInternetFacing());

        assertNotNull(landing.getUsFlag());
        assertFalse(landing.getUsFlag());

        assertNotNull(landing.getLifecycle());
        assertEquals(validValues.get("lifecycle"), landing.getLifecycle());

        assertNotNull(landing.getEnvironments());
        assertEquals(validValues.get("environments"), landing.getEnvironments());
    }

    @Test
    public void Given_invalid_values_for_required_data_When_try_to_create_Then_throw_an_exception() {
        //Arrange
        Map<String, String> invalidValues = LandingInvalidStub.invalidCharsForRequiredFields();

        //Act and Assert
        assertThrows(InvalidPropertyException.class,
                () -> {  new Landing(
                    invalidValues.get("uniqueComponentId"),
                    invalidValues.get("apmCode"),
                    invalidValues.get("appName"),
                    invalidValues.get("vendor"),
                    invalidValues.get("swType"),
                    invalidValues.get("swName"),
                    invalidValues.get("swId"),
                    invalidValues.get("swVersion"),
                    invalidValues.get("swExpireIn"),
                    invalidValues.get("groupHeadName"),
                    invalidValues.get("businessLines"),
                    invalidValues.get("dbrTier"),
                    invalidValues.get("swValidPlan"),
                    invalidValues.get("appValidPlan"),
                    invalidValues.get("swPlanStatus"),
                    invalidValues.get("planNo"),
                    invalidValues.get("planName"),
                    invalidValues.get("planStartDate"),
                    invalidValues.get("planEndDate"),
                    invalidValues.get("planFunded"),
                    invalidValues.get("refNumber"),
                    invalidValues.get("planComments"),
                    invalidValues.get("planExternalCost"),
                    invalidValues.get("planInternalCost"),
                    invalidValues.get("planLicenseCost"),
                    invalidValues.get("eosDate"),
                    invalidValues.get("extendedDate"),
                    invalidValues.get("extendedCustomDate"),
                    invalidValues.get("localRCMP"),
                    invalidValues.get("countryName"),
                    invalidValues.get("internetFacing"),
                    invalidValues.get("usFlag"),
                    invalidValues.get("lifecycle"),
                    invalidValues.get("environments"));
        });
    }

    @Test
    public void Given_only_optional_data_When_try_to_create_Then_throw_an_exception() {
        //Arrange
        Map<String, String> invalidValues = LandingInvalidStub.onlyOptionalFields();

        //Act and Assert
        assertThrows(InvalidPropertyException.class,
                () -> {  new Landing(
                        invalidValues.get("uniqueComponentId"),
                        invalidValues.get("apmCode"),
                        invalidValues.get("appName"),
                        invalidValues.get("vendor"),
                        invalidValues.get("swType"),
                        invalidValues.get("swName"),
                        invalidValues.get("swId"),
                        invalidValues.get("swVersion"),
                        invalidValues.get("swExpireIn"),
                        invalidValues.get("groupHeadName"),
                        invalidValues.get("businessLines"),
                        invalidValues.get("dbrTier"),
                        invalidValues.get("swValidPlan"),
                        invalidValues.get("appValidPlan"),
                        invalidValues.get("swPlanStatus"),
                        invalidValues.get("planNo"),
                        invalidValues.get("planName"),
                        invalidValues.get("planStartDate"),
                        invalidValues.get("planEndDate"),
                        invalidValues.get("planFunded"),
                        invalidValues.get("refNumber"),
                        invalidValues.get("planComments"),
                        invalidValues.get("planExternalCost"),
                        invalidValues.get("planInternalCost"),
                        invalidValues.get("planLicenseCost"),
                        invalidValues.get("eosDate"),
                        invalidValues.get("extendedDate"),
                        invalidValues.get("extendedCustomDate"),
                        invalidValues.get("localRCMP"),
                        invalidValues.get("countryName"),
                        invalidValues.get("internetFacing"),
                        invalidValues.get("usFlag"),
                        invalidValues.get("lifecycle"),
                        invalidValues.get("environments"));
                });
    }

    @Test
    public void Given_null_values_for_required_data_When_try_to_create_Then_throw_an_exception() {
        //Arrange
        Map<String, String> invalidValues = LandingInvalidStub.onlyOptionalFields();

        //Act and Assert
        assertThrows(InvalidPropertyException.class,
                () -> {  new Landing(
                        invalidValues.get("uniqueComponentId"),
                        invalidValues.get("apmCode"),
                        invalidValues.get("appName"),
                        invalidValues.get("vendor"),
                        invalidValues.get("swType"),
                        invalidValues.get("swName"),
                        invalidValues.get("swId"),
                        invalidValues.get("swVersion"),
                        invalidValues.get("swExpireIn"),
                        invalidValues.get("groupHeadName"),
                        invalidValues.get("businessLines"),
                        invalidValues.get("dbrTier"),
                        invalidValues.get("swValidPlan"),
                        invalidValues.get("appValidPlan"),
                        invalidValues.get("swPlanStatus"),
                        invalidValues.get("planNo"),
                        invalidValues.get("planName"),
                        invalidValues.get("planStartDate"),
                        invalidValues.get("planEndDate"),
                        invalidValues.get("planFunded"),
                        invalidValues.get("refNumber"),
                        invalidValues.get("planComments"),
                        invalidValues.get("planExternalCost"),
                        invalidValues.get("planInternalCost"),
                        invalidValues.get("planLicenseCost"),
                        invalidValues.get("eosDate"),
                        invalidValues.get("extendedDate"),
                        invalidValues.get("extendedCustomDate"),
                        invalidValues.get("localRCMP"),
                        invalidValues.get("countryName"),
                        invalidValues.get("internetFacing"),
                        invalidValues.get("usFlag"),
                        invalidValues.get("lifecycle"),
                        invalidValues.get("environments"));
                });
    }

}
