package io.mine.ft.train.nio_1;
// $Id$

import java.io.FileInputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class ReadAndShow
{
	static public void main( String args[] ) throws Exception {
		FileInputStream fin = new FileInputStream("C:\\Users\\machao\\Desktop\\2.txt");
		FileChannel fc = fin.getChannel();

		ByteBuffer buffer = ByteBuffer.allocate(1024);

		fc.read(buffer);

		//读写切换，从buffer中读取数据
		buffer.flip();

		int i = 0;
		while (buffer.remaining() > 0) {
			byte b = buffer.get();
			System.out.println("Character " + i + ": " + ((char)b));
			i++;
		}
		fin.close();
	}
}
