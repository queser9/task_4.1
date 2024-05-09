package com.example.task2;

import android.os.Bundle;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;
import java.util.concurrent.Executors;

public class AddTaskActivity extends AppCompatActivity {
    private EditText editTextTitle;
    private EditText editTextDescription;
    private EditText editTextDueDate;
    private int taskId = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);

        editTextTitle = findViewById(R.id.editTextTitle);
        editTextDescription = findViewById(R.id.editTextDescription);
        editTextDueDate = findViewById(R.id.editTextDueDate);

        taskId = getIntent().getIntExtra("taskId", -1);

        if (taskId != -1) {
            loadTaskDetails(taskId);
        }

        findViewById(R.id.buttonSave).setOnClickListener(v -> saveTask());
    }

    private void loadTaskDetails(int taskId) {
        Executors.newSingleThreadExecutor().execute(() -> {
            Task task = TaskRoomDatabase.getDatabase(getApplicationContext()).taskDao().getTaskById(taskId);
            runOnUiThread(() -> {
                editTextTitle.setText(task.getTitle());
                editTextDescription.setText(task.getDescription());
                editTextDueDate.setText(task.getDueDate());
            });
        });
    }

    private void saveTask() {
        String title = editTextTitle.getText().toString();
        String description = editTextDescription.getText().toString();
        String dueDate = editTextDueDate.getText().toString();

        if (taskId != -1) {
            updateTask(taskId, title, description, dueDate);
        } else {
            insertTask(title, description, dueDate);
        }
    }

    private void insertTask(String title, String description, String dueDate) {
        Executors.newSingleThreadExecutor().execute(() -> {
            TaskRoomDatabase.getDatabase(getApplicationContext()).taskDao().insert(new Task(title, description, dueDate));
            runOnUiThread(this::finish);
        });
    }

    private void updateTask(int taskId, String title, String description, String dueDate) {
        Executors.newSingleThreadExecutor().execute(() -> {
            Task task = new Task(title, description, dueDate);
            task.setId(taskId);
            TaskRoomDatabase.getDatabase(getApplicationContext()).taskDao().update(task);
            runOnUiThread(this::finish);
        });
    }
}