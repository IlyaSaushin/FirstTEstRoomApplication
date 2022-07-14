package com.esinilyadev.roomapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import com.esinilyadev.roomapplication.databinding.ActivityMainBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var appDB: AppDataBase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater).also { setContentView(it.root) }
        appDB = AppDataBase.getDataBase(this)

        binding.btnWriteData.setOnClickListener {
            lifecycleScope.launch {
                writeData()
            }
        }
        binding.btnReadData.setOnClickListener {
            lifecycleScope.launch {
                readData()
            }
        }
        binding.btnDeleteAll.setOnClickListener {
            lifecycleScope.launch {
                appDB.studentDao().clearDataBase()
            }
        }
        binding.updateBtn.setOnClickListener {
            lifecycleScope.launch{
                updateData()
            }
        }

    }

    private suspend fun updateData() {

        val firstName = binding.etFirstName.text.toString()
        val surName = binding.etLastName.text.toString()
        val persNumber = binding.etPersonalNumber.text.toString()

        if (firstName.isNotEmpty() && surName.isNotEmpty() && persNumber.isNotEmpty()){

            GlobalScope.launch(Dispatchers.IO) {
                appDB.studentDao().updateDB(firstName, surName, persNumber.toInt())
            }

            binding.etFirstName.text.clear()
            binding.etLastName.text.clear()
            binding.etPersonalNumber.text.clear()

            Toast.makeText(this@MainActivity, "Successfully updated", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this@MainActivity, "Error", Toast.LENGTH_SHORT).show()

        }
    }

    private suspend fun writeData() {

        val firstName = binding.etFirstName.text.toString()
        val surName = binding.etLastName.text.toString()
        val persNumber = binding.etPersonalNumber.text.toString()

        if (firstName.isNotEmpty() && surName.isNotEmpty() && persNumber.isNotEmpty()){
            val newStudent = Student(0, firstName, surName, persNumber.toInt())

            GlobalScope.launch(Dispatchers.IO) {
                appDB.studentDao().insertStudent(newStudent)
            }

            binding.etFirstName.text.clear()
            binding.etLastName.text.clear()
            binding.etPersonalNumber.text.clear()

            Toast.makeText(this@MainActivity, "Successfully saved", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this@MainActivity, "Error", Toast.LENGTH_SHORT).show()

        }
    }

    private suspend fun displayData(student: Student){

        withContext(Dispatchers.Main) {
            binding.tvFirstName.text = student.name
            binding.tvLastName.text = student.surName
            binding.tvRollNo.text = student.personalNumber.toString()
        }
    }

    private suspend fun readData(){

        val persNumber = binding.etPersonalNumberSearch.text.toString()

        if (persNumber.isNotEmpty()){
            GlobalScope.launch {
                val student = appDB.studentDao().getStudentByPersNumber(persNumber.toInt())
                displayData(student)
            }
        }
    }
}