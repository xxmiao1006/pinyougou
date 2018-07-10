package com.pinyougou.manager.controller;

import entity.Result;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import utils.FastDFSClient;

/**
 * 文件上传控制器
 */
@RestController
public class UploadController {

    @Value("${FILE_SERVER_URL}")
    private String file_server_url;//文件服务器地址


    @RequestMapping("/upload")
    public Result upload(MultipartFile file) {

        //获取文件全名
        String originalFilename = file.getOriginalFilename();
        //截取最后一个.之后的字符串，即拓展名
        String extName = originalFilename.substring( originalFilename.lastIndexOf(".")+1 );

        try {
            //利用自定义的工具类快速创建一个 FastDFS 的客户端
            FastDFSClient client = new FastDFSClient("classpath:config/fdfs_client.conf");
            //执行上传处理
            String fileId = client.uploadFile(file.getBytes(),extName);
            //拼接ip地址和路径获得图片的完整路径
            String url = file_server_url + fileId;
            return new Result(true,url);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,"上传失败!");
        }
    }
}
