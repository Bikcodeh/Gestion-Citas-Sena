package com.kriverdevice.gestioncitas.modules.citas;


import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatSpinner;
import android.support.v7.widget.AppCompatTextView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.kriverdevice.gestioncitas.Constants;
import com.kriverdevice.gestioncitas.R;
import com.kriverdevice.gestioncitas.interfaces.ModuleFormListener;
import com.kriverdevice.gestioncitas.models.Citas;
import com.kriverdevice.gestioncitas.models.Consultorios;
import com.kriverdevice.gestioncitas.models.Medicos;
import com.kriverdevice.gestioncitas.models.Pacientes;

import static android.widget.Toast.LENGTH_SHORT;
import static com.kriverdevice.gestioncitas.R.layout.support_simple_spinner_dropdown_item;

public class CitasForm extends Fragment implements ModuleFormListener {

    ModuleFormListener moduleFormListener;

    private String operaction = null;
    Integer id = null;

    AppCompatEditText fecha, hora;
    TextView pacienteName, medicoName;
    AppCompatTextView profesiones;
    AppCompatSpinner consultorios, pacienteId, medicoId;

    Consultorios consultorio;
    Medicos medico;
    Pacientes paciente;

    Consultorios consultorioSelected;
    Medicos medicoSelected;
    Pacientes pacienteSelected;

    ArrayAdapter<Medicos> medicosList;
    ArrayAdapter<Pacientes> pacientesList;
    ArrayAdapter<Consultorios> consultoriosList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.form_citas, container, false);
        pacienteId = v.findViewById(R.id.form_citas_paciente_identificacion);
        medicoId = v.findViewById(R.id.form_citas_medico_identificacion);
        fecha = v.findViewById(R.id.form_citas_fecha);
        hora = v.findViewById(R.id.form_citas_hora);

        pacienteName = v.findViewById(R.id.form_citas_paciente_name);
        medicoName = v.findViewById(R.id.form_citas_medico_name);
        profesiones = v.findViewById(R.id.form_citas_medico_profesion);
        consultorios = v.findViewById(R.id.form_citas_consultorio_name);
        return v;
    }

    @Override
    public void onResume() {
        super.onResume();

        consultorio = new Consultorios(getContext());
        paciente = new Pacientes(getContext());
        medico = new Medicos(getContext());

        medicosList = new ArrayAdapter<Medicos>(
                this.getContext(),
                support_simple_spinner_dropdown_item,
                medico.getAllMedicos()
        );

        pacientesList = new ArrayAdapter<Pacientes>(
                this.getContext(),
                support_simple_spinner_dropdown_item,
                paciente.getAllPacientes()
        );

        consultoriosList = new ArrayAdapter<Consultorios>(
                this.getContext(),
                support_simple_spinner_dropdown_item,
                consultorio.getAllConsultorios()
        );

        medicoId.setAdapter(medicosList);
        pacienteId.setAdapter(pacientesList);
        consultorios.setAdapter(consultoriosList);

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
            pacienteId.setSelection(0);
            medicoId.setSelection(0);
            consultorios.setSelection(0);

            pacienteSelected = ((Pacientes) pacienteId.getSelectedItem());
            medicoSelected = ((Medicos) medicoId.getSelectedItem());
            consultorioSelected = ((Consultorios) consultorios.getSelectedItem());

            pacienteName.setText(pacienteSelected.getNombre() + " " + pacienteSelected.getApellido());
            medicoName.setText(medicoSelected.getNombre() + " " + medicoSelected.getApellido());

            fecha.setText("");
            hora.setText("");

            // Establece el flag de la operacion que se realiza
            this.operaction = Constants.MODULE_OPERATION_NEW;
        }

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

        id = null;
        pacienteId.setSelection(0);
        medicoId.setSelection(0);
        consultorios.setSelection(0);

        pacienteSelected = ((Pacientes) pacienteId.getSelectedItem());
        medicoSelected = ((Medicos) medicoId.getSelectedItem());
        consultorioSelected = ((Consultorios) consultorios.getSelectedItem());

        pacienteName.setText(pacienteSelected.getNombre() + " " + pacienteSelected.getApellido());
        medicoName.setText(medicoSelected.getNombre() + " " + medicoSelected.getApellido());

        fecha.setText("");
        hora.setText("");

        // Notifica al padre para que haga el cambio de la vista
        if (moduleFormListener != null) {
            moduleFormListener.onBack();
        }
    }

    private boolean validateForm() {
        if (
                fecha.getText().toString().isEmpty() ||
                        hora.getText().toString().isEmpty() ||
                        pacienteName.getText().toString().isEmpty() ||
                        medicoName.getText().toString().isEmpty()
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

    private void save() {
        // TODO: SAVE
        String respMsg = "TODO: SAVE";

        Citas citas = new Citas(getContext());
        Consultorios consultorioId = consultorio.getByDescripcion(consultorios.getSelectedItem().toString());

        /*
        citas.setConsultorio_id( consultorioId.getId() );
        citas.setFecha( fecha.getText().toString() );
        citas.setHora( hora.getText().toString() );
        citas.setMedico_id( medico.getByIdentificacion( medicoId.getText().toString() ).getId() );
        */

        switch (this.operaction) {

            case Constants.MODULE_OPERATION_NEW:
                citas.create();
                respMsg = getString(R.string.saved);
                break;

            case Constants.MODULE_OPERATION_UPDATE:
                consultorios.setId(this.id);
                citas.update();
                respMsg = getString(R.string.update);
                break;
        }

        Toast.makeText(getContext(), respMsg, LENGTH_SHORT).show();
    }

    private void delete() {
        new Citas(getContext()).deleteBy(this.id);
        Toast.makeText(getContext(), R.string.delete_record, LENGTH_SHORT).show();
    }

    private void fillForm(Parcelable data) {

        Citas cita = (Citas) data;
        Medicos medicoInfo = cita.getMedico();
        Pacientes pacienteInfo = cita.getPaciente();

        fecha.setText(cita.getFecha());
        hora.setText(cita.getHora());
        pacienteName.setText(pacienteInfo.getNombre());
        medicoName.setText(medicoInfo.getNombre() + " " + medicoInfo.getApellido());
        profesiones.setText("Profesion");

        // TODO: Poblar el combo y buscar la profesion.
        // consultorios.setSelection(0);
        // pacienteId.setText(pacienteInfo.getIdentificacion());
        // medicoId.setText(medicoInfo.getIdentificacion());

    }

    public void setModuleFormListener(ModuleFormListener moduleFormListener) {
        this.moduleFormListener = moduleFormListener;
    }

    @Override
    public void onBack() {
    }

}
