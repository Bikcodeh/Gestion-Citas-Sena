package com.kriverdevice.gestioncitas.modules.consultorios;


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
import com.kriverdevice.gestioncitas.models.Consultorios;

import static android.widget.Toast.LENGTH_SHORT;

public class ConsultoriosForm extends Fragment implements ModuleFormListener {

    ModuleFormListener moduleFormListener;

    private String operaction = null;
    Integer id = null;
    AppCompatEditText
            descripcion,
            telefono,
            direccion;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.form_consultorios, container, false);
        descripcion = v.findViewById(R.id.form_consultorio_description);
        telefono = v.findViewById(R.id.form_consultorio_telefono);
        direccion = v.findViewById(R.id.form_consultorio_direccion);
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
            descripcion.setText("");
            telefono.setText("");
            direccion.setText("");
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
        descripcion.setText("");
        direccion.setText("");
        telefono.setText("");
        this.id = null;

        // Notifica al padre para que haga el cambio de la vista
        if (moduleFormListener != null) {
            moduleFormListener.onBack();
        }
    }

    private boolean validateForm() {
        if (
                descripcion.getText().toString().isEmpty() ||
                        telefono.getText().toString().isEmpty() ||
                        direccion.getText().toString().isEmpty()
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
        new Consultorios(getContext()).deleteBy(this.id);
        Toast.makeText(getContext(), R.string.delete_record, LENGTH_SHORT).show();
    }

    private void save() {
        // TODO: SAVE
        String respMsg = "TODO: SAVE";

        Consultorios consultorios = new Consultorios(getContext());
        consultorios.setdescripcion(descripcion.getText().toString());
        consultorios.settelefono(telefono.getText().toString());
        consultorios.setdireccion(direccion.getText().toString());

        switch (this.operaction) {

            case Constants.MODULE_OPERATION_NEW:
                consultorios.create();
                respMsg = getString(R.string.saved);
                break;

            case Constants.MODULE_OPERATION_UPDATE:
                consultorios.setId(this.id);
                consultorios.update();
                respMsg = getString(R.string.update);
                break;
        }

        Toast.makeText(getContext(), respMsg, LENGTH_SHORT).show();
    }

    private void fillForm(Parcelable data) {
        this.operaction = Constants.MODULE_OPERATION_UPDATE;

        Consultorios consultorios = (Consultorios) data;

        this.id = ((Consultorios) data).getId();
        descripcion.setText(consultorios.getdescripcion());
        telefono.setText(consultorios.gettelefono());
        direccion.setText(consultorios.getdireccion());
    }

    public void setModuleFormListener(ModuleFormListener moduleFormListener) {
        this.moduleFormListener = moduleFormListener;
    }

    @Override
    public void onBack() {
    }
}
