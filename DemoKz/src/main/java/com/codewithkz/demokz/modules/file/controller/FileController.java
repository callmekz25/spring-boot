package com.codewithkz.demokz.modules.file.controller;

import com.codewithkz.demokz.common.response.ApiResponse;
import com.codewithkz.demokz.modules.file.service.FileService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/files")
public class FileController {

    private final FileService fileService;

    public FileController(FileService fileService) {
        this.fileService = fileService;
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<String>>> GetFiles() {
        var result = fileService.GetFiles();

        return ResponseEntity.ok(ApiResponse.success(result));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<String>> UploadFile(@RequestParam("file") MultipartFile file) {

        var result = fileService.UploadFile(file);

        return ResponseEntity.ok(ApiResponse.success(result));
    }

}
