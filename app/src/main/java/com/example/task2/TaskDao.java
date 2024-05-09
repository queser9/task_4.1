package com.example.task2;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;
import java.util.List;

@Dao
public interface TaskDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insert(Task task);

    @Update
    int update(Task task);

    @Delete
    int delete(Task task);

    @Query("SELECT * FROM task_table ORDER BY dueDate ASC")
    List<Task> getAllTasks();

    @Query("SELECT * FROM task_table WHERE id = :taskId")
    Task getTaskById(int taskId);

    @Query("SELECT COUNT(*) FROM task_table")
    int getTaskCount();

    @Query("DELETE FROM task_table WHERE id = :taskId")
    void deleteById(int taskId);
}