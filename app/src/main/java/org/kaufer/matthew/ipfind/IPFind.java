package org.kaufer.matthew.ipfind;

import android.app.Activity;

public class IPFind extends Activity{
    private static String ip = null;

    public static String getIP(){
        return ip;
    }

    public static void setIP(String nip){
        ip = nip;
    }
}
