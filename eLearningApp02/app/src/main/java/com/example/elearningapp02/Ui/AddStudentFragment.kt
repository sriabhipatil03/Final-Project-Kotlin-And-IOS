package com.example.elearningapp02.Ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.elearningapp02.Database.DatabaseHelper
import com.example.elearningapp02.R

class AddStudentFragment : Fragment() {

    private lateinit var dbHelper: DatabaseHelper
    private lateinit var etStudentName: EditText
    private lateinit var etStudentEmail: EditText
    private lateinit var etStudentPassword: EditText
    private lateinit var btnAddStudent: Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_add_student, container, false)

        dbHelper = DatabaseHelper(requireContext())
        etStudentName = view.findViewById(R.id.etStudentName)
        etStudentEmail = view.findViewById(R.id.etStudentEmail)
        etStudentPassword = view.findViewById(R.id.etStudentPassword)
        btnAddStudent = view.findViewById(R.id.btnAddStudent)

        btnAddStudent.setOnClickListener {
            addStudent()
        }

        return view
    }

    private fun addStudent() {
        val studentName = etStudentName.text.toString().trim()
        val studentEmail = etStudentEmail.text.toString().trim()
        val studentPassword = etStudentPassword.text.toString().trim()

        if (studentName.isEmpty() || studentEmail.isEmpty() || studentPassword.isEmpty()) {
            Toast.makeText(requireContext(), "Please fill in all fields", Toast.LENGTH_SHORT).show()
        } else {
            val result = dbHelper.insertStudent(studentName, studentEmail, studentPassword)
            if (result) {
                Toast.makeText(requireContext(), "Student Added Successfully", Toast.LENGTH_SHORT).show()
                parentFragmentManager.popBackStack() // Navigate back after adding
            } else {
                Toast.makeText(requireContext(), "Failed to Add Student", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
