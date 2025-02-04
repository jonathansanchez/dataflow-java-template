package com.verix.forecast.domain.model;

import com.verix.forecast.domain.model.exception.InvalidActionTypeException;
import com.verix.forecast.domain.model.exception.InvalidDateException;
import com.verix.forecast.domain.model.exception.InvalidPropertyException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RemediationTest {

    private static final String EMPTY_STRING = "";
    private static final String INVALID_CHAR = "!¡'¿+ç´%()*";

    @Test
    void Given_a_valid_data_When_try_to_create_Then_create_a_valid_remediation() {
        //Arrange
        String strategy = "SIPFY25";
        String apmCode = "BDQ4";
        String component = "SQL Server Integration Services";
        String version = "SQL Server Integration Services 2014 Standard 12.0.2456.0";
        String action = "update";
        String newVersion = "SQL Server Integration Services 2022 Standard";
        String deliveryDate = "2025-10-31";

        //Act
        Remediation remediation = new Remediation(strategy,
                apmCode,
                component,
                version,
                Action.create(action),
                newVersion,
                DeliveryDate.create(deliveryDate));

        //Assert
        assertNotNull(remediation.getStrategy());
        assertNotEquals(EMPTY_STRING, remediation.getStrategy());
        assertEquals(strategy, remediation.getStrategy());

        assertNotNull(remediation.getApmCode());
        assertNotEquals(EMPTY_STRING, remediation.getApmCode());
        assertEquals(apmCode, remediation.getApmCode());

        assertNotNull(remediation.getComponent());
        assertNotEquals(EMPTY_STRING, remediation.getComponent());
        assertEquals(component, remediation.getComponent());

        assertNotNull(remediation.getVersion());
        assertNotEquals(EMPTY_STRING, remediation.getVersion());
        assertEquals(version, remediation.getVersion());

        assertNotNull(remediation.getAction().getValue());
        assertNotEquals(EMPTY_STRING, remediation.getAction().getValue());
        assertEquals(action.toUpperCase(), remediation.getAction().getValue());

        assertNotNull(remediation.getNewVersion());
        assertNotEquals(EMPTY_STRING, remediation.getNewVersion());
        assertEquals(newVersion, remediation.getNewVersion());

        assertNotNull(remediation.getDeliveryDate().getValue());
        assertNotEquals(EMPTY_STRING, remediation.getDeliveryDate().getValue());
        assertEquals(deliveryDate, remediation.getDeliveryDate().getValue());
    }

    @Test
    void Given_data_with_invalid_action_value_When_try_to_create_Then_throw_an_exception() {
        //Arrange
        String strategy = "SIPFY25";
        String apmCode = "BDQ4";
        String component = "SQL Server Integration Services";
        String version = "SQL Server Integration Services 2014 Standard 12.0.2456.0";
        String action = INVALID_CHAR;
        String newVersion = "SQL Server Integration Services 2022 Standard";
        String deliveryDate = "2025-10-31";

        //Act and Assert
        assertThrows(InvalidActionTypeException.class, () -> new Remediation(strategy,
                apmCode,
                component,
                version,
                Action.create(action),
                newVersion,
                DeliveryDate.create(deliveryDate))
        );
    }

    @Test
    void Given_data_with_invalid_date_value_When_try_to_create_Then_throw_an_exception() {
        //Arrange
        String strategy = "SIPFY25";
        String apmCode = "BDQ4";
        String component = "SQL Server Integration Services";
        String version = "SQL Server Integration Services 2014 Standard 12.0.2456.0";
        String action = "update";
        String newVersion = "SQL Server Integration Services 2022 Standard";
        String deliveryDate = INVALID_CHAR;

        //Act and Assert
        assertThrows(InvalidDateException.class, () -> new Remediation(strategy,
                apmCode,
                component,
                version,
                Action.create(action),
                newVersion,
                DeliveryDate.create(deliveryDate))
        );
    }

    @Test
    void Given_data_with_invalid_values_When_try_to_create_Then_throw_an_exception() {
        //Arrange
        String strategy = INVALID_CHAR;
        String apmCode = INVALID_CHAR;
        String component = INVALID_CHAR;
        String version = INVALID_CHAR;
        String action = "update";
        String newVersion = INVALID_CHAR;
        String deliveryDate = "2025-10-31";

        //Act and Assert
        assertThrows(InvalidPropertyException.class, () -> new Remediation(strategy,
                apmCode,
                component,
                version,
                Action.create(action),
                newVersion,
                DeliveryDate.create(deliveryDate))
        );
    }

    @Test
    void Given_data_with_required_values_When_try_to_create_Then_throw_an_exception() {
        //Arrange
        String strategy = EMPTY_STRING;
        String apmCode = EMPTY_STRING;
        String component = EMPTY_STRING;
        String version = EMPTY_STRING;
        String action = "update";
        String newVersion = EMPTY_STRING;
        String deliveryDate = "2025-10-31";

        //Act and Assert
        assertThrows(InvalidPropertyException.class, () -> new Remediation(strategy,
                apmCode,
                component,
                version,
                Action.create(action),
                newVersion,
                DeliveryDate.create(deliveryDate))
        );
    }

    @Test
    void Given_data_with_required_and_null_values_When_try_to_parse_null_Then_throw_an_exception() {
        //Arrange
        String strategy = null;
        String apmCode = null;
        String component = null;
        String version = null;
        String action = "update";
        String newVersion = null;
        String deliveryDate = "2025-10-31";

        //Act and Assert
        assertThrows(InvalidPropertyException.class, () -> new Remediation(strategy,
                apmCode,
                component,
                version,
                Action.create(action),
                newVersion,
                DeliveryDate.create(deliveryDate))
        );
    }
}