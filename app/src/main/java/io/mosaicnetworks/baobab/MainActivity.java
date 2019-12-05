package io.mosaicnetworks.baobab;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import io.mosaicnetworks.babble.node.KeyPair;
import io.mosaicnetworks.babble.configure.BaseConfigActivity;
import io.mosaicnetworks.babble.node.BabbleService;

import android.util.Log;

public class MainActivity extends BaseConfigActivity {

    public static final String TAG = "FIRST-BABBLE-APP";

    @Override
    public BabbleService getBabbleService() {
        return MessagingService.getInstance();
    }

    @Override
    public void onJoined(String moniker) {

    }

    @Override
    public void onStartedNew(String moniker) {
        Bundle b = new Bundle();
        b.putString("MONIKER", moniker);

        Intent intent = new Intent(this, CreateActivity.class);
        intent.putExtras(b);

        //intent.putExtra("MONIKER", moniker);

        startActivity(intent);
    }
}
