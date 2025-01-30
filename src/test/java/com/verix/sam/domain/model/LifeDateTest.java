package com.verix.sam.domain.model;

import org.junit.jupiter.api.Test;
import java.time.format.DateTimeParseException;

import static org.junit.jupiter.api.Assertions.*;

class LifeDateTest {
    @Test
    void Given_a_valid_string_date_When_try_to_create_Then_return_a_formated_date() {
        //Arrange
        String value = "12/17/2014 12:00:00 AM";
        String expectedDate = "2014-12-17";

        //Act
        LifeDate date = LifeDate.create(value);

        //Assert
        assertNotNull(date.getValue());
        assertNotEquals("", date.getValue());
        assertEquals(expectedDate, date.getValue());
    }

    @Test
    void Given_a_null_When_try_to_create_Then_return_empty_string() {
        //Arrange
        String value = null;

        //Act
        LifeDate date = LifeDate.create(value);

        //Assert
        assertNotNull(date.getValue());
        assertEquals("", date.getValue());
    }

    @Test
    void Given_an_empty_string_When_try_to_create_Then_return_empty_string() {
        //Arrange
        String value = "";

        //Act
        LifeDate date = LifeDate.create(value);

        //Assert
        assertNotNull(date.getValue());
        assertEquals("", date.getValue());
    }

    @Test
    void Given_an_whitespace_string_When_try_to_create_Then_return_empty_string() {
        //Arrange
        String value = " ";

        //Act
        LifeDate date = LifeDate.create(value);

        //Assert
        assertNotNull(date.getValue());
        assertEquals("", date.getValue());
    }

    @Test
    void Given_a_invalid_string_date_When_try_to_create_Then_thrown_an_exception() {
        //Arrange
        String value = "invalid";

        //Act and Assert
        assertThrows(DateTimeParseException.class, () -> LifeDate.create(value));
    }
}