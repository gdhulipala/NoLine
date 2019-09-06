package com.example.ganga.noline;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RadioButton;
import android.widget.Toast;

public class PaymentMethodActivity extends AppCompatActivity {

    RadioButton radioButton1, radioButton2, radioButton3, radioButton4;


    private static final String LOG_TAG = PaymentMethodActivity.class.getName();

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_method);

        // The following line presents the back arrow button on the action bar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        radioButton1 = (RadioButton) findViewById(R.id.radio_button1);
        radioButton2 = (RadioButton) findViewById(R.id.radio_button2);
        radioButton3 = (RadioButton) findViewById(R.id.radio_button3);
        radioButton4 = (RadioButton) findViewById(R.id.radio_button4);

        SharedPreferences sharedselection1 = getSharedPreferences("LEVEL", 0);

        if(sharedselection1!=null) {

            Log.e(LOG_TAG, "entered if loop");
            boolean isChk1 = sharedselection1.getBoolean("FIRST", false);
            boolean isChk2 = sharedselection1.getBoolean("SECOND", false);
            boolean isChk3 = sharedselection1.getBoolean("THIRD", false);
            boolean isChk4 = sharedselection1.getBoolean("FOURTH", false);

            if (isChk1) {
                radioButton1.setChecked(true);
            }
            if (isChk2) {
                radioButton2.setChecked(true);
            }
            if (isChk3) {
                radioButton3.setChecked(true);
            }
            if (isChk4) {
                radioButton4.setChecked(true);
            }

        }

        radioButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SharedPreferences sharedselection = getSharedPreferences("LEVEL", 0);
                SharedPreferences.Editor editor = sharedselection.edit();
                editor.putBoolean("FIRST", radioButton1.isChecked()); // first argument is a
                editor.putBoolean("SECOND", radioButton2.isChecked()); // first argument is a
                editor.putBoolean("THIRD", radioButton3.isChecked()); // first argument is a
                editor.putBoolean("FOURTH", radioButton3.isChecked()); // first argument is a
                editor.commit(); // Commit the changes
                Log.e(LOG_TAG, "called shared preferences");

            }
        });

        radioButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SharedPreferences sharedselection = getSharedPreferences("LEVEL", 0);
                SharedPreferences.Editor editor = sharedselection.edit();
                editor.putBoolean("FIRST", radioButton1.isChecked()); // first argument is a
                editor.putBoolean("SECOND", radioButton2.isChecked()); // first argument is a
                editor.putBoolean("THIRD", radioButton3.isChecked()); // first argument is a
                editor.putBoolean("FOURTH", radioButton3.isChecked()); // first argument is a
                editor.commit(); // Commit the changes
                Log.e(LOG_TAG, "called shared preferences");

            }
        });

        radioButton3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SharedPreferences sharedselection = getSharedPreferences("LEVEL", 0);
                SharedPreferences.Editor editor = sharedselection.edit();
                editor.putBoolean("FIRST", radioButton1.isChecked()); // first argument is a
                editor.putBoolean("SECOND", radioButton2.isChecked()); // first argument is a
                editor.putBoolean("THIRD", radioButton3.isChecked()); // first argument is a
                editor.putBoolean("FOURTH", radioButton3.isChecked()); // first argument is a
                editor.commit(); // Commit the changes
                Log.e(LOG_TAG, "called shared preferences");

            }
        });

        radioButton4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SharedPreferences sharedselection = getSharedPreferences("LEVEL", 0);
                SharedPreferences.Editor editor = sharedselection.edit();
                editor.putBoolean("FIRST", radioButton1.isChecked()); // first argument is a
                editor.putBoolean("SECOND", radioButton2.isChecked()); // first argument is a
                editor.putBoolean("THIRD", radioButton3.isChecked()); // first argument is a
                editor.putBoolean("FOURTH", radioButton3.isChecked()); // first argument is a
                editor.commit(); // Commit the changes
                Log.e(LOG_TAG, "called shared preferences");

            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu options from the res/menu/menu_catalog.xml file.
        // This adds menu items to the app bar.
        getMenuInflater().inflate(R.menu.menu_mainactivity, menu);
        return true;
    }

    // The following code is called when the invalidate options menu is called  in the oncreate method

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
        MenuItem menuItem = menu.findItem(R.id.add_inventory);
        menuItem.setVisible(false);
        MenuItem menuItem2 = menu.findItem(R.id.finditem_code);
        menuItem2.setVisible(false);
        MenuItem menuItem3 = menu.findItem(R.id.receipts);
        menuItem3.setVisible(false);
        MenuItem menuItem4 = menu.findItem(R.id.share_application);
        menuItem4.setVisible(false);
        MenuItem menuItem5 = menu.findItem(R.id.rate_experience);
        menuItem5.setVisible(false);
        MenuItem menuItem6 = menu.findItem(R.id.payment_method);
        menuItem6.setVisible(false);
        return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:


                 onBackPressed();
                 return true;


            //Use code below or the above one when handling the up navigation bar depending up on the scenario

               /** Intent upIntent = NavUtils.getParentActivityIntent(this);
                if (NavUtils.shouldUpRecreateTask(this, upIntent)) {
                    // This activity is NOT part of this app's task, so create a new task
                    // when navigating up, with a synthesized back stack.
                    TaskStackBuilder.create(this)
                            // Add all of this activity's parents to the back stack
                            .addNextIntentWithParentStack(upIntent)
                            // Navigate up to the closest parent
                            .startActivities();
                } else {
                    // This activity is part of this app's task, so simply
                    // navigate up to the logical parent activity.
                    upIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    NavUtils.navigateUpTo(this, upIntent);
                }

                return true;*/

            case R.id.save_changes:

                Toast.makeText(PaymentMethodActivity.this, "Saved Changes", Toast.LENGTH_SHORT).show();

            case R.id.edit_payment:

                return true;

            case R.id.add_payment:

                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }




}