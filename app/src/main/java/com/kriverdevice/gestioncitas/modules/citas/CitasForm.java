package com.kriverdevice.gestioncitas.modules.citas;


import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatTextView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.kriverdevice.gestioncitas.Constants;
import com.kriverdevice.gestioncitas.R;
import com.kriverdevice.gestioncitas.interfaces.ModuleFormListener;
import com.kriverdevice.gestioncitas.models.Citas;

public class CitasForm extends Fragment implements ModuleFormListener {

    AppCompatEditText pacienteId, medicoId, fecha, hora;
    TextView pacienteName, medicoName;
    AppCompatTextView profesiones;
    Spinner consultorios;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.form_citas, container, false);

        Bundle argument = getArguments();

        if (argument != null) {
            if (argument.containsKey(Constants.KEY_BUNDLE_FORM_DATA)) {
                Parcelable data = getArguments().getParcelable(Constants.KEY_BUNDLE_FORM_DATA);
                fillForm(data, v);
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

    private void fillForm(Parcelable data, View v) {
        Citas cita = (Citas) data;

        pacienteId = v.findViewById(R.id.form_citas_paciente_identificacion);
        medicoId = v.findViewById(R.id.form_citas_medico_identificacion);
        fecha = v.findViewById(R.id.form_citas_fecha);
        hora = v.findViewById(R.id.form_citas_hora);

        pacienteName = v.findViewById(R.id.form_citas_paciente_name);
        medicoName = v.findViewById(R.id.form_citas_medico_name);
        profesiones = v.findViewById(R.id.form_citas_medico_profesion);
        consultorios = v.findViewById(R.id.form_citas_consultorio_name);

        pacienteId.setText(cita.getPacienteIdentification());
        medicoId.setText(cita.getMedicoIdentification());
        fecha.setText(cita.getFecha());
        hora.setText(cita.getHora());
        pacienteName.setText(cita.getPacienteName());
        medicoName.setText(cita.getMedicoName());

        // TODO: Poblar el combo y buscar la profesion.
        // profesiones.setText("Profesion");
        // consultorios.setSelection(0);

    }

    @Override
    public void onBack() {
    }

}
