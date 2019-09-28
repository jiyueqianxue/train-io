package io.mine.ft.train.nio_talk.client;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Scanner;

import com.alibaba.fastjson.JSON;

import io.mine.ft.train.nio_talk.entity.RequestEntity;

/**
 * 2017/3/2 15:42.
 * <p>
 * Email: 978733153@qq.com
 * <p>
 * 聊天室客户端
 */
public class TalkClient {


    /**
     * 创建客户端
     *
     * @param hostName
     * @param port
     * @throws IOException
     */
    @SuppressWarnings("resource")
	public static void buildClient(String hostName, int port, String self, String friend) throws IOException {

        SocketChannel socketChannel = SocketChannel.open(new InetSocketAddress(hostName, port));

        Selector selector = Selector.open();

        socketChannel.configureBlocking(false);
        //接收朋友发来的信息
        socketChannel.register(selector, SelectionKey.OP_READ);
        Scanner scanner = new Scanner(System.in);
        ClientReadThread clientReadThread = new ClientReadThread(selector);
        Thread thread = new Thread(clientReadThread);
        thread.start();
        RequestEntity fistRequest = new RequestEntity();
        fistRequest.setFrom(self);
        fistRequest.setIsFirstRequest(1);
        socketChannel.write(ByteBuffer.wrap(JSON.toJSONString(fistRequest).getBytes("utf-8")));
        while (scanner.hasNextLine()) {
            String send = scanner.nextLine();
            RequestEntity requestEntity = new RequestEntity();
            requestEntity.setFrom(self);
            requestEntity.setTarget(friend);
            requestEntity.setBody(send);
            requestEntity.setIsFirstRequest(0);
            socketChannel.write(ByteBuffer.wrap(JSON.toJSONString(requestEntity).getBytes("utf-8")));
        }
    }
}
