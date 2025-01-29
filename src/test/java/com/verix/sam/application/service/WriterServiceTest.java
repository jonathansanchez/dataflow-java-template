package com.verix.sam.application.service;

import com.verix.sam.domain.model.Sam;
import com.verix.sam.domain.model.SamStub;
import com.verix.sam.domain.model.WriterRepository;
import com.verix.sam.domain.model.exception.SamWriterException;
import org.apache.beam.sdk.transforms.DoFn.OutputReceiver;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class WriterServiceTest {
    @InjectMocks
    private WriterService service;

    @Mock
    private WriterRepository repository;

    @Test
    void Given_a_sam_When_try_to_save_Then_save_into_repository() {
        //Arrange
        Sam sam                 = SamStub.create();
        OutputReceiver<Sam> out = mock(OutputReceiver.class);

        doNothing().when(repository).save(eq(sam));
        doNothing().when(out).output(eq(sam));

        //Act
        service.execute(sam, out);

        //Assert
        verify(repository, times(1)).save(sam);
        verify(out, times(1)).output(sam);
    }

    @Test
    void Given_a_sam_When_try_to_save_and_repository_fail_Then_throw_an_exception() {
        //Arrange
        Sam sam                 = SamStub.create();
        OutputReceiver<Sam> out = mock(OutputReceiver.class);

        doThrow(new RuntimeException()).when(repository).save(eq(sam));

        //Act and Assert
        assertThrows(SamWriterException.class, () -> service.execute(sam, out));

        verify(repository, times(1)).save(eq(sam));
        verify(out, times(0)).output(eq(sam));
    }

}