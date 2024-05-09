package com.example.task2;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.concurrent.Executors;

public class TaskDetailActivity extends AppCompatActivity {
    private TextView textViewDetailTitle;
    private TextView textViewDetailDescription;
    private TextView textViewDetailDueDate;
    private int taskId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_detail);

        textViewDetailTitle = findViewById(R.id.textViewDetailTitle);
        textViewDetailDescription = findViewById(R.id.textViewDetailDescription);
        textViewDetailDueDate = findViewById(R.id.textViewDetailDueDate);

        taskId = getIntent().getIntExtra("taskId", -1);

        findViewById(R.id.buttonEdit).setOnClickListener(v -> {
            Intent intent = new Intent(this, AddTaskActivity.class);
            intent.putExtra("taskId", taskId);
            startActivity(intent);
        });

        findViewById(R.id.buttonDelete).setOnClickListener(v -> deleteTask(taskId));
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadTaskDetails(taskId);
    }

    private void loadTaskDetails(int taskId) {
        Executors.newSingleThreadExecutor().execute(() -> {
            Task task = TaskRoomDatabase.getDatabase(getApplicationContext()).taskDao().getTaskById(taskId);
            runOnUiThread(() -> {
                textViewDetailTitle.setText(task.getTitle());
                textViewDetailDescription.setText(task.getDescription());
                textViewDetailDueDate.setText("Due Date: " + task.getDueDate());
            });
        });
    }

    private void deleteTask(int taskId) {
        Executors.newSingleThreadExecutor().execute(() -> {
            TaskRoomDatabase.getDatabase(getApplicationContext()).taskDao().deleteById(taskId);
            runOnUiThread(this::finish);
        });
    }
}