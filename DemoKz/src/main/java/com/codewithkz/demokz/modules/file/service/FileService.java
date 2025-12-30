package com.codewithkz.demokz.modules.file.service;

import com.codewithkz.demokz.common.exception.FileStorageException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Stream;

@Service
@Slf4j
public class FileService implements IFileService {

    private final String path = "uploads";

    public String UploadFile(MultipartFile file) {
        try {
            Path uploadDir = Path.of(path);


            if (!Files.exists(uploadDir)) {
                Files.createDirectories(uploadDir);
            }

            Path filePath = uploadDir.resolve(file.getOriginalFilename());

            file.transferTo(filePath);

            log.info("Uploaded file: {}", filePath);

            return filePath.toString();
        } catch (IOException e) {
            log.error("Error while uploading file", e);
            throw new FileStorageException("Failed to upload file");
        }
    }



    public List<String> GetFiles() {
        Path uploadDir = Path.of(path);

        if (!Files.exists(uploadDir)) {
            return List.of();
        }

        try (Stream<Path> paths = Files.list(uploadDir)) {
            return paths
                    .filter(Files::isRegularFile)
                    .map(p -> p.getFileName().toString())
                    .toList();
        } catch (IOException e) {
            log.error("Error while read files", e);
            throw new FileStorageException("Failed to read uploaded files");
        }

    }
}
