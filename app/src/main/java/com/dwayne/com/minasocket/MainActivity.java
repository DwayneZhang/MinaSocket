package com.dwayne.com.minasocket;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.dwayne.com.minasocket.socket.ClientConnectManager;

/**
 * @author Dwayne
 * @email dev1024@foxmail.com
 * @time 2019/8/25 16:56
 * @change
 * @chang time
 * @class describe
 */


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ClientConnectManager.getInstance().init(this);
        ClientConnectManager.getInstance().connect(this);
    }
}
