package com.kriverdevice.gestioncitas.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "GestionCitasMedicas";
    private static final int DATABASE_VERSION = 1;

    public DatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME,null, DATABASE_VERSION, null);
    }

    //Instancia de la clase SQLiteDatabase
    protected SQLiteDatabase db;

    @Override
    public void onCreate(SQLiteDatabase db) {
        /* Recuerde que usando SQL, primero se deben crear las tablas que no tienen llaves foraneas,bpara evitar errores de referencia
        integral */
        String sql = "";
        sql += "CREATE TABLE especialidads";
        sql += "(";
        sql += "id INTEGER PRIMARY KEY AUTOINCREMENT,";
        sql += "descripcion TEXT";
        sql += ");";
        //Ejecucicón de la instrucción SQL anterior
        db.execSQL(sql);

        sql = "";
        sql += "CREATE TABLE consultorios";
        sql += "(";
        sql += "id INTEGER PRIMARY KEY AUTOINCREMENT,";
        sql += "descripcion TEXT,";
        sql += "direccion TEXT,";
        sql += "telefono TEXT";
        sql += ");";
        //Ejecucicón de la instrucción SQL anterior
        db.execSQL(sql);

        sql = "";
        sql += "CREATE TABLE pacientes";
        sql += "(";
        sql += "id INTEGER PRIMARY KEY AUTOINCREMENT,";
        sql += "identificacion TEXT UNIQUE,";
        sql += "nombres TEXT,";
        sql += "apellidos TEXT";
        sql += ");";
        //Ejecucicón de la instrucción SQL anterior
        db.execSQL(sql);

        sql = "";
        sql += "CREATE TABLE medicos";
        sql += "(";
        sql += "id INTEGER PRIMARY KEY AUTOINCREMENT,";
        sql += "identificacion TEXT UNIQUE,";
        sql += "nombres TEXT,";
        sql += "apellidos TEXT,";
        sql += "especialidad_id INTEGER,";
        sql += "FOREIGN KEY(especialidad_id) REFERENCES especialidads(id)";
        sql += ");";
        //Ejecucicón de la instrucción SQL anterior
        db.execSQL(sql);

        sql = "";
        sql += "CREATE TABLE citas";
        sql += "(";
        sql += "id INTEGER PRIMARY KEY AUTOINCREMENT,";
        sql += "paciente_id INTEGER,";
        sql += "medico_id INTEGER,";
        sql += "consultorio_id INTEGER,";
        sql += "fecha TEXT,";
        sql += "hora TEXT,";
        sql += "FOREIGN KEY(paciente_id) REFERENCES pacientes(id)";
        sql += "FOREIGN KEY(medico_id) REFERENCES medicos(id)";
        sql += "FOREIGN KEY(consultorio_id) REFERENCES consultorios(id)";
        sql += ");";
        //Ejecucicón de la instrucción SQL anterior
        db.execSQL(sql);

        /* Inserción de algunas especialidades al momento de crear la bd*/
        sql = "";
        sql += "INSERT INTO especialidads(descripcion) ";
        sql += "VALUES";
        sql += "('Médico general'),";
        sql += "('Odontólogo'),";
        sql += "('Ginecologo'),";
        sql += "('Fisioterapeuta');";
        //Ejecucicón de la instrucción SQL anterior
        db.execSQL(sql);

        /* Inserción de algunos consultorios al momento de crear la bd*/
        sql = "";
        sql += "INSERT INTO consultorios(descripcion, direccion, telefono) ";
        sql += "VALUES";
        sql += "('Consultorio 1', 'Calle 34 # 18 - 51','76767673'),";
        sql += "('Consultorio 2', 'Calle 24 # 98 - 90','34343434'),";
        sql += "('Consultorio 3', 'Calle 64 # 38 - 82','99785565'),";
        sql += "('Consultorio 4', 'Calle 84 # 18 – 90','34343444');";
        //Ejecucicón de la instrucción SQL anterior
        db.execSQL(sql);
        //Mensaje que indica que la base de datos, fue creada correctamente
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //Borrar la tabla especialidads
        String sql = "DROP TABLE IF EXISTS especialidads;";
        db.execSQL(sql);
        sql = "";

        //Borrar la tabla consultorios
        sql += "DROP TABLE IF EXISTS consultorios;";
        db.execSQL(sql);
        sql = "";

        //Borrar la tabla pacientes
        sql += "DROP TABLE IF EXISTS pacientes;";
        db.execSQL(sql);
        sql = "";

        //Borrar la tabla medicos
        sql += "DROP TABLE IF EXISTS medicos;";
        db.execSQL(sql);
        sql = "";

        //Borrar la tabla citas
        sql += "DROP TABLE IF EXISTS citas;";
        db.execSQL(sql);
        sql = "";
        onCreate(db);
    }

    //Function insertarSql sirve para ejecutar instrucciones SQL (insert)
    private boolean insertarSql(String sql) {
        this.db = this.getWritableDatabase();
        db.execSQL(sql);
        db.close();
        return true;
    }

    /* función insertarRegistro tiene dos parametros ContentValues valores, String tabla,
     * el primero representa los valores que seran insertados,
     * el segundo corresponde a la tabla en la cual se realizara la insercción */
    protected long insertarRegistro(ContentValues valores, String tabla) {
        SQLiteDatabase db = this.getWritableDatabase();
        long res = db.insert(tabla, null, valores);
        db.close();
        return res;
    }
    /*getHighestID obtiene el ultimo id despues de una insercción SQL */
    public int getHighestID() {
        final String MY_QUERY = "SELECT last_insert_rowid()";
        Cursor cur = db.rawQuery(MY_QUERY, null);
        cur.moveToFirst();
        int ID = cur.getInt(0);
        cur.close();
        db.close();
        return ID;
    }

}
