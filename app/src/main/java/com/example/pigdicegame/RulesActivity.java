package com.example.pigdicegame;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class RulesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rules);

        String[] rulesArray = getResources().getStringArray(R.array.rules); //references the string-array with the rules in XML file
        TextView rulesTextView = (TextView) findViewById(R.id.rules_text);
        String rules = "";
        for(int i=0; i<rulesArray.length; i++) {
            rules += "• " + rulesArray[i].toString() + "\n";    //concatenates all rule items from array into a string
        }
        rulesTextView.setText(rules);   //sets the textView for the rules with the "rules" string variable
    }

    public void onReturnButton(View view) {
        finish();   //destroys this activity and we return to the main activity
    }
}
