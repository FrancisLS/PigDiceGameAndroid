package com.example.pigdicegame;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class GameSetup extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_setup);
    }

    public void onSetupStartButton(View view) {
        EditText rawP1Name = (EditText) findViewById(R.id.player1_edit);
        EditText rawP2Name = (EditText) findViewById(R.id.player2_edit);
        String p1Name = rawP1Name.getText().toString(); //get P1 input from EditText. Put it in a string var
        String p2Name = rawP2Name.getText().toString(); //get P2 input from EditText. Put it in a string var
        Intent startGameIntent = new Intent(this, GameActivity.class);
        startGameIntent.putExtra("p1Name", p1Name); //sends string name data to game activity w/in intent
        startGameIntent.putExtra("p2Name", p2Name);
        startActivity(startGameIntent);
    }
}
