package io.mosaicnetworks.baobab;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import	com.google.android.material.textfield.TextInputLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class CreateActivity extends AppCompatActivity {

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create);
        final Context context = this;

        // Retrieve the moniker passed from previous activity
        Intent intent = getIntent();
        final String mMoniker = intent.getStringExtra("MONIKER");

        // Get handle to TextView item
        final TextView mTv = findViewById(R.id.description);

        // Define what the button does
        Button button = findViewById(R.id.submit_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Read description text
                final String description = mTv.getText().toString();

                Intent intent = new Intent(context, AuctionActivity.class);
                // Pass description and moniker to the next activity
                intent.putExtra("DESCRIPTION", description );
                intent.putExtra("MONIKER", mMoniker);

                startActivity(intent);
            }
        });
    }

}
