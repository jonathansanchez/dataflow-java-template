package com.verix.landing.application.service;

import com.verix.landing.domain.model.DataPipeline;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class StreamingServiceTest {

    @Mock
    private DataPipeline dataPipeline;

    @InjectMocks
    private StreamingService streamingService;

    @Test
    void Given_a_pipeline_service_When_try_to_run_Then_call_pipeline_run(){
        //Arrange
        doNothing().when(dataPipeline).run();

        //Act
        streamingService.execute();

        //Assert
        verify(dataPipeline, times(1)).run();
    }

    @Test
    void Given_a_pipeline_service_When_try_to_run_and_fail_Then_throw_an_exception(){
        //Arrange
        assertThrows(RuntimeException.class, () -> doThrow().when(dataPipeline).run());

        //Act
        streamingService.execute();

        //Assert
        verify(dataPipeline, times(1)).run();
    }


}
