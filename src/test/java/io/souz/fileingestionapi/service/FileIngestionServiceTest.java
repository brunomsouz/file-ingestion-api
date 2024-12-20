package io.souz.fileingestionapi.service;

import io.souz.fileingestionapi.domain.Product;
import io.souz.fileingestionapi.exception.BadRequestException;
import io.souz.fileingestionapi.repository.ProductRepository;
import io.souz.fileingestionapi.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.multipart.MultipartFile;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyCollection;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
class FileIngestionServiceTest {

    @Mock
    private UserRepository userRepository;
    @Mock
    private ProductRepository productRepository;

    private FileIngestionService fileIngestionService;

    @BeforeEach
    void setUp() {
        this.fileIngestionService = new FileIngestionService(this.userRepository,
                this.productRepository);
    }

    @Test
    void processFileWithValidData() {
        MultipartFile file = new MockMultipartFile("file", "data.txt", "text/plain", "0000000001                              Sammie Baumbach00000000060000000003      479.0420210519".getBytes());
        fileIngestionService.processFile(file);

        verify(userRepository, times(1)).saveAll(anyCollection());
        verify(productRepository, times(1)).findProductById(3L);
        verify(productRepository, times(1)).save(any(Product.class));
    }

    @Test
    void processFileWithInvalidData() {
        MultipartFile file = new MockMultipartFile("file", "data.txt", "text/plain", "invalid data".getBytes());
        assertThrows(BadRequestException.class, () -> fileIngestionService.processFile(file));

        verify(userRepository, never()).saveAll(anyCollection());
        verify(productRepository, never()).findProductById(anyLong());
        verify(productRepository, never()).save(any(Product.class));
    }

    @Test
    void processFileWithMultipleRecords() {
        String data = "10000000001                              Sammie Baumbach00000000060000000003      479.0420210519\n" +
                "0000000065                                  Burma Crona00000006960000000005      385.0120210801";
        MultipartFile file = new MockMultipartFile("file", "data.txt", "text/plain", data.getBytes());
        fileIngestionService.processFile(file);

        verify(userRepository, times(1)).saveAll(anyCollection());
        verify(productRepository, times(2)).findProductById(anyLong());
        verify(productRepository, times(2)).save(any(Product.class));
    }

}