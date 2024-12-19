package io.souz.fileingestionapi.controller;

import io.souz.fileingestionapi.exception.BadRequestException;
import io.souz.fileingestionapi.service.FileIngestionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class FileIngestionController implements BaseController {

    private final FileIngestionService fileIngestionService;

    public FileIngestionController(FileIngestionService fileIngestionService) {
        this.fileIngestionService = fileIngestionService;
    }

    @PostMapping("/upload")
    public ResponseEntity<String> upload(@RequestParam("file") MultipartFile file) {
        if (file == null || file.isEmpty() || !"text/plain".equals(file.getContentType())) {
            throw new BadRequestException("invalid.file");
        }

        this.fileIngestionService.processFile(file);

        return ResponseEntity.noContent().build();
    }

}
