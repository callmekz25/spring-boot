package com.codewithkz.demokz.modules.file.service;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface IFileService {
    String UploadFile(MultipartFile file);
    List<String> GetFiles();
}
