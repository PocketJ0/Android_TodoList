package com.example.cesiappli;

import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.example.cesiappli.controller.TaskSqlManager;
import com.example.cesiappli.model.Task;

public class TodoListActivity extends AppCompatActivity {

    private String m_Text = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todo_list);

        //Button Listener
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab); //AddButton
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                //         .setAction("Action", null).show();

                AlertDialog.Builder builder = new AlertDialog.Builder(TodoListActivity.this);
                builder.setTitle("Nouvelle tâche");

                // Set up the input
                final EditText input = new EditText(TodoListActivity.this);
                // Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
                input.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_MULTI_LINE);
                builder.setView(input);

                // Set up the buttons
                builder.setPositiveButton("Ajouter", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        m_Text = input.getText().toString();
                        TaskSqlManager t = new TaskSqlManager(TodoListActivity.this);

                        t.open();

                        Task newTask = new Task(m_Text);
                        t.addTask(newTask);


                        // Listing des enregistrements de la table
                        Cursor c = t.getAllTasks();
                        if (c.moveToFirst())
                        {
                            do {
                                Log.d("test",
                                        c.getInt(c.getColumnIndex(TaskSqlManager.KEY_ID_TASK)) + "," +
                                                c.getString(c.getColumnIndex(TaskSqlManager.KEY_LABEL))
                                );
                            }
                            while (c.moveToNext());
                        }
                        c.close(); // fermeture du curseur
                    }
                });

                builder.setNegativeButton("Annuler", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

                builder.show();

            }
        });
        // END button listener

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

}
