package com.kriverdevice.gestioncitas.models;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Parcel;
import android.os.Parcelable;

import com.kriverdevice.gestioncitas.database.DatabaseHelper;

import java.util.ArrayList;

public class Citas extends DatabaseHelper implements Parcelable {

    Context context;
    private int id;
    private int paciente_id;
    private int medico_id;
    private int consultorio_id;
    private String fecha, hora;

    String pacienteName, medicoName, consultorioName, consultorioPhone, consultorioAddress;
    String pacienteIdentification, medicoIdentification;

    //Nombre de la tabla MySQL
    private String tabla = "citas";

    /*
        PERSISTENCIA EN LA BASE DE DATOS

     */

    /*
     * Función para insertar medicos, al momento de llamar la función se debe
     * pasar como argumento un objeto de tipo Medico
     */
    public long create() {
        /*
         * Valores es un objeto de la clase ContentValues, mediante el método
         * put asignamos un valor a cada campo de la tabla, los valores son
         * obtenidos de un objeto tipo medico el cual es pasado como argumento
         * al momento de llamar la función insertarMedico(Medico medico) desde una Activity"
         */
        ContentValues valores = new ContentValues();
        valores.put("paciente_id", this.paciente_id);
        valores.put("medico_id", this.medico_id);
        valores.put("consultorio_id", this.consultorio_id);
        valores.put("fecha", this.fecha);
        valores.put("hora", this.hora);
        /*
         * invocación de la función insertarRegistro a la cual le pasan
         * dos argumentos, el objeto valores y "medicos" que corresponde al
         * Nombre de la tabla en SQLite
         */
        return this.insertarRegistro(valores, tabla);
    }

    public ArrayList<Citas> getAllCitas() {
        /* lstPaciente ArrayList para almacenar todos los medicos obtenidos en la busqueda*/
        ArrayList<Citas> lstCitas = new ArrayList<Citas>();
        /* Instrucción SQL para obtener todos los medicos*/
        String selectQuery = "";
        selectQuery += "SELECT c.id, c.paciente_id, c.medico_id, c.consultorio_id, c.fecha, c.hora ";
        selectQuery += "FROM citas c";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        if (c.moveToFirst()) { /* moveToFirst() devuelve TRUE en caso de haber realizado el movimiento */
            do {
                /* Creación de un objeto de la clase Medico */
                Citas citas = new Citas(this.context);

                citas.id = c.getInt(0);
                citas.paciente_id = c.getInt(1);
                citas.medico_id = c.getInt(2);
                citas.consultorio_id = c.getInt(3);
                citas.fecha = c.getString(4);
                citas.hora = c.getString(5);

                lstCitas.add(citas);
            } while (c.moveToNext()); //Mover el cursor a la siguiente posición
        }
        db.close(); //Cierre de la conexión
        return lstCitas; //Retorno del listado de medicos
    }


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

    public Citas(Context context) {
        super(context);
        this.context = context;
    }

    public Citas(Parcel in) {
        super(null);
        readFromParcel(in);
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
