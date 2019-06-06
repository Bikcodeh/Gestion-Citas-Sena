package com.kriverdevice.gestioncitas;

import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.kriverdevice.gestioncitas.interfaces.Main;
import com.kriverdevice.gestioncitas.ui.Dashboard;
import com.kriverdevice.gestioncitas.ui.DispatchModule;

public class MainActivity extends AppCompatActivity implements Dashboard.OnItemSelected, Main {

    Dashboard dashboard = new Dashboard();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getWindow().setStatusBarColor(getColor(R.color.colorPrimary));
        }

        //Dashboard dashboard = new Dashboard();
        dashboard.setOnItemSelectedListener(this);
        showDashboard();
    }

    @Override
    public void onClick(View v) {
        String module;
        switch (v.getId()) {
            case R.id.cardMedicos:
                module = Constants.MODULE_MEDICOS;
                break;
            case R.id.cardPacientes:
                module = Constants.MODULE_PACIENTES;
                break;
            case R.id.cardCitas:
                module = Constants.MODULE_CITAS;
                break;
            case R.id.cardConsultorios:
                module = Constants.MODULE_CONSULTORIOS;
                break;
            default:
                return;
        }

        DispatchModule dispatchModule = new DispatchModule();
        dispatchModule.setModule(module);
        dispatchModule.setMainListener(this);

        getSupportFragmentManager()
                .beginTransaction()
                .addToBackStack(null)
                .replace(R.id.containerMain, dispatchModule)
                .commit();
    }

    @Override
    public void onBackPressed() {
        // super.onBackPressed();
        showDashboard();
    }

    @Override
    public void showDashboard() {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.containerMain, dashboard)
                .commit();
    }


}
