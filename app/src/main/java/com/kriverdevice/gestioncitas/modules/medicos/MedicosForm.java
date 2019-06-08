package com.kriverdevice.gestioncitas.modules.medicos;


import android.os.Bundle;
import android.os.Parcelable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatEditText;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.kriverdevice.gestioncitas.Constants;
import com.kriverdevice.gestioncitas.R;
import com.kriverdevice.gestioncitas.interfaces.ModuleFormListener;
import com.kriverdevice.gestioncitas.models.Especialidades;
import com.kriverdevice.gestioncitas.models.Medicos;

import java.util.ArrayList;

import static com.kriverdevice.gestioncitas.R.layout.support_simple_spinner_dropdown_item;

public class MedicosForm extends Fragment implements ModuleFormListener {

    AppCompatEditText nombre, apellido, identificacion;
    Spinner profession;
    ArrayList<String> especialidades;
    ArrayAdapter<String> professions;
    View v;
    private String operaction = Constants.MODULE_OPERATION_NEW;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.form_medicos, container, false);

        nombre = v.findViewById(R.id.form_medico_nomnbre);
        apellido = v.findViewById(R.id.form_medico_apellido);
        identificacion = v.findViewById(R.id.form_medico_identificacion);
        profession = v.findViewById(R.id.form_medico_profession);

        especialidades = new Especialidades(this.getContext()).getEspecialidades();
        professions = new ArrayAdapter<String>(
                this.getContext(),
                support_simple_spinner_dropdown_item,
                especialidades
        );
        profession.setAdapter(professions);
        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
        Bundle argument = getArguments();

        if (argument != null) {
            if (argument.containsKey(Constants.KEY_BUNDLE_FORM_DATA)) {
                Parcelable data = getArguments().getParcelable(Constants.KEY_BUNDLE_FORM_DATA);
                this.operaction = Constants.MODULE_OPERATION_UPDATE;
                fillForm(data);
            }
        } else {
            nombre.setText("");
            apellido.setText("");
            profession.setSelection(0);
        }
    }

    @Override
    public void onSave(String operation) {

        if (operation != Constants.MODULE_OPERATION_SAVE &&
                operation != Constants.MODULE_OPERATION_DELETE
        ) return;

        switch (operation) {
            case Constants.MODULE_OPERATION_SAVE:
                save();
                break;
            case Constants.MODULE_OPERATION_DELETE:
                delete();
                break;
        }
        nombre.setText("");
        apellido.setText("");
        identificacion.setText("");
    }

    private void delete() {
        // TODO: DELETE
        Toast.makeText(getContext(), "Delete", Toast.LENGTH_SHORT).show();
    }

    private void save() {

        Medicos medicos = new Medicos(getContext());
        medicos.setIdentificacion(identificacion.getText().toString());
        medicos.setNombre(nombre.getText().toString());
        medicos.setApellido(apellido.getText().toString());
        medicos.setProfesion(profession.getSelectedItem().toString());

        switch (this.operaction) {
            case Constants.MODULE_OPERATION_NEW:
                medicos.create();
                break;

            case Constants.MODULE_OPERATION_UPDATE:
                medicos.update();
                break;
        }
        Toast.makeText(getContext(), getString(R.string.saved), Toast.LENGTH_SHORT).show();
    }

    // Llena el formulario
    private void fillForm(Parcelable data) {
        Medicos medico = (Medicos) data;
        nombre.setText(medico.getNombre());
        apellido.setText(medico.getApellido());
        identificacion.setText(medico.getIdentificacion());
        int idProfession = especialidades.indexOf(medico.getProfesion());
        profession.setSelection(idProfession);
    }

    @Override
    public void onBack() {
    }

}
