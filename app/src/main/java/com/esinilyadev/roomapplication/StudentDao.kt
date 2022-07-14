package com.esinilyadev.roomapplication

import androidx.room.*


@Dao
interface StudentDao {

    @Query("SELECT * FROM Student_table")
    fun getAllStudents(): List<Student>

    @Query("SELECT * FROM Student_table WHERE personalNumber LIKE :personalNumber LIMIT 1")
    suspend fun getStudentByPersNumber(personalNumber: Int): Student

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertStudent(student: Student)

    @Delete
    suspend fun deleteStudent(student: Student)

    @Query("DELETE FROM Student_table")
    suspend fun clearDataBase()

    @Query("UPDATE student_table SET name=:firstName, surname=:surName WHERE personalNumber LIKE :persNumber")
    suspend fun updateDB(firstName: String, surName: String, persNumber: Int)
}