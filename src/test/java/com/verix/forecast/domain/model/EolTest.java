package com.verix.forecast.domain.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EolTest {
    @Test
    void Given_a_valid_string_date_When_try_to_create_Then_return_a_formated_date() {
        //Arrange
        String value = "2014-12-17";
        String expectedDate = "2014-12-17";

        //Act
        Eol date = Eol.create(value);

        //Assert
        assertNotNull(date.getValue());
        assertNotEquals("", date.getValue());
        assertEquals(expectedDate, date.getValue());
    }

    @Test
    void Given_a_null_When_try_to_create_Then_return_a_null_value() {
        //Arrange
        String value = null;

        //Act
        Eol date = Eol.create(value);

        //Assert
        assertNull(date.getValue());
        assertNotEquals("", date.getValue());
    }

    @Test
    void Given_an_empty_string_When_try_to_create_Then_return_a_null_value() {
        //Arrange
        String value = "";

        //Act
        Eol date = Eol.create(value);

        //Assert
        assertNull(date.getValue());
        assertNotEquals("", date.getValue());
    }

    @Test
    void Given_an_whitespace_string_When_try_to_create_Then_return_a_null_value() {
        //Arrange
        String value = " ";

        //Act
        Eol date = Eol.create(value);

        //Assert
        assertNull(date.getValue());
        assertNotEquals("", date.getValue());
    }

    @Test
    void Given_a_invalid_string_date_When_try_to_create_Then_return_a_null_value() {
        //Arrange
        String value = "invalid";

        //Act
        Eol date = Eol.create(value);

        //Assert
        assertNull(date.getValue());
        assertNotEquals("", date.getValue());
    }
}