package io.mine.ft.train.nio_talk.server;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.util.Set;

import com.alibaba.fastjson.JSON;

import io.mine.ft.train.nio_talk.entity.Contants;
import io.mine.ft.train.nio_talk.entity.RequestEntity;

/**
 * 2017/3/2 15:43.
 * <p>
 * Email: 978733153@qq.com
 * <p>
 * 聊天室服务端
 */
public class TalkServer {

    public static int bufferSize = 1024;

    /**
     * 建立服务器
     *
     * @param port
     */
    public static void buildServer(int port) throws IOException {

        //服务器通道
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        //注册器
        Selector selector = Selector.open();
        //网络端口
        SocketAddress socketAddress = new InetSocketAddress(port);
        serverSocketChannel.bind(socketAddress);
        serverSocketChannel.configureBlocking(false);//异步
        //注册,返回感兴趣的selectionKey对象
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);//这里SelectionKey.OP_ACCEPT也可以

        while (true) {
            //准备就绪的通道
            int selectCount = selector.select();
            if (selectCount == 0) {
                continue;
            } else {
                Set<SelectionKey> selectionKeySet = selector.selectedKeys();
                for (SelectionKey selectionKey : selectionKeySet) {
                    if (selectionKey.isValid() && selectionKey.isAcceptable()) {
                        //返回和客户端通信的对象
                        SocketChannel socketChannel = ((ServerSocketChannel) selectionKey.channel()).accept();
                        socketChannel.configureBlocking(false);
                        //和客户端通信对象注册可读事件
                        socketChannel.register(selector, SelectionKey.OP_READ);
                    } else if (selectionKey.isValid() && selectionKey.isReadable()) {
                        //返回和客户端通信的socket
                        SocketChannel socketChannel = (SocketChannel) selectionKey.channel();
                        ByteBuffer byteBuffer = ByteBuffer.allocate(bufferSize);
                        int count = socketChannel.read(byteBuffer);
                        if (count == -1) {
                            socketChannel.close();
                        } else {
                            byteBuffer.flip();
                            String received = Charset.forName("utf-8").newDecoder().decode(byteBuffer).toString();
                            RequestEntity requestEntity = JSON.parseObject(received, RequestEntity.class);
                            //是第一次请求
                            if (requestEntity.getIsFirstRequest() == 1) {
                                Contants.socketChannelMap.put(requestEntity.getFrom(), socketChannel);
                            } else {//不是第一次请求[这里是和朋友聊天]

                                SocketChannel targetChannel = Contants.socketChannelMap.get(requestEntity.getTarget());
                                if (targetChannel == null) {
                                    throw new RuntimeException(Contants.Error.FRIEND_NOT_EXIST + "#" + Contants.Error.FRIEND_NOT_EXIST_MSG);
                                }
                                //向好友输出信息
                                targetChannel.write(ByteBuffer.wrap(received.getBytes("utf-8")));
                            }
                        }
                    }
                    //移除已经处理完的selectionKey
                    selectionKeySet.remove(selectionKey);
                }
            }
        }
    }
}
