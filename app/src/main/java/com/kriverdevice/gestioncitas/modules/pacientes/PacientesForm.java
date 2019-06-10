package com.kriverdevice.gestioncitas.modules.pacientes;


import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatEditText;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.kriverdevice.gestioncitas.Constants;
import com.kriverdevice.gestioncitas.R;
import com.kriverdevice.gestioncitas.interfaces.ModuleFormListener;
import com.kriverdevice.gestioncitas.models.Pacientes;

import static android.widget.Toast.LENGTH_SHORT;

public class PacientesForm extends Fragment implements ModuleFormListener {

    ModuleFormListener moduleFormListener;

    private String operaction = null;
    Integer id = null;
    AppCompatEditText
            nombre,
            apellido,
            identificacion;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.form_pacientes, container, false);
        nombre = v.findViewById(R.id.form_paciente_name);
        apellido = v.findViewById(R.id.form_paciente_apellido);
        identificacion = v.findViewById(R.id.form_paciente_identificacion);
        return v;
    }

    @Override
    public void onResume() {
        super.onResume();

        Bundle argument = getArguments();

        if (argument != null) {

            if (argument.containsKey(Constants.KEY_BUNDLE_FORM_DATA)) {

                // Establece el flag de la operacion que se realiza
                this.operaction = Constants.MODULE_OPERATION_UPDATE;

                // Extrae los datos del modelo pasados por  el bundle
                Parcelable data = getArguments().getParcelable(Constants.KEY_BUNDLE_FORM_DATA);

                // Llena los campos del formulario.
                fillForm(data);
            }
        } else {
            id = null;
            nombre.setText("");
            apellido.setText("");
            identificacion.setText("");
            // Establece el flag de la operacion que se realiza
            this.operaction = Constants.MODULE_OPERATION_NEW;
        }
    }

    @Override
    public void onSave(String operation) {

        if (!validateForm()) return;

        // Evita que se realice una operacion diferente notificada desde el menu
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
        this.id = null;

        // Notifica al padre para que haga el cambio de la vista
        if (moduleFormListener != null) {
            moduleFormListener.onBack();
        }
    }

    private boolean validateForm() {
        if (
                nombre.getText().toString().isEmpty() ||
                        apellido.getText().toString().isEmpty() ||
                        identificacion.getText().toString().isEmpty()
        ) {
            Toast.makeText(
                    getContext(),
                    R.string.form_validate_message,
                    LENGTH_SHORT)
                    .show();
            return false;
        }
        return true;
    }

    private void delete() {
        new Pacientes(getContext()).deleteBy(this.id);
        Toast.makeText(getContext(), R.string.delete_record, LENGTH_SHORT).show();
    }

    private void save() {
        // TODO: SAVE
        String respMsg = "TODO: SAVE";

        Pacientes pacientes = new Pacientes(getContext());
        pacientes.setIdentificacion(identificacion.getText().toString());
        pacientes.setNombre(nombre.getText().toString());
        pacientes.setApellido(apellido.getText().toString());

        switch (this.operaction) {

            case Constants.MODULE_OPERATION_NEW:
                pacientes.create();
                respMsg = getString(R.string.saved);
                break;

            case Constants.MODULE_OPERATION_UPDATE:
                pacientes.setId(this.id);
                pacientes.update();
                respMsg = getString(R.string.update);
                break;
        }

        Toast.makeText(getContext(), respMsg, LENGTH_SHORT).show();
    }

    private void fillForm(Parcelable data) {
        this.operaction = Constants.MODULE_OPERATION_UPDATE;

        Pacientes pacientes = (Pacientes) data;

        this.id = ((Pacientes) data).getId();
        nombre.setText(pacientes.getNombre());
        apellido.setText(pacientes.getApellido());
        identificacion.setText(pacientes.getIdentificacion());
    }

    public void setModuleFormListener(ModuleFormListener moduleFormListener) {
        this.moduleFormListener = moduleFormListener;
    }

    @Override
    public void onBack() {
    }
}
