package com.example.ganga.noline;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class ReceiptsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receipts);

        SharedPreferences shrdpref = getSharedPreferences("my_pref", MODE_PRIVATE);
        String totalValue = shrdpref.getString("totalValue","2.5");
        String itemsTotalText = shrdpref.getString("noofItems", "1");

        TextView totalsummary = (TextView) findViewById(R.id.totalfinal);
        totalsummary.setText("$ " + totalValue);

        TextView itemsTotal = (TextView) findViewById(R.id.numberofItems);
        itemsTotal.setText(itemsTotalText + " Items");

        /**Date currentDate = new Date(System.currentTimeMillis());
        Toast.makeText(PaymentSummaryActivity.this, currentDate.toString(), Toast.LENGTH_SHORT).show();
        TextView dateTextView = (TextView) findViewById(R.id.current_date);
        dateTextView.setText(currentDate.toString());*/


        TextView dateTextView = (TextView) findViewById(R.id.current_date);
        TextView timeStamp = (TextView) findViewById(R.id.current_time);



        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("dd MMM yyyy/HH:mm:ss", Locale.US);
        String strDate = sdf.format(cal.getTime());

        String[] values = strDate.split("/", 0);

        for(int i =0; i<values.length; i++){

            dateTextView.setText(values[0]);
            timeStamp.setText(values[1]);

        }

        LinearLayout layoutLinear = (LinearLayout) findViewById(R.id.first_receipt);
        layoutLinear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ReceiptsActivity.this, ReceiptDetailsActivity.class);
                startActivity(intent);
            }
        });

    }
}
