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
        Intent intent = new Intent(this, AuctionActivity.class);
        intent.putExtra("MONIKER", moniker);
        startActivity(intent);
    }

    @Override
    public void onStartedNew(String moniker) {
        Intent intent = new Intent(this, CreateActivity.class);
        intent.putExtra("MONIKER", moniker);
        startActivity(intent);
    }
}
