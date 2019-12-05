package io.mosaicnetworks.baobab;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.StringRes;
import androidx.appcompat.app.AppCompatActivity;

import com.stfalcon.chatkit.messages.MessageInput;
import com.stfalcon.chatkit.messages.MessagesList;
import com.stfalcon.chatkit.messages.MessagesListAdapter;

import java.util.List;

import io.mosaicnetworks.babble.node.BabbleService;
import io.mosaicnetworks.babble.node.ServiceObserver;

public class ChatActivity extends AppCompatActivity implements ServiceObserver {
    private MessagesListAdapter<Message> mAdapter;
    private String mMoniker;
    private String mDescription;
    private final MessagingService mMessagingService =
            MessagingService.getInstance();
    private Integer mMessageIndex = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        Intent intent = getIntent();
        mMoniker = intent.getStringExtra("MONIKER");
        mDescription = intent.getStringExtra("DESCRIPTION");
        initialiseAdapter();
        mMessagingService.registerObserver(this);

        if (mMessagingService.getState() !=
                BabbleService.State.RUNNING_WITH_DISCOVERY) {
            Toast.makeText(this,
                    "Unable to advertise peers",
                    Toast.LENGTH_LONG).show();
        }

        mMessagingService.submitTx(
                new Message("New Auction from " + mMoniker +": " + mDescription, mMoniker, "INIT", 0).toBabbleTx());
    }

    private void initialiseAdapter() {
        MessagesList mMessagesList = findViewById(R.id.messagesList);
        mAdapter = new MessagesListAdapter<>(mMoniker, null);
        mMessagesList.setAdapter(mAdapter);
        MessageInput input = findViewById(R.id.input);

        input.setInputListener(new MessageInput.InputListener() {
            @Override
            public boolean onSubmit(CharSequence input) {

                int amount = 0;
                try {
                    amount = Integer.parseInt(input.toString());
                }
                catch (NumberFormatException e)
                {
                  return false;
                }

                // TODO: Display error message if amount < state.min

                mMessagingService.submitTx(
                        new Message("", mMoniker, "BID", amount).toBabbleTx());

                return true;
            }
        });
    }

    @Override
    public void stateUpdated() {

        final List<Message> newMessages =
                mMessagingService.state.getMessagesFromIndex(mMessageIndex);

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                for (Message message : newMessages) {
                    mAdapter.addToStart(message, true);
                }
            }
        });

        mMessageIndex = mMessageIndex + newMessages.size();
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
}
