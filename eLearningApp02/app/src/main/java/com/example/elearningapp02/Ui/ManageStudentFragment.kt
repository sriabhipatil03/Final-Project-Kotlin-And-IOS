package com.example.elearningapp02.Ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.elearningapp02.Adapter.StudentAdapter
import com.example.elearningapp02.Data.Student
import com.example.elearningapp02.Database.DatabaseHelper
import com.example.elearningapp02.R
import com.google.android.material.floatingactionbutton.FloatingActionButton

class ManageStudentFragment : Fragment() {

    private lateinit var dbHelper: DatabaseHelper
    private lateinit var recyclerView: RecyclerView
    private lateinit var studentAdapter: StudentAdapter
    private lateinit var btnAddStudent: FloatingActionButton
    private val studentsList = mutableListOf<Student>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_manage_student, container, false)

        dbHelper = DatabaseHelper(requireContext())
        recyclerView = view.findViewById(R.id.recyclerViewStudents)

        // Set up RecyclerView adapter
        studentAdapter = StudentAdapter(studentsList, { student ->
            showUpdateDialog(student) // Update action
        }, { student ->
            deleteStudent(student) // Delete action
        })

        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = studentAdapter

        // Load students when fragment is created
        loadStudents()



        return view
    }

    private fun loadStudents() {
        studentsList.clear()
        val students = dbHelper.getAllStudents()

        if (students.isNotEmpty()) {
            studentsList.addAll(students)
            studentAdapter.notifyDataSetChanged()
        } else {
            Toast.makeText(requireContext(), "No students found", Toast.LENGTH_SHORT).show()
        }
    }

    private fun showUpdateDialog(student: Student) {
        if (student.id != -1) {  // Check if the student ID is valid
            val bundle = Bundle().apply {
                putInt("student_id", student.id)
                putString("student_name", student.name)
                putString("student_email", student.email)
            }
            findNavController().navigate(R.id.action_manageStudentFragment_to_updateStudentFragment, bundle)
        } else {
            Toast.makeText(requireContext(), "Invalid student ID", Toast.LENGTH_SHORT).show()
        }
    }


    private fun deleteStudent(student: Student) {
        AlertDialog.Builder(requireContext())
            .setMessage("Are you sure you want to delete this student?")
            .setPositiveButton("Yes") { _, _ ->
                if (dbHelper.deleteStudent(student.id)) {
                    studentsList.remove(student)
                    studentAdapter.notifyDataSetChanged()
                    Toast.makeText(requireContext(), "Student deleted successfully", Toast.LENGTH_SHORT).show()
                }
            }
            .setNegativeButton("No", null)
            .show()
    }
}
