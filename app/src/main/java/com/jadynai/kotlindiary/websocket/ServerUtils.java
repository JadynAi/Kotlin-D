package com.jadynai.kotlindiary.websocket;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;

/**
 * JadynAi since 2020/12/25
 */
class ServerUtils {

    @SuppressLint("DefaultLocale")
    public static String getIPAddress(Context ctx) {
        WifiManager wifi = (WifiManager) ctx.getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        WifiInfo connectionInfo = null;
        if (wifi != null) {
            connectionInfo = wifi.getConnectionInfo();
        }
        if (connectionInfo != null) {
            int ipAddress = connectionInfo.getIpAddress();
            return String.format(
                    "%d.%d.%d.%d",
                    ipAddress & 0xff,
                    ipAddress << 8 & 0xff,
                    ipAddress >> 16 & 0xff,
                    ipAddress >> 24 & 0xff
            );
        }
        return "0";
    }
}
