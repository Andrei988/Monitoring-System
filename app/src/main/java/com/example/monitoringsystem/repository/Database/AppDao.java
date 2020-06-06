package com.example.monitoringsystem.repository.Database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface AppDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void setPreference(Preferences pref);

    @Insert
    void insertNotification(Notification notification);

    @Insert
    void  insertReport(Report report);

    @Query("SELECT * FROM preferences_table")
    Preferences getPreferences();

    @Query("SELECT * FROM notifications_table")
    List<Notification> getNotifications();

    @Query("SELECT * FROM report_table")
    List<Report> getReports();

    @Delete
    void removeNotification(Notification notification);

    @Delete
    void removeReport(Report report);
}
