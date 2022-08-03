package com.study.refuse_classification;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;

public class PythonTest {
    public static void main(String[] args) {
//        String[] arguments = new String[] {"python","D:\\PycharmProjects\\refuseClassification\\predict1.py","D:/PycharmProjects/refuseClassification/img/book.jpg"};
//        try {
//            Process process = Runtime.getRuntime().exec(arguments);//这个方法相当于在cmd中输入 python D:\\ccc\\1.py D:/ccc/
//            BufferedReader in = new BufferedReader(new InputStreamReader(process.getInputStream()));
//            String line = null;
//            while ((line = in.readLine()) != null) {
//                System.out.println(line);  //在java编译器中打印.py文件的执行结果
//            }
//            in.close();
//            int re = process.waitFor();//因为是process这个进程调用.py文件， 所以要将当前进程阻塞等待.py文件执行完毕， 若果.py文件成功运行完毕，此方法将返回0，代表执行成功
//            System.out.println(re); //执行成功的话这里会打印一个0，否则会打印1，2或者其他数字
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
        StringBuilder s= (StringBuilder) remoteCall("https://online-education-lin.oss-cn-hangzhou.aliyuncs.com/clothes.jpg");
        System.out.println(s);
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
            socket = new Socket("172.31.134.176", 12345);
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
