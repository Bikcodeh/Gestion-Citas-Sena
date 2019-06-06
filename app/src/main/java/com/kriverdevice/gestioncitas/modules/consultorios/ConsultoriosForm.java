package com.kriverdevice.gestioncitas.modules.consultorios;


import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.kriverdevice.gestioncitas.Constants;
import com.kriverdevice.gestioncitas.R;
import com.kriverdevice.gestioncitas.interfaces.ModuleFormListener;
import com.kriverdevice.gestioncitas.models.Consultorios;

public class ConsultoriosForm extends Fragment implements ModuleFormListener {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.form_consultorios, container, false);
        Bundle argument = getArguments();

        if (argument != null) {
            if (argument.containsKey(Constants.KEY_BUNDLE_FORM_DATA)) {
                Parcelable data = getArguments().getParcelable(Constants.KEY_BUNDLE_FORM_DATA);
                fillForm(data);
            }
        }

        return v;
    }

    @Override
    public void onSave(String operation) {

        if (operation != Constants.MODULE_OPERATION_SAVE &&
                operation != Constants.MODULE_OPERATION_DELETE) return;

        switch (operation) {
            case Constants.MODULE_OPERATION_SAVE:
                save();
                break;
            case Constants.MODULE_OPERATION_DELETE:
                delete();
                break;
        }
    }

    private void delete() {
        // TODO: DELETE
        Toast.makeText(getContext(), "Delete", Toast.LENGTH_SHORT).show();
    }

    private void save() {
        // TODO: SAVE
        Toast.makeText(getContext(), "Save", Toast.LENGTH_SHORT).show();
    }

    private void fillForm(Parcelable data) {
        Consultorios consultorio = (Consultorios) data;
        Log.d("***->", consultorio.getdescripcion());
    }

    @Override
    public void onBack() {
    }
}
