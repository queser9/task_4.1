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
    long insert(Task task); // 可以选择使用 REPLACE 策略来处理插入冲突

    @Update
    int update(Task task); // 返回更新的行数

    @Delete
    int delete(Task task); // 返回删除的行数

    @Query("SELECT * FROM task_table ORDER BY dueDate ASC")
    List<Task> getAllTasks();

    @Query("SELECT * FROM task_table WHERE id = :taskId")
    Task getTaskById(int taskId);
}
