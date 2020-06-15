package io.mine.ft.train.nio_atguigu;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class NIOFileChannel01 {

    public static void main(String[] args) throws FileNotFoundException {

        String str = "hello, 尚硅谷";

        FileOutputStream fileOutputStream = new FileOutputStream("");

        FileChannel channel = fileOutputStream.getChannel();
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        buffer.put(str.getBytes());
        buffer.flip();

    }
}
