package com.example.assignment6;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements prisoner1.PrisonerChoice, prisoner2.PrisonerChoice {

    private int currentPrisoner, prisoner1Score = 0, prisoner2Score = 0;

    private String prisoner1Choice = null;
    private String prisoner2Choice = null;
    private prisoner1 prisoner1Frag = null;
    private prisoner2 prisoner2Frag = null;

    public void onSaveInstanceState(Bundle saved) { // Saving info in bundles
        super.onSaveInstanceState(saved);

        TextView resultTV = findViewById(R.id.resultTV);

        saved.putInt("currentPrisoner", currentPrisoner);
        saved.putInt("prisoner1Choice", prisoner1Score);
        saved.putInt("prisoner2Choice", prisoner2Score);
        saved.putString("prisoner1Choice", prisoner1Choice);
        saved.putString("prisoner2Choice", prisoner2Choice);
        saved.putString("result", resultTV.getText().toString());

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView result = findViewById(R.id.resultTV);

        if (savedInstanceState != null) { // Importing info if bundle isn't null
            currentPrisoner = savedInstanceState.getInt("currentPrisoner", -1);
            prisoner1Score = savedInstanceState.getInt("prisoner1Choice", -2);
            prisoner2Score = savedInstanceState.getInt("prisoner2Choice", -3);
            prisoner1Choice = savedInstanceState.getString("prisoner1Choice", "default");
            prisoner2Choice = savedInstanceState.getString("prisoner2Choice", "default");
            result.setText(savedInstanceState.getString("result", ""));

        }

        FragmentManager fragManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragManager.beginTransaction();

        currentPrisoner = 1;

        prisoner1Frag = new prisoner1();
        prisoner2Frag = new prisoner2();
        transaction.add(R.id.prisonerContainer, prisoner1Frag);
        transaction.add(R.id.prisonerContainer, prisoner2Frag);

        switch(currentPrisoner) { // Switch case to determine which fragment will be showing
            case 1: transaction.hide(prisoner2Frag); transaction.commit(); break;
            case 2: transaction.hide(prisoner1Frag); transaction.commit(); break;
            default: transaction.hide(prisoner1Frag); transaction.hide(prisoner2Frag); transaction.commit();
        }
    }

    public void prisonerChoice(int prisonerNumber, String choice) {

        // Using these strings to test against later
        String testBetray = "Betray";
        String testQuiet = "Quiet";

        FragmentManager fragManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragManager.beginTransaction();

        if (currentPrisoner == 1) {
            prisoner1Choice = choice;
            currentPrisoner++;
            transaction.hide(prisoner1Frag);
            transaction.show(prisoner2Frag);
            transaction.commit();
        }
        else {
            TextView resultTV = findViewById(R.id.resultTV);
            prisoner2Choice = choice;
            currentPrisoner++;
            transaction.hide(prisoner2Frag);
            transaction.commit();

            // Testing Strings to determine points
            if (prisoner1Choice.equals(testBetray) && prisoner2Choice.equals(testQuiet)) {
                resultTV.setText("Prisoner 1 - Betray - Gains 5 points \nPrisoner 2 - Quiet - Gains 0 points");
                prisoner1Score += 5;
            }
            else if (prisoner1Choice.equals(testBetray) && prisoner2Choice.equals(testBetray)) {
                resultTV.setText("Prisoner 1 - Betray - Gains 1 point \nPrisoner 2 - Betray - Gains 1 point");
                prisoner1Score++;
                prisoner2Score++;
            }
            else if (prisoner1Choice.equals(testQuiet) && prisoner2Choice.equals(testQuiet)) {
                resultTV.setText("Prisoner 1 - Quiet - Gains 3 points \nPrisoner 2 - Quiet - Gains 3 points");
                prisoner1Score += 3;
                prisoner2Score += 3;
            }
            else if (prisoner1Choice.equals(testQuiet) && prisoner2Choice.equals(testBetray)) {
                resultTV.setText("Prisoner 1 - Quiet - Gains 0 points \nPrisoner 2 - Betray - Gains 5 points");
                prisoner2Score += 5;
            }

            // Outputting final score on screen
            TextView finalScore = findViewById(R.id.finalScore);
            finalScore.setText("Final Score:\nPrisoner 1: " + prisoner1Score + "\nPrisoner 2: " + prisoner2Score);

        }
    }
}
