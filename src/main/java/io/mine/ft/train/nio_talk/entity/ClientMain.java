package io.mine.ft.train.nio_talk.entity;

import java.io.IOException;

import io.mine.ft.train.nio_talk.client.TalkClient;

/**
 * 2017/3/3 10:00.
 * <p>
 * Email: 978733153@qq.com
 */
public class ClientMain {


    public static void main(String[] args) throws IOException {

        //TalkClient.buildClient("127.0.0.1",8084,"person_1","person_2");
        TalkClient.buildClient("127.0.0.1",8084,"person_2","person_1");


    }
}
