package com.verix.forecast.domain.model;

import com.verix.forecast.domain.model.exception.InvalidActionTypeException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ActionTest {
    @Test
    void Given_a_valid_string_update_action_When_try_to_create_Then_return_a_formated_update_action() {
        //Arrange
        String value = "update";
        String expectedAction = "UPDATE";

        //Act
        Action action = Action.create(value);

        //Assert
        assertNotNull(action.getValue());
        assertNotEquals("", action.getValue());
        assertEquals(expectedAction, action.getValue());
        assertEquals(expectedAction, ActionType.UPDATE.name());
    }

    @Test
    void Given_a_valid_string_remove_action_When_try_to_create_Then_return_a_formated_remove_action() {
        //Arrange
        String value = "remove";
        String expectedAction = "REMOVE";

        //Act
        Action action = Action.create(value);

        //Assert
        assertNotNull(action.getValue());
        assertNotEquals("", action.getValue());
        assertEquals(expectedAction, action.getValue());
        assertEquals(expectedAction, ActionType.REMOVE.name());
    }

    @Test
    void Given_a_valid_string_add_action_When_try_to_create_Then_return_a_formated_add_action() {
        //Arrange
        String value = "add";
        String expectedAction = "ADD";

        //Act
        Action action = Action.create(value);

        //Assert
        assertNotNull(action.getValue());
        assertNotEquals("", action.getValue());
        assertEquals(expectedAction, action.getValue());
        assertEquals(expectedAction, ActionType.ADD.name());
    }

    @Test
    void Given_a_null_When_try_to_create_Then_thrown_an_exception() {
        //Arrange
        String value = null;

        //Act and Assert
        assertThrows(InvalidActionTypeException.class, () -> Action.create(value));
    }

    @Test
    void Given_an_empty_string_When_try_to_create_Then_thrown_an_exception() {
        //Arrange
        String value = "";

        //Act and Assert
        assertThrows(InvalidActionTypeException.class, () -> Action.create(value));
    }

    @Test
    void Given_a_whitespace_string_When_try_to_create_Then_thrown_an_exception() {
        //Arrange
        String value = " ";

        //Act and Assert
        assertThrows(InvalidActionTypeException.class, () -> Action.create(value));
    }

    @Test
    void Given_an_invalid_action_string_When_try_to_create_Then_thrown_an_exception() {
        //Arrange
        String value = "invalid";

        //Act and Assert
        assertThrows(IllegalArgumentException.class, () -> Action.create(value));
    }
}