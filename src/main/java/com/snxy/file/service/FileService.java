package com.snxy.file.service;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * Created by 24398 on 2018/9/4.
 */
public interface FileService {
    String upload(MultipartFile file) ;

    byte [] downLoad(String filePath);

    void delete(String filePath);

    void batchDelete(List<String> filePaths);

}
