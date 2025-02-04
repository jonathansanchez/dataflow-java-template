package com.verix.forecast.domain.model;

import com.verix.forecast.domain.model.exception.InvalidDateException;
import org.junit.jupiter.api.Test;
import java.time.format.DateTimeParseException;

import static org.junit.jupiter.api.Assertions.*;

class DeliveryDateTest {
    @Test
    void Given_a_valid_string_date_When_try_to_create_Then_return_a_formated_date() {
        //Arrange
        String value = "2014-12-17";
        String expectedDate = "2014-12-17";

        //Act
        DeliveryDate date = DeliveryDate.create(value);

        //Assert
        assertNotNull(date.getValue());
        assertNotEquals("", date.getValue());
        assertEquals(expectedDate, date.getValue());
    }

    @Test
    void Given_a_null_When_try_to_create_Then_thrown_an_exception() {
        //Arrange
        String value = null;

        //Act and Assert
        assertThrows(InvalidDateException.class, () -> DeliveryDate.create(value));
    }

    @Test
    void Given_an_empty_string_When_try_to_create_Then_thrown_an_exception() {
        //Arrange
        String value = "";

        //Act and Assert
        assertThrows(InvalidDateException.class, () -> DeliveryDate.create(value));
    }

    @Test
    void Given_an_whitespace_string_When_try_to_create_Then_thrown_an_exception() {
        //Arrange
        String value = " ";

        //Act and Assert
        assertThrows(InvalidDateException.class, () -> DeliveryDate.create(value));
    }

    @Test
    void Given_a_invalid_string_date_When_try_to_create_Then_thrown_an_exception() {
        //Arrange
        String value = "invalid";

        //Act and Assert
        assertThrows(InvalidDateException.class, () -> DeliveryDate.create(value));
    }
}