package com.verix.sam.application.service;

import com.verix.sam.domain.model.DataPipeline;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class StreamingServiceTest {
    @InjectMocks
    private StreamingService service;

    @Mock
    private DataPipeline pipeline;

    @Test
    void Given_a_pipeline_service_When_try_to_run_Then_call_to_concrete_pipeline() {
        //Arrange
        doNothing().when(pipeline).run();

        //Act
        service.execute();

        //Assert
        verify(pipeline, times(1)).run();
    }

    @Test
    void Given_a_pipeline_service_When_try_to_run_and__fail_Then_throw_an_exception() {
        //Arrange
        assertThrows(RuntimeException.class, () -> doThrow().when(pipeline).run());

        //Act
        service.execute();

        //Assert
        verify(pipeline, times(1)).run();
    }
}