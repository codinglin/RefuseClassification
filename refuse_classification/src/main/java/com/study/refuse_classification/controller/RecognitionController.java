package com.study.refuse_classification.controller;

import com.alibaba.fastjson.JSONObject;
import com.study.refuse_classification.entity.vo.RecordVO;
import com.study.refuse_classification.utils.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.List;

@RestController
@RequestMapping("/classification/recognition")
@CrossOrigin
public class RecognitionController {
    @GetMapping("getFileName")
    public ResponseEntity getFileName(@RequestParam("fileName") String fileName){
        StringBuilder s= (StringBuilder) remoteCall(fileName);
        System.out.println(s);
        return ResponseEntity.success().data("item",s);
    }

    private static Object remoteCall(String content){
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("content", content);
        String str = jsonObject.toJSONString();
        // 访问服务进程的套接字
        Socket socket = null;
//        log.info("调用远程接口:host=>"+HOST+",port=>"+PORT);
        try {
            // 初始化套接字，设置访问服务的主机和进程端口号，HOST是访问python进程的主机名称，可以是IP地址或者域名，PORT是python进程绑定的端口号
            socket = new Socket("192.168.43.13", 12345);
            // 获取输出流对象
            OutputStream os = socket.getOutputStream();
            PrintStream out = new PrintStream(os);
            // 发送内容
            out.print(str);
            // 告诉服务进程，内容发送完毕，可以开始处理
            out.print("over");
            // 获取服务进程的输入流
            InputStream is = socket.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            String tmp = null;
            StringBuilder sb = new StringBuilder();
            // 读取内容
            while((tmp=br.readLine())!=null)
                sb.append(tmp).append('\n');
            // 解析结果
//            System.out.println(sb);
//            JSONArray res = JSON.parseArray(sb.toString());

            return sb;
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {if(socket!=null) socket.close();} catch (IOException e) {}
//            log.info("远程接口调用结束.");
        }
        return null;
    }
}
