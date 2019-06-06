package com.kriverdevice.gestioncitas.models;

import android.os.Parcel;
import android.os.Parcelable;

public class Medicos implements Parcelable {

    private String nombre;
    private String identificacion;
    private String profesion;
    private int id;

    public Medicos() {
    }

    public Medicos(Parcel in) {
        readFromParcel(in);
    }

    public Medicos Medicos(String nombre, String identificacion, String profesion) {
        return new Medicos(0, nombre, identificacion, profesion);
    }

    public Medicos(int id, String nombre, String identificacion, String profesion) {
        this.nombre = nombre;
        this.identificacion = identificacion;
        this.profesion = profesion;
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

    public void setProfesion(String profesion) {
        this.profesion = profesion;
    }

    public String getProfesion() {
        return profesion;
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
        dest.writeString(this.profesion);
    }

    private void readFromParcel(Parcel in) {
        this.id = in.readInt();
        this.nombre = in.readString();
        this.identificacion = in.readString();
        this.profesion = in.readString();
    }

    public static final Creator<Medicos> CREATOR = new Creator<Medicos>() {
        @Override
        public Medicos createFromParcel(Parcel in) {
            return new Medicos(in);
        }

        @Override
        public Medicos[] newArray(int size) {
            return new Medicos[size];
        }
    };
}
