package com.example.task2;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;

public class AddTaskActivity extends AppCompatActivity {

    private EditText editTextTitle, editTextDescription, editTextDueDate;
    private Button buttonSave;
    private Task task;  // 可能是新任务或现有任务

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);

        editTextTitle = findViewById(R.id.editTextTitle);
        editTextDescription = findViewById(R.id.editTextDescription);
        editTextDueDate = findViewById(R.id.editTextDueDate);
        buttonSave = findViewById(R.id.buttonSave);

        int taskId = getIntent().getIntExtra("taskId", -1);
        if (taskId != -1) {
            task = TaskRoomDatabase.getDatabase(getApplicationContext()).taskDao().getTaskById(taskId);
            editTextTitle.setText(task.getTitle());
            editTextDescription.setText(task.getDescription());
            editTextDueDate.setText(task.getDueDate());
        }

        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveTask();
            }
        });
    }

    private void saveTask() {
        String title = editTextTitle.getText().toString().trim();
        String description = editTextDescription.getText().toString().trim();
        String dueDate = editTextDueDate.getText().toString().trim();

        if (task == null) {
            task = new Task(title, description, dueDate);
            TaskRoomDatabase.getDatabase(getApplicationContext()).taskDao().insert(task);
        } else {
            task.setTitle(title);
            task.setDescription(description);
            task.setDueDate(dueDate);
            TaskRoomDatabase.getDatabase(getApplicationContext()).taskDao().update(task);
        }
        finish();
    }
}
