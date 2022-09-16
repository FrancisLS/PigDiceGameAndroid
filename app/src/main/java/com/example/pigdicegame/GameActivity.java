package com.example.pigdicegame;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class GameActivity extends AppCompatActivity {

    private int turn = 0;   //determines whose turn it is. 0 is P1's, while 1 is P2's. P1 always plays first
    private PlayerMaker theGame;    //initializes the game object
    int image;  //for choosing which side of the die picture to send to ImageView

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        Intent intent = getIntent();
        String p1Name = intent.getStringExtra("p1Name");   //retrieves the passed name string from intent from GameSetup
        String p2Name = intent.getStringExtra("p2Name");

        theGame = new PlayerMaker(p1Name, p2Name, 0, 0, 0, 0);  //game constructor
        updatePlayerTurn();    //displays turn
        updateScoreBoard();    //displays player stats
    }

    public class PlayerMaker {  //constructor for the Pig dice game. Holds stats/info of players
        private String[] players;
        private int[] round;
        private int[] total;

        public PlayerMaker(String player1, String player2, int round1, int round2, int total1, int total2) {
            this.players = new String[]{player1, player2};
            this.round = new int[]{round1, round2};
            this.total = new int[]{total1, total2};
        }
    }

    public void onRollButton(View view) {
        ImageView diePicture = (ImageView) findViewById(R.id.die_picture);
        int rollNum = (int) (Math.floor(Math.random() * 6) + 1);    //formula for rolling the die numbers 1-6

        if (rollNum==1) {
            image = R.drawable.die1;    //There might be an Android Studio bug here. It keeps saying there's no "die1.png" in the drawable resource even though it exists and the picture shows up when user rolls 1
            diePicture.setImageResource(image);
            theGame.round[turn] = 0;    //change round score to 0 since we rolled a 1
            CharSequence winnerText = "Oh, no! " + theGame.players[turn] + " rolled a 1";
            int duration = Toast.LENGTH_LONG;
            Toast toast = Toast.makeText(this, winnerText, duration);
            toast.show();   //announce that you rolled a 1
            changeTurn();   //change turn since player rolled a 1.
        }

        else if (rollNum==2) {
            image = R.drawable.die2;
            diePicture.setImageResource(image);
            theGame.round[turn] += 2;
        }

        else if (rollNum==3) {
            image = R.drawable.die3;
            diePicture.setImageResource(image);
            theGame.round[turn] += 3;
        }

        else if (rollNum==4) {
            image = R.drawable.die4;
            diePicture.setImageResource(image);
            theGame.round[turn] += 4;
        }

        else if (rollNum==5) {
            image = R.drawable.die5;
            diePicture.setImageResource(image);
            theGame.round[turn] += 5;
        }

        else {
            image = R.drawable.die6;
            diePicture.setImageResource(image);
            theGame.round[turn] += 6;
        }
        updatePlayerTurn();
        updateScoreBoard();
    }

    public void onHoldButton(View view) {
        theGame.total[turn] += theGame.round[turn]; //add current player's round score to his total score
        theGame.round[turn] = 0;    //resets round score of current player back to 0 before changing turns
        updateScoreBoard();

        if (theGame.total[0] >= 100) {
            CharSequence winnerText = theGame.players[0] + " is the winner!";
            int duration = Toast.LENGTH_LONG;
            Toast toast = Toast.makeText(this, winnerText, duration);
            toast.show();   //announce winner via toast
            Intent returnIntent = new Intent(this, MainActivity.class);
            startActivity(returnIntent);    //return to main menu after someone wins
        }

        else if (theGame.total[1] >= 100) {
            CharSequence winnerText = theGame.players[1] + " is the winner!";
            int duration = Toast.LENGTH_LONG;
            Toast toast = Toast.makeText(this, winnerText, duration);
            toast.show();
            Intent returnIntent = new Intent(this, MainActivity.class);
            startActivity(returnIntent);    //return to main menu after someone wins
        }

        else {
            changeTurn();   //if no one has reached 100 yet, change turns
            image = R.drawable.empty_die;   //change the die image back to empty side when changing turns
            updateDieImage();
        }
    }

    public void onRestartButton(View view) {    //reset all player scores. Turn resets to player 1. Die image resets to empty die
        theGame.round[0] = 0;
        theGame.round[1] = 0;
        theGame.total[0] = 0;
        theGame.total[1] = 0;
        turn = 0;
        image = R.drawable.empty_die;
        updateScoreBoard();
        updatePlayerTurn();
        updateDieImage();
        CharSequence resetText = "Game has been reset.";
        int duration = Toast.LENGTH_LONG;
        Toast toast = Toast.makeText(this, resetText, duration);
        toast.show();   //pop-up stating that game has been reset
    }

    public void onEndGameButton(View view) {    //prematurely end the game and return to main menu
        Intent returnIntent = new Intent(this, MainActivity.class);
        startActivity(returnIntent);
    }

    public void changeTurn() {
        if (turn==0) {
            turn = 1;
            updatePlayerTurn();
        }
        else {
            turn = 0;
            updatePlayerTurn();
        }
    }


    public void updatePlayerTurn() {    //sets text for the TextView showing whose turn it is
        TextView playerTurn = (TextView) findViewById(R.id.player_turn_text);
        String turnReport = "It is " + theGame.players[turn] + "\'s turn.";
        playerTurn.setText(turnReport);
    }

    public void updateScoreBoard() {    //sets text for scoreBoard TextView
        TextView scoreBoard = (TextView) findViewById(R.id.score_board);
        String scoreReport = "";
        scoreReport += "Your ROUND SCORE is " + theGame.round[turn] + ".\n";
        scoreReport += theGame.players[0] + " has " + theGame.total[0] + " total points.\n";
        scoreReport += theGame.players[1] + " has " + theGame.total[1] + " total points.";
        scoreBoard.setText(scoreReport);
    }

    public void updateDieImage() {  //sets correct die image for ImageView when player rolls a die
        ImageView diePicture = (ImageView) findViewById(R.id.die_picture);
        diePicture.setImageResource(image);
    }
}
