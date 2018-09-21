package com.snxy.file.controller;

import com.snxy.common.response.ResultData;
import com.snxy.common.util.CheckUtil;
import com.snxy.common.util.StringUtil;
import com.snxy.file.service.FileService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Created by 24398 on 2018/9/4.
 */

@Controller
public class FileController {

    @Resource
    private FileService fileService;

    /**
     * 上传单个图片
     * @param file
     * @return
     * @throws Exception
     */
    @RequestMapping("/file/upload")
    @ResponseBody
    public ResultData<String> upload(@RequestParam("file")MultipartFile file) {
        String filePath =  this.fileService.upload(file);
        return ResultData.success(filePath);
    }


    /***
     * 删除图片
     * @param filePath
     * @return
     */
    @RequestMapping("/file/delete")
    @ResponseBody
    public ResultData delFile(String filePath){
        CheckUtil.isTrue(StringUtil.isNotBlank(filePath),"filePath 不能为空");
        this.fileService.delete(filePath);
        return ResultData.success("");
    }


    /****
     * 批量删除图片
     * @param filePaths
     * @return
     */
    @RequestMapping("/file/delete/batch")
    @ResponseBody
    public ResultData batchDel(@RequestBody List<String> filePaths){
        if(filePaths == null || filePaths.isEmpty()){
            return ResultData.success("");
        }
        this.fileService.batchDelete(filePaths);
        return ResultData.success("");
    }

    /***
     * 下载图片
     * @param filePath
     */
    @RequestMapping("/file/download")
    @ResponseBody
    public ResultData<byte[]> downLoad(String filePath) {
        byte[] fileBytes = this.fileService.downLoad(filePath);

      return ResultData.success(fileBytes);
    }


}
