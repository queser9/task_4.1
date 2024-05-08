package com.example.task2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.ViewHolder> {
    private List<Task> taskList;
    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(Task task);
    }

    public TaskAdapter(List<Task> taskList, OnItemClickListener listener) {
        this.taskList = taskList;
        this.listener = listener;
    }
    public void updateTasks(List<Task> newTasks) {
        taskList.clear();
        taskList.addAll(newTasks);
        notifyDataSetChanged(); // 通知数据变化，刷新视图
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.task_list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Task task = taskList.get(position);
        holder.textViewTaskTitle.setText(task.getTitle());
        holder.textViewTaskDescription.setText(task.getDescription()); // 显示描述
        holder.textViewTaskDueDate.setText("Due Date: " + task.getDueDate());
        holder.itemView.setOnClickListener(v -> listener.onItemClick(task));
    }

    @Override
    public int getItemCount() {
        return taskList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView textViewTaskTitle;
        public TextView textViewTaskDescription; // 添加描述 TextView
        public TextView textViewTaskDueDate;

        public ViewHolder(View itemView) {
            super(itemView);
            textViewTaskTitle = itemView.findViewById(R.id.textViewTaskTitle);
            textViewTaskDescription = itemView.findViewById(R.id.textViewTaskDescription); // 绑定描述视图
            textViewTaskDueDate = itemView.findViewById(R.id.textViewTaskDueDate);
        }
    }
}



