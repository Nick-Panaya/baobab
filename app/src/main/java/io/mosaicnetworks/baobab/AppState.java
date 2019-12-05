package io.mosaicnetworks.baobab;

import com.google.gson.JsonSyntaxException;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.mosaicnetworks.babble.node.BabbleState;

public class AppState implements BabbleState {

    private byte[] mStateHash = new byte[0];
    private final Map<Integer, BabbleTx> mState = new HashMap<>();
    private Integer mNextIndex = 0;

    private String mCreator = "";
    private String mDescription = "";
    private String mStatus = "NULL"; // NULL, OPEN, or CLOSED
    private int mMin = 0;

    @Override
    public byte[] applyTransactions(byte[][] transactions) {

        Boolean breakout = false;
        for (byte[] rawTx : transactions) {
            String tx = new String(rawTx, StandardCharsets.UTF_8);
            BabbleTx babbleTx;

            try {
                babbleTx = BabbleTx.fromJson(tx);
            } catch (JsonSyntaxException ex) {
                //skip any malformed transactions
                continue;
            }

            switch (babbleTx.type) {
                case "INIT":
                    if (mStatus == "NULL") {
                        mCreator = babbleTx.from;
                        mDescription = babbleTx.text;
                        mMin = babbleTx.amount;
                        mStatus = "OPEN";
                    } else {
                        continue;
                    }
                    break;
                case "BID":
                    if (mStatus == "OPEN") {
                        if (babbleTx.amount > mMin) {
                            mMin = babbleTx.amount;
                        } else {
                            continue;
                        }
                    } else {
                        continue;
                    }
                    break;
                case "CLOSE":
                    if (mStatus != "CLOSED") {
                        mStatus = "CLOSED";
                        breakout = true;
                    }
                    break;
                default:
                    break;
            }

            mState.put(mNextIndex, babbleTx);
            mNextIndex++;

            if (breakout) {
                break;
            }
        }

        updateStateHash();
        return mStateHash;
    }


    @Override
    public void reset() {
        mState.clear();
        mNextIndex = 0;
        mCreator = "";
        mDescription = "";
        mStatus = "NULL";
        mMin = 0;
    }

    public String getCreator() {
        return mCreator;
    }

    public String getDescription() {
        return mDescription;
    }

    public String getStatus() {
        return mStatus;
    }

    public int getHighestBid() {
        return mMin;
    }

    private void updateStateHash() {
        // TODO: implement this
    }
}
