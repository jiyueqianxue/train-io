package io.mine.ft.train.nio_talk.entity;

import java.io.IOException;

import io.mine.ft.train.nio_talk.server.TalkServer;

/**
 * 2017/3/3 10:00.
 * <p>
 * Email: 978733153@qq.com
 */
public class ServerMain {


    public static void main(String[] args) throws IOException {

        TalkServer.buildServer(8084);

    }
}
