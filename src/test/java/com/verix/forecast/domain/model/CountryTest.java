package com.verix.forecast.domain.model;

import com.verix.forecast.domain.model.exception.InvalidCountryException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CountryTest {
    @Test
    void Given_a_valid_string_cca_When_try_to_create_Then_return_a_valid_cca() {
        //Arrange
        String value = "cca";
        String expectedCountry = "CCA";

        //Act
        Country country = Country.create(value);

        //Assert
        assertNotNull(country.getValue());
        assertNotEquals("", country.getValue());
        assertEquals(expectedCountry, country.getValue());
        assertEquals(expectedCountry, CountryType.CCA.name());
    }

    @Test
    void Given_a_valid_string_cl_When_try_to_create_Then_return_a_valid_cl() {
        //Arrange
        String value = "cl";
        String expectedCountry = "CL";

        //Act
        Country country = Country.create(value);

        //Assert
        assertNotNull(country.getValue());
        assertNotEquals("", country.getValue());
        assertEquals(expectedCountry, country.getValue());
        assertEquals(expectedCountry, CountryType.CL.name());
    }

    @Test
    void Given_a_valid_string_co_When_try_to_create_Then_return_a_valid_co() {
        //Arrange
        String value = "co";
        String expectedCountry = "CO";

        //Act
        Country country = Country.create(value);

        //Assert
        assertNotNull(country.getValue());
        assertNotEquals("", country.getValue());
        assertEquals(expectedCountry, country.getValue());
        assertEquals(expectedCountry, CountryType.CO.name());
    }

    @Test
    void Given_a_valid_string_ib_When_try_to_create_Then_return_a_valid_ib() {
        //Arrange
        String value = "ib";
        String expectedCountry = "IB";

        //Act
        Country country = Country.create(value);

        //Assert
        assertNotNull(country.getValue());
        assertNotEquals("", country.getValue());
        assertEquals(expectedCountry, country.getValue());
        assertEquals(expectedCountry, CountryType.IB.name());
    }

    @Test
    void Given_a_valid_string_mx_When_try_to_create_Then_return_a_valid_mx() {
        //Arrange
        String value = "mx";
        String expectedCountry = "MX";

        //Act
        Country country = Country.create(value);

        //Assert
        assertNotNull(country.getValue());
        assertNotEquals("", country.getValue());
        assertEquals(expectedCountry, country.getValue());
        assertEquals(expectedCountry, CountryType.MX.name());
    }

    @Test
    void Given_a_valid_string_pe_When_try_to_create_Then_return_a_valid_pe() {
        //Arrange
        String value = "pe";
        String expectedCountry = "PE";

        //Act
        Country country = Country.create(value);

        //Assert
        assertNotNull(country.getValue());
        assertNotEquals("", country.getValue());
        assertEquals(expectedCountry, country.getValue());
        assertEquals(expectedCountry, CountryType.PE.name());
    }

    @Test
    void Given_a_valid_string_uy_When_try_to_create_Then_return_a_valid_uy() {
        //Arrange
        String value = "uy";
        String expectedCountry = "UY";

        //Act
        Country country = Country.create(value);

        //Assert
        assertNotNull(country.getValue());
        assertNotEquals("", country.getValue());
        assertEquals(expectedCountry, country.getValue());
        assertEquals(expectedCountry, CountryType.UY.name());
    }

    @Test
    void Given_a_null_When_try_to_create_Then_thrown_an_exception() {
        //Arrange
        String value = null;

        //Act and Assert
        assertThrows(InvalidCountryException.class, () -> Country.create(value));
    }

    @Test
    void Given_an_empty_string_When_try_to_create_Then_thrown_an_exception() {
        //Arrange
        String value = "";

        //Act and Assert
        assertThrows(InvalidCountryException.class, () -> Country.create(value));
    }

    @Test
    void Given_a_whitespace_string_When_try_to_create_Then_thrown_an_exception() {
        //Arrange
        String value = " ";

        //Act and Assert
        assertThrows(InvalidCountryException.class, () -> Country.create(value));
    }

    @Test
    void Given_an_invalid_country_string_When_try_to_create_Then_thrown_an_exception() {
        //Arrange
        String value = "invalid";

        //Act and Assert
        assertThrows(IllegalArgumentException.class, () -> Country.create(value));
    }
}