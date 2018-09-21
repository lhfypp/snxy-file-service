package com.snxy.file.serviceImpl;

import com.github.tobato.fastdfs.domain.StorePath;
import com.github.tobato.fastdfs.proto.storage.DownloadByteArray;
import com.github.tobato.fastdfs.service.FastFileStorageClient;
import com.snxy.common.exception.BizException;
import com.snxy.common.response.ResultData;
import com.snxy.file.service.FileService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by 24398 on 2018/9/4.
 */
@Service
@Slf4j
public class FileServiceImpl implements FileService {

    @Resource
    private FastFileStorageClient fileStorageClient;

    private static final String FILE_PATH_SEPARATOR = "/";
    private static final String SEPARATOR = ".";

    @Override
    public String upload(MultipartFile file)  {

       String filePath = this.uploadSingle(file);
       log.info("上传图片成功");
        return filePath;
    }


    String uploadSingle(MultipartFile file)  {
        if(file == null){
            throw new BizException("上传文件不能为空");
        }
        String simpleName = file.getOriginalFilename();
        Integer separatorIndex = simpleName.lastIndexOf(SEPARATOR);
        String fileType = simpleName.substring(separatorIndex + 1 );
        InputStream is = null;
        try {
            is = file.getInputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }
        StorePath  storePath = this.fileStorageClient.uploadFile(is,file.getSize(),fileType,null);

        return storePath.getFullPath();
    }


    @Override
    public byte[] downLoad(String filePath) {
        Integer separatorIndex = filePath.indexOf(FILE_PATH_SEPARATOR);
        String group = filePath.substring(0,separatorIndex);
        String path = filePath.substring(separatorIndex + 1);
        log.info("下载图片  group :  [{}] ,path : [{}]",group,path);

        byte [] fileBytes =  this.fileStorageClient.downloadFile(group,path,new DownloadByteArray());
        log.info("下载成功");
        return fileBytes;
    }


    @Override
    public void delete(String filePath) {
        log.info("删除路径 filePath : [{}]",filePath);
        this.fileStorageClient.deleteFile(filePath);
        log.info("删除成功 ");
    }

    @Override
    public void batchDelete(List<String> filePaths) {
        log.info("批量删除路径 ：[{}]",filePaths.parallelStream().collect(Collectors.joining(",")));
        filePaths.forEach(filePath ->
            this.fileStorageClient.deleteFile(filePath)
        );
        log.info("批量删除成功");
    }


}
