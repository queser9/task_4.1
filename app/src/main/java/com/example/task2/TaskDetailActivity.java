package com.example.task2;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class TaskDetailActivity extends AppCompatActivity {

    private TextView textViewTitle, textViewDescription, textViewDueDate;
    private Button buttonEdit, buttonDelete;
    private Task task; // 假设有逻辑从数据库加载这个 Task 对象

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_detail);

        textViewTitle = findViewById(R.id.textViewDetailTitle);
        textViewDescription = findViewById(R.id.textViewDetailDescription);
        textViewDueDate = findViewById(R.id.textViewDetailDueDate);
        buttonEdit = findViewById(R.id.buttonEdit);
        buttonDelete = findViewById(R.id.buttonDelete);

        int taskId = getIntent().getIntExtra("taskId", -1);
        task = TaskRoomDatabase.getDatabase(getApplicationContext()).taskDao().getTaskById(taskId);

        initViews();

        buttonEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TaskDetailActivity.this, AddTaskActivity.class);
                intent.putExtra("taskId", task.getId());
                startActivity(intent);
            }
        });

        buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                confirmDelete();
            }
        });
    }

    private void initViews() {
        if (task != null) {
            textViewTitle.setText(task.getTitle());
            textViewDescription.setText(task.getDescription());
            textViewDueDate.setText(task.getDueDate());
        }
    }

    private void confirmDelete() {
        new AlertDialog.Builder(this)
                .setTitle("Confirm Delete")
                .setMessage("Are you sure you want to delete this task?")
                .setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        deleteTask();
                    }
                })
                .setNegativeButton("Cancel", null)
                .show();
    }

    private void deleteTask() {
        TaskRoomDatabase.getDatabase(getApplicationContext()).taskDao().delete(task);
        finish();
    }
}
