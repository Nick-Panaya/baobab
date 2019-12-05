package io.mosaicnetworks.baobab;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import io.mosaicnetworks.babble.node.BabbleService;
import io.mosaicnetworks.babble.node.ServiceObserver;
import io.mosaicnetworks.babble.node.ServiceObserver;

public class AuctionActivity extends AppCompatActivity implements ServiceObserver {
    private String mMoniker;
    private String mCreator;
    private String mDescription;
    private String mStatus;
    private int mHighestBid;
    private int mParticipants;

    private final MessagingService mMessagingService =
            MessagingService.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auction);

        Intent intent = getIntent();
        mMoniker = intent.getStringExtra("MONIKER");

        mMessagingService.registerObserver(this);

        if (mMessagingService.getState() !=
                BabbleService.State.RUNNING_WITH_DISCOVERY) {
            Toast.makeText(this,
                    "Unable to advertise peers",
                    Toast.LENGTH_LONG).show();
        }

        stateUpdated();
    }

    @Override
    public void stateUpdated() {

        mCreator = mMessagingService.state.getCreator();
        mDescription = mMessagingService.state.getDescription();
        mStatus = mMessagingService.state.getStatus();
        mHighestBid = mMessagingService.state.getHighestBid();

        final TextView creatorTV = (TextView) findViewById(R.id.creatorValue);
        final TextView descTV = (TextView) findViewById(R.id.descriptionValue);
        final TextView statusTV = (TextView) findViewById(R.id.statusValue);
        final TextView hbTV = (TextView) findViewById(R.id.bidValue);

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                creatorTV.setText(mCreator);
                descTV.setText(mDescription);
                statusTV.setText(mStatus);
                hbTV.setText(Integer.toString(mHighestBid));
            }
        });
    }

    @Override
    public void onBackPressed() {
        mMessagingService.leave(null);
        super.onBackPressed();
    }

    @Override
    protected void onDestroy() {
        mMessagingService.removeObserver(this);
        super.onDestroy();
    }

    public void onClickBid(View v)
    {
        final TextView bidV = findViewById(R.id.newBidInput);
        int newBid = 0;

        try {
            newBid = Integer.parseInt(bidV.getText().toString());
        } catch (Exception e) {
            Toast.makeText(this, "Invalid bid", Toast.LENGTH_LONG).show();
        }

        mMessagingService.submitTx(
                new Message("", mMoniker, "BID", newBid).toBabbleTx());

    }
}
