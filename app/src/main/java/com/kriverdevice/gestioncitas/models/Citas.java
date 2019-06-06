package com.kriverdevice.gestioncitas.models;

import android.os.Parcel;
import android.os.Parcelable;

public class Citas implements Parcelable {

    private int id;
    private int paciente_id;
    private int medico_id;
    private int consultorio_id;
    private String fecha, hora;

    String pacienteName, medicoName, consultorioName, consultorioPhone, consultorioAddress;
    String pacienteIdentification, medicoIdentification;

    public String getPacienteIdentification() {
        return pacienteIdentification;
    }

    public void setPacienteIdentification(String pacienteIdentification) {
        this.pacienteIdentification = pacienteIdentification;
    }

    public String getMedicoIdentification() {
        return medicoIdentification;
    }

    public void setMedicoIdentification(String medicoIdentification) {
        this.medicoIdentification = medicoIdentification;
    }

    public String getHoraFecha() {
        return fecha + " / " + hora;
    }

    public String getPacienteName() {
        return pacienteName;
    }

    public void setPacienteName(String pacienteName) {
        this.pacienteName = pacienteName;
    }

    public String getMedicoName() {
        return medicoName;
    }

    public void setMedicoName(String medicoName) {
        this.medicoName = medicoName;
    }

    public String getConsultorioName() {
        return consultorioName;
    }

    public void setConsultorioName(String consultorioName) {
        this.consultorioName = consultorioName;
    }

    public String getConsultorioPhone() {
        return consultorioPhone;
    }

    public void setConsultorioPhone(String consultorioPhone) {
        this.consultorioPhone = consultorioPhone;
    }

    public String getConsultorioAddress() {
        return consultorioAddress;
    }

    public void setConsultorioAddress(String consultorioAddress) {
        this.consultorioAddress = consultorioAddress;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPaciente_id() {
        return paciente_id;
    }

    public void setPaciente_id(int paciente_id) {
        this.paciente_id = paciente_id;
    }

    public int getMedico_id() {
        return medico_id;
    }

    public void setMedico_id(int medico_id) {
        this.medico_id = medico_id;
    }

    public int getConsultorio_id() {
        return consultorio_id;
    }

    public void setConsultorio_id(int consultorio_id) {
        this.consultorio_id = consultorio_id;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public Citas() {
    }

    public Citas(Parcel in) {
        readFromParcel(in);
    }

    public Citas Citas(int paciente_id, int consultorio_id, int medico_id, String fecha, String hora) {
        return new Citas(0, paciente_id, consultorio_id, medico_id, fecha, hora);
    }

    public Citas(int id, int paciente_id, int consultorio_id, int medico_id, String fecha, String hora) {
        this.id = id;
        this.paciente_id = paciente_id;
        this.medico_id = medico_id;
        this.consultorio_id = consultorio_id;
        this.fecha = fecha;
        this.hora = hora;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeInt(this.paciente_id);
        dest.writeInt(this.medico_id);
        dest.writeInt(this.consultorio_id);
        dest.writeString(this.fecha);
        dest.writeString(this.hora);

        dest.writeString(this.pacienteName);
        dest.writeString(this.medicoName);
        dest.writeString(this.consultorioName);
        dest.writeString(this.consultorioAddress);
        dest.writeString(this.consultorioPhone);
    }

    private void readFromParcel(Parcel in) {
        this.id = in.readInt();
        this.paciente_id = in.readInt();
        this.medico_id = in.readInt();
        this.consultorio_id = in.readInt();
        this.fecha = in.readString();
        this.hora = in.readString();

        this.pacienteName = in.readString();
        this.medicoName = in.readString();
        this.consultorioName = in.readString();
        this.consultorioAddress = in.readString();
        this.consultorioPhone = in.readString();
    }

    public static final Creator<Citas> CREATOR = new Creator<Citas>() {
        @Override
        public Citas createFromParcel(Parcel in) {
            return new Citas(in);
        }

        @Override
        public Citas[] newArray(int size) {
            return new Citas[size];
        }
    };
}
