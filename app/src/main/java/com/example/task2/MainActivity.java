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

        // 初始化适配器并设置，避免 'No adapter attached; skipping layout'
        adapter = new TaskAdapter(new ArrayList<>(), this);
        recyclerView.setAdapter(adapter);

        loadTasks();
    }

    private void loadTasks() {
        Executors.newSingleThreadExecutor().execute(() -> {
            List<Task> tasks = TaskRoomDatabase.getDatabase(getApplicationContext()).taskDao().getAllTasks();
            runOnUiThread(() -> {
                // 使用 updateTasks 方法更新数据
                adapter.updateTasks(tasks);
            });
        });
    }

    @Override
    public void onItemClick(Task task) {
        Intent intent = new Intent(this, TaskDetailActivity.class);
        intent.putExtra("taskId", task.getId());
        startActivity(intent);
    }
}
