package com.jadynai.kotlindiary.websocket;

import android.content.Context;
import android.util.Log;

import com.koushikdutta.async.callback.CompletedCallback;
import com.koushikdutta.async.http.WebSocket;
import com.koushikdutta.async.http.server.AsyncHttpServer;
import com.koushikdutta.async.http.server.AsyncHttpServerRequest;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * JadynAi since 2020/12/16
 */
class TServer {

    private List<WebSocket> mSockets = Collections.synchronizedList(new ArrayList<WebSocket>());
    private AsyncHttpServer mServer = new AsyncHttpServer();

    public TServer() {
    }

    public void start(Context context) {
        AsyncHttpServer.WebSocketRequestCallback callback = new AsyncHttpServer.WebSocketRequestCallback() {
            @Override
            public void onConnected(final WebSocket webSocket, AsyncHttpServerRequest request) {
                Log.d("ceceS", "onConnected");
                mSockets.add(webSocket);
                webSocket.send("Welcome Client");
                webSocket.setClosedCallback(new CompletedCallback() {
                    @Override
                    public void onCompleted(Exception ex) {
                        try {
                            if (ex != null) Log.e("ceceS", "Error");
                        } finally {
                            mSockets.remove(webSocket);
                        }
                    }
                });
                webSocket.setStringCallback(new WebSocket.StringCallback() {
                    @Override
                    public void onStringAvailable(String s) {
                        Log.d("ceceS", "server callback print" + s);
                        for (WebSocket socket : mSockets) {
                            socket.send("this is from server");
                        }
                    }
                });
            }
        };
        String regex = "/eavesdrop";
        mServer.websocket(regex, callback);
        //监听指定端口
        int port = 2333;
        mServer.listen(port);
        Log.d("ceceS", "http://" + ServerUtils.getIPAddress(context) + ":" + port + regex);
    }
}
