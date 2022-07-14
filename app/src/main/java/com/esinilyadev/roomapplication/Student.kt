package com.esinilyadev.roomapplication

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(
    tableName = "Student_table"
)
data class Student (
    @PrimaryKey(autoGenerate = true) val id: Int,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "surName") val surName: String,
    @ColumnInfo(name = "personalNumber") val personalNumber: Int
)
