package com.kriverdevice.gestioncitas.models;

import android.os.Parcel;
import android.os.Parcelable;

public class Pacientes implements Parcelable {

    private String nombre;
    private String identificacion;
    private String apellido;
    private int id;

    public Pacientes() {
    }

    public Pacientes(Parcel in) {
        readFromParcel(in);
    }

    public Pacientes Pacientes(String nombre, String apellido, String identificacion) {
        return new Pacientes(0, nombre, apellido, identificacion);
    }

    public Pacientes(int id, String nombre, String apellido, String identificacion) {
        this.nombre = nombre;
        this.identificacion = identificacion;
        this.apellido = apellido;
        this.id = id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getNombre() {
        return nombre;
    }

    public void setIdentificacion(String identificacion) {
        this.identificacion = identificacion;
    }

    public String getIdentificacion() {
        return identificacion;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getApellido() {
        return apellido;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.nombre);
        dest.writeString(this.identificacion);
        dest.writeString(this.apellido);
    }

    private void readFromParcel(Parcel in) {
        this.id = in.readInt();
        this.nombre = in.readString();
        this.identificacion = in.readString();
        this.apellido = in.readString();
    }

    public static final Creator<Pacientes> CREATOR = new Creator<Pacientes>() {
        @Override
        public Pacientes createFromParcel(Parcel in) {
            return new Pacientes(in);
        }

        @Override
        public Pacientes[] newArray(int size) {
            return new Pacientes[size];
        }
    };
}
