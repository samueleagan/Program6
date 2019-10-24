package com.example.assignment6;


import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class prisoner1 extends Fragment  {

    public prisoner1() {
        // Required empty public constructor
    }

    public interface PrisonerChoice {
        void prisonerChoice(int prisonerNumber, String choice);
    }

    private prisoner1.PrisonerChoice myActivity = null;

    public void onAttach(Context context) {
        super.onAttach(context);
        myActivity = (prisoner1.PrisonerChoice) context;

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_prisoner1, container, false);

        Button betrayBTN = v.findViewById(R.id.betryBTN);
        Button quietBTN = v.findViewById(R.id.stayBTN);

        // Having buttons for each selection
        betrayBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myActivity.prisonerChoice(1, "Betray");
            }

        });

        quietBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myActivity.prisonerChoice(2, "Quiet");
            }

        });

        return v;
    }

}
