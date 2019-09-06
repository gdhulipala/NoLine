package com.example.ganga.noline;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Toast;

public class RatingActivity extends AppCompatActivity {

    public RatingBar ratingBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rating);

        // The following line presents the back arrow button on the action bar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Initialize RatingBar
        ratingBar = (RatingBar) findViewById(R.id.ratingBar);

    }

    public void rateMe(View view) {

        if (ratingBar.getRating() > 0) {

            EditText comments = (EditText) findViewById(R.id.comments);
            comments.getText().clear();

            /**Toast.makeText(getApplicationContext(),
             String.valueOf(ratingBar.getRating()), Toast.LENGTH_LONG).show();*/


            Toast.makeText(getApplicationContext(), " Your Response Is Captured", Toast.LENGTH_LONG).show();
        } else {

            Toast.makeText(getApplicationContext(), " Please Select A Value for Rating", Toast.LENGTH_LONG).show();

        }





    }

    @Override
    public boolean onOptionsItemSelected (MenuItem item){
        switch (item.getItemId()) {
            case android.R.id.home:
                Intent intent = new Intent(RatingActivity.this, MainActivity.class);
                startActivity(intent);
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
