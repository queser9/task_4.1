package com.example.task2;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity implements TaskAdapter.OnItemClickListener {
    private RecyclerView recyclerView;
    private TaskAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerViewTasks);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        adapter = new TaskAdapter(new ArrayList<>(), this);
        recyclerView.setAdapter(adapter);

        insertSampleTasks();
        loadTasks();

        findViewById(R.id.fabAddTask).setOnClickListener(v -> {
            Intent intent = new Intent(this, AddTaskActivity.class);
            startActivity(intent);
        });
    }

    private void insertSampleTasks() {
        Executors.newSingleThreadExecutor().execute(() -> {
            TaskDao taskDao = TaskRoomDatabase.getDatabase(getApplicationContext()).taskDao();

            if (taskDao.getTaskCount() == 0) {
                taskDao.insert(new Task("Task 1", "Description 1", "2023-05-10"));
                taskDao.insert(new Task("Task 2", "Description 2", "2023-05-11"));
                taskDao.insert(new Task("Task 3", "Description 3", "2023-05-12"));
            }
        });
    }

    private void loadTasks() {
        Executors.newSingleThreadExecutor().execute(() -> {
            List<Task> tasks = TaskRoomDatabase.getDatabase(getApplicationContext()).taskDao().getAllTasks();
            runOnUiThread(() -> adapter.updateTasks(tasks));
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadTasks();
    }

    @Override
    public void onItemClick(Task task) {
        Intent intent = new Intent(this, TaskDetailActivity.class);
        intent.putExtra("taskId", task.getId());
        startActivity(intent);
    }
}