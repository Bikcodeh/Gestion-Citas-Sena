package com.kriverdevice.gestioncitas.ui;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kriverdevice.gestioncitas.R;

public class Dashboard extends Fragment implements View.OnClickListener {

    OnItemSelected itemSelectedListener;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.dashboard, container, false);
        v.findViewById(R.id.cardMedicos).setOnClickListener(this);
        v.findViewById(R.id.cardPacientes).setOnClickListener(this);
        v.findViewById(R.id.cardCitas).setOnClickListener(this);
        v.findViewById(R.id.cardConsultorios).setOnClickListener(this);
        return v;
    }

    public void setOnItemSelectedListener(OnItemSelected itemSelectedListener) {
        this.itemSelectedListener = itemSelectedListener;
    }

    @Override
    public void onClick(View v) {
        if ( itemSelectedListener == null ) return;
        itemSelectedListener.onClick(v);
    }

    public interface OnItemSelected {
        void onClick(View v);
    }

}
