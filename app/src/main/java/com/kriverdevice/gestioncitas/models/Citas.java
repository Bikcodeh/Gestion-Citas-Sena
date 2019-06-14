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

    /*
     * Función actualizarMedico sirve para actualizar los valores de un medico,
     * tiene un parametro de tipo medico, el cual es pasado como argumento al
     * momento de llamar el la función
     */
    public int update() {
        /*
         * SQLiteDatabase db = this.getWritableDatabase(); Crea o abre una base
         * de datos que se usara para lectura y escritura usando instrucciones
         * SQL.
         */
        SQLiteDatabase db = this.getWritableDatabase();
        /*
         * valores es un objeto de la clase ContentValues, mediante el método
         * put asignamos un valor a cada campo de la tabla, los valores
         * son obtenidos del parametro tipo Paciente, los
         * valores son pasados como argumentos al momento de invocar la función
         * actualizarPaciente
         */
        ContentValues valores = new ContentValues();
        valores.put("paciente_id", this.paciente_id);
        valores.put("medico_id", this.medico_id);
        valores.put("consultorio_id", this.consultorio_id);
        valores.put("fecha", this.fecha);
        valores.put("hora", this.hora);
        /*
         * La función update sirve para actualizar registros en una tabla,
         * recibe como argumentos el nombre de la tabla, los nuevos valores, una
         * condición de actualización y opcionalmente un arreglo con valores de
         * reemplazo para la condición
         */
        //String[] strArray = { "" + id };
        int res = db.update(this.tabla, valores, "id=" + this.id, null);
        db.close(); // Cierre de la conexión
        return res; // Retorno del número de registros afectados
    }

    public int deleteBy(int id) {
        /*
         * SQLiteDatabase db = this.getWritableDatabase(); Crea o abre una base
         * de datos que se usara para lectura y escritura usando instrucciones
         * SQL.
         */
        SQLiteDatabase db = this.getWritableDatabase();
        int res = db.delete(this.tabla, "id=" + id, null);
        db.close(); // Cierre de la conexión
        return res; // Retorno del número de registros afectados
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

    public Medicos getMedico() {

        Medicos medicos = null;

        String selectQuery = "";
        selectQuery += "SELECT m.id, m.identificacion, m.nombres, m.apellidos, e.id, e.descripcion ";
        selectQuery += "FROM medicos m, especialidads e WHERE m.especialidad_id = e.id3 ";
        selectQuery += "AND m.id=" + this.medico_id;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor c = null;

        c = db.rawQuery(selectQuery, null);

        if (c.moveToFirst()) {
            medicos = new Medicos(this.context);

            medicos.setId(c.getInt(0));
            medicos.setIdentificacion(c.getString(1));
            medicos.setNombre(c.getString(2));
            medicos.setApellido(c.getString(3));
            medicos.setProfesion(c.getInt(4));
        }
        db.close(); // Cierre de la conexión
        return medicos; // Retorno del medico obtenido en la consulta
    }

    public String getHoraFecha() {
        return fecha + " / " + hora;
    }

    public Pacientes getPaciente() {
        Pacientes pacientes = null;
        /* Instrucción SQL para buscar medicos por id, tenga en cuenta el orden
         de las columnas, asi mismo tendra que acceder a sus valores */
        String selectQuery = "";
        selectQuery += "SELECT p.id, p.identificacion, p.nombres, p.apellidos";
        selectQuery += "FROM pacientes p WHERE p.id ='" + this.paciente_id + "'";

        /*
         * SQLiteDatabase db = this.getWritableDatabase(); Crea o abre una base
         * de datos que se usara para lectura y escritura usando instrucciones
         * SQL.
         */
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor c = null;
        /*
         * El método rawQuery() de la clase SQLiteDatabase, recibe como
         * argumento un comando SQL a ejecutar, retorna un Cursor
         */
        c = db.rawQuery(selectQuery, null);
        /*
         * moveToFirst(): mueve el puntero del cursor al primer registro
         * obtenido.
         */
        if (c.moveToFirst()) {
            pacientes = new Pacientes(this.context);
            pacientes.setId(c.getInt(0));
            pacientes.setIdentificacion(c.getString(1));
            pacientes.setNombre(c.getString(2));
            pacientes.setApellido(c.getString(3));
        }
        db.close(); // Cierre de la conexión
        return pacientes; // Retorno del medico obtenido en la consulta
    }


    public Consultorios getConsultorio() {

        Consultorios consultorios = null;
        /* Instrucción SQL para buscar medicos por id, tenga en cuenta el orden
         de las columnas, asi mismo tendra que acceder a sus valores */
        String selectQuery = "";
        selectQuery += "SELECT c.id, c.descripcion, c.telefono, c.direccion";
        selectQuery += "FROM consultorios c WHERE c.id ='" + this.consultorio_id + "'";

        /*
         * SQLiteDatabase db = this.getWritableDatabase(); Crea o abre una base
         * de datos que se usara para lectura y escritura usando instrucciones
         * SQL.
         */
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor c = null;
        /*
         * El método rawQuery() de la clase SQLiteDatabase, recibe como
         * argumento un comando SQL a ejecutar, retorna un Cursor
         */
        c = db.rawQuery(selectQuery, null);
        /*
         * moveToFirst(): mueve el puntero del cursor al primer registro
         * obtenido.
         */
        if (c.moveToFirst()) {
            consultorios = new Consultorios(this.context);
            consultorios.setId(c.getInt(0));
            consultorios.setdescripcion(c.getString(1));
            consultorios.settelefono(c.getString(3));
            consultorios.setdescripcion(c.getString(2));
        }
        db.close(); // Cierre de la conexión
        return consultorios; // Retorno del medico obtenido en la consulta
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
