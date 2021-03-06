package com.lord.learn.design.reactor;

import com.lord.learn.socket.TalkBackgroundThread;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * Created by xj_xiaocheng on 2015/1/22.
 */
public class NioClient {

    public static void main(String[] args) throws Exception {
        String name = "小亮";
        System.out.println("我是" + name + "，客户端");
        //向本机的5700端口发出客户请求
        Socket socket = new Socket("10.144.33.114", 5700);

        //由系统标准输入设备构造BufferedReader对象
        BufferedReader sin = new BufferedReader(new InputStreamReader(System.in));

        //由Socket对象得到输出流，并构造PrintWriter对象
        PrintWriter os = new PrintWriter(socket.getOutputStream());
        //由Socket对象得到输入流，并构造相应的BufferedReader对象
        BufferedReader is = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        new Thread(new TalkBackgroundThread("小明", is)).start();

        String readline = sin.readLine(); //从系统标准输入读入一字符串

        //若从标准输入读入的字符串为 "bye"则停止循环
        while (!readline.equals("bye")) {
            //将从系统标准输入读入的字符串输出到Server
            os.println(readline);
            //刷新输出流，使Server马上收到该字符串
            os.flush();
            //在系统标准输出上打印读入的字符串
            System.out.println("我说:" + readline);

            readline = sin.readLine(); //从系统标准输入读入一字符串
        }

        os.close(); //关闭Socket输出流
        is.close(); //关闭Socket输入流
        socket.close(); //关闭Socket
    }
}
