package io.souz.fileingestionapi.controller;

import io.souz.fileingestionapi.exception.BadRequestException;
import io.souz.fileingestionapi.service.FileIngestionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.multipart.MultipartFile;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
class FileIngestionControllerTest {

    @Mock
    private FileIngestionService fileIngestionService;

    private FileIngestionController fileIngestionController;

    @BeforeEach
    void setUp() {
        this.fileIngestionController = new FileIngestionController(this.fileIngestionService);
    }

    @Test
    void uploadWithValidFile() {
        MultipartFile file = mock(MultipartFile.class);
        when(file.getContentType()).thenReturn("text/plain");
        doNothing().when(this.fileIngestionService).processFile(file);

        ResponseEntity<String> response = this.fileIngestionController.upload(file);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

    @Test
    void uploadWithInvalidFileType() {
        MultipartFile file = mock(MultipartFile.class);
        when(file.getContentType()).thenReturn("application/pdf");

        BadRequestException exception = assertThrows(BadRequestException.class, () -> {
            this.fileIngestionController.upload(file);
        });

        assertEquals("Invalid file. Only files with text/plain content type are allowed.", exception.getMessage());
    }

    @Test
    void uploadWithEmptyFile() {
        MultipartFile file = mock(MultipartFile.class);
        when(file.isEmpty()).thenReturn(true);

        BadRequestException exception = assertThrows(BadRequestException.class, () -> {
            this.fileIngestionController.upload(file);
        });

        assertEquals("Invalid file. Only files with text/plain content type are allowed.", exception.getMessage());
    }

    @Test
    void uploadWithNullFile() {
        BadRequestException exception = assertThrows(BadRequestException.class, () -> {
            this.fileIngestionController.upload(null);
        });

        assertEquals("Invalid file. Only files with text/plain content type are allowed.", exception.getMessage());
    }

}