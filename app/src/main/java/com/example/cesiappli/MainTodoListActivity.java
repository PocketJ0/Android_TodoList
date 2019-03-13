package com.example.cesiappli;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.cesiappli.controller.TaskSqlManager;
import com.example.cesiappli.model.Task;

public class MainTodoListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_todo_list);

        Button btnList = (Button)findViewById(R.id.accessList_Btn1);
        btnList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goToList_Inte = new Intent(MainTodoListActivity.this, TodoListActivity.class);
                startActivity(goToList_Inte);
            }
        });

        TaskSqlManager t = new TaskSqlManager(this);

        t.open();

     //   Task TATACHE = new Task(0,"WESH");
   //     t.addTask(TATACHE);


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
}
