package com.example.cesiappli.controller;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.content.Context;

import com.example.cesiappli.model.Task;

/* https://fr.jeffprod.com/blog/2015/utilisation-d-une-base-sqlite-sous-android/ */

public class TaskSqlManager {
    private static final String TABLE_NAME = "task";
    public static final String KEY_ID_TASK = "id_task";
    public static final String KEY_LABEL = "label";
    public static final String KEY_ISDONE = "isDone";

    public static final String CREATE_TABLE_TASK = "CREATE TABLE IF NOT EXISTS "+ TABLE_NAME +
            " ("+
            " " + KEY_ID_TASK+" INTEGER PRIMARY KEY AUTOINCREMENT,"+
            " " + KEY_LABEL + " TEXT," +
            " " + KEY_ISDONE + " BOOLEAN" +
            ");";
    private MySQLite maBaseSQLite; // Gestionnaire de fichier SQL
    private SQLiteDatabase db;


    public TaskSqlManager(Context context)
    {
        maBaseSQLite = MySQLite.getInstance(context);
    }

    public void open()
    {
        //on ouvre la table en lecture/écriture
        db = maBaseSQLite.getWritableDatabase();
    }

    public void close()
    {
        //on ferme l'accès à la BDD
        db.close();
    }

    public long addTask(Task task) {
        //Ajout d'un enregistrement dans la table

        ContentValues values = new ContentValues();
        //values.put(KEY_ID_TASK,task.getId()); // AutoIncrement sur l'ID
        values.put(KEY_LABEL,task.getLabel());
        values.put(KEY_ISDONE,task.isDone());

        //insert() retourn l'id du nouvel enregistrement inséré ou -1 en cas d'erreur
        return db.insert(TABLE_NAME,null,values);
    }

    public int modifyTask(Task task) {
        //Modification de l'enregistrement
        //Valeur de retour : (int) nombre de ligne affectées par la rêquete

        ContentValues values = new ContentValues();
        values.put(KEY_LABEL, task.getLabel());
        values.put(KEY_ISDONE, task.isDone());

        String where = KEY_ID_TASK+" = ?";
        String[] whereArgs = {task.getId()+""};

        return db.update(TABLE_NAME,values,where,whereArgs);
    }

    public int deleteTask(Task task){
        //suppression d'un enregistrement
        //valeur de retour : (int) nb suppression

        String where = KEY_ID_TASK+" = ?";
        String[] whereArgs = {task.getId()+""};

        return db.delete(TABLE_NAME,where,whereArgs);
    }

    public Task getTask(int id){
        //Task t = new Task(0,"");
        Task t = new Task("");

        /*
        Cursor est un tableau à 2 dimensions
        When you invokes moveToFirst() method on the Cursor, it takes the cursor pointer to the first location
        */
        Cursor c = db.rawQuery("SELECT * FROM " + TABLE_NAME+" WHERE "+KEY_ID_TASK+ "=" + id,null);
        if(c.moveToFirst()) {
            t.setId(c.getInt(c.getColumnIndex(KEY_ID_TASK)));
            t.setLabel(c.getString(c.getColumnIndex(KEY_LABEL)));
            t.setDone(Boolean.parseBoolean(c.getString(c.getColumnIndex(KEY_ISDONE))));
            c.close();
        }
        return t;
    }

    public Cursor getAllTasks() {
        //Sélection tous les enregistrements de la table
        return db.rawQuery("SELECT * FROM " + TABLE_NAME,null);
    }
}
