package com.example.elearningapp02.Ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.elearningapp02.Database.DatabaseHelper
import com.example.elearningapp02.R

class UpdateStudentFragment : Fragment() {

    private lateinit var dbHelper: DatabaseHelper
    private lateinit var etStudentName: EditText
    private lateinit var etStudentEmail: EditText
    private lateinit var etStudentPassword: EditText
    private lateinit var btnUpdateStudent: Button

    private val args: UpdateStudentFragmentArgs by navArgs() // Retrieve studentId from navigation args
    private var studentId: Int = -1

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_update_student, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        dbHelper = DatabaseHelper(requireContext())

        etStudentName = view.findViewById(R.id.etUpdateStudentName)
        etStudentEmail = view.findViewById(R.id.etUpdateStudentEmail)
        etStudentPassword = view.findViewById(R.id.etUpdateStudentPassword)
        btnUpdateStudent = view.findViewById(R.id.btnUpdateStudent)

        // Safely retrieve studentId from arguments
        studentId = args.studentId

        if (studentId != -1) {
            loadStudentData(studentId)
        } else {
            Toast.makeText(requireContext(), "Invalid student ID", Toast.LENGTH_SHORT).show()
        }

        btnUpdateStudent.setOnClickListener {
            val studentName = etStudentName.text.toString().trim()
            val studentEmail = etStudentEmail.text.toString().trim()
            val studentPassword = etStudentPassword.text.toString().trim()

            if (studentName.isEmpty() || studentEmail.isEmpty() || studentPassword.isEmpty()) {
                Toast.makeText(requireContext(), "Please fill in all fields", Toast.LENGTH_SHORT).show()
            } else {
                val result = dbHelper.updateStudent(studentId, studentName, studentEmail, studentPassword)
                if (result) {
                    Toast.makeText(requireContext(), "Student Updated Successfully", Toast.LENGTH_SHORT).show()
                    findNavController().popBackStack() // Navigate back after update
                } else {
                    Toast.makeText(requireContext(), "Failed to Update Student", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun loadStudentData(studentId: Int) {
        val student = dbHelper.getStudentById(studentId)
        student?.let {
            etStudentName.setText(it.name)
            etStudentEmail.setText(it.email)
            etStudentPassword.setText(it.password)
        }
    }
}


