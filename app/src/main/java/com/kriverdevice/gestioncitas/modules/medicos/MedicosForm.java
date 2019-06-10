package com.kriverdevice.gestioncitas.modules.medicos;


import android.os.Bundle;
import android.os.Parcelable;
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

import static android.widget.Toast.LENGTH_SHORT;
import static com.kriverdevice.gestioncitas.R.layout.support_simple_spinner_dropdown_item;

public class MedicosForm extends Fragment implements ModuleFormListener {

    ModuleFormListener moduleFormListener;

    private String operaction = null;
    Integer id = null;
    AppCompatEditText
            nombre,
            apellido,
            identificacion;

    Spinner profession;

    ArrayList<String> especialidades;
    ArrayAdapter<String> professions;

    View v;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        v = inflater.inflate(R.layout.form_medicos, container, false);

        nombre = v.findViewById(R.id.form_medico_nomnbre);
        apellido = v.findViewById(R.id.form_medico_apellido);
        identificacion = v.findViewById(R.id.form_medico_identificacion);
        profession = v.findViewById(R.id.form_medico_profession);

        return v;
    }

    @Override
    public void onResume() {
        super.onResume();

        especialidades = new Especialidades(this.getContext()).getEspecialidades();

        professions = new ArrayAdapter<String>(
                this.getContext(),
                support_simple_spinner_dropdown_item,
                especialidades
        );

        profession.setAdapter(professions);

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
            // Establece el flag de la operacion que se realiza
            this.operaction = Constants.MODULE_OPERATION_NEW;
            nombre.setText("");
            apellido.setText("");
            profession.setSelection(0);
            this.id = null;
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
        profession.setSelection(0);
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
        new Medicos(getContext()).deleteBy(this.id);
        Toast.makeText(getContext(), R.string.delete_record, LENGTH_SHORT).show();
    }

    private void save() {

        String respMsg = "";

        Medicos medicos = new Medicos(getContext());
        medicos.setIdentificacion(identificacion.getText().toString());
        medicos.setNombre(nombre.getText().toString());
        medicos.setApellido(apellido.getText().toString());
        medicos.setProfesion(profession.getSelectedItem().toString());

        switch (this.operaction) {

            case Constants.MODULE_OPERATION_NEW:
                medicos.create();
                respMsg = getString(R.string.saved);
                break;

            case Constants.MODULE_OPERATION_UPDATE:
                medicos.setId(this.id);
                medicos.update();
                respMsg = getString(R.string.update);
                break;
        }

        Toast.makeText(getContext(), respMsg, LENGTH_SHORT).show();
    }

    // Llena el formulario
    private void fillForm(Parcelable data) {

        this.operaction = Constants.MODULE_OPERATION_UPDATE;

        Medicos medico = (Medicos) data;

        this.id = ((Medicos) data).getId();
        nombre.setText(medico.getNombre());
        apellido.setText(medico.getApellido());
        identificacion.setText(medico.getIdentificacion());

        int idProfession = especialidades.indexOf(medico.getProfesion());
        profession.setSelection(idProfession);
    }

    public void setModuleFormListener(ModuleFormListener moduleFormListener) {
        this.moduleFormListener = moduleFormListener;
    }

    @Override
    public void onBack() {
    }

}
