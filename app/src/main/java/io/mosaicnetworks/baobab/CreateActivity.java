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

public class CreateActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Intent intent = getIntent();
        final String mMoniker = intent.getStringExtra("MONIKER");

        final TextInputLayout mTil = findViewById(R.id.descriptionInput);
        mTil.setErrorEnabled(false);
        mTil.setError("Cant be blank");

        FloatingActionButton fab = findViewById(R.id.fab);

        final Context context = this;

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String description = mTil.getEditText().getText().toString();
                Intent intent = new Intent(context, ChatActivity.class);
                intent.putExtra("MONIKER", mMoniker);
                intent.putExtra("DESCRIPTION", description );
                startActivity(intent);
            }
        });
    }

}
