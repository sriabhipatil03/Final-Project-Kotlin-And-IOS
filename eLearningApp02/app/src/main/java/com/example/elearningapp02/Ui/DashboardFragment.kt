package com.example.elearningapp02.Ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.elearningapp02.R

class DashboardFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_dashboard, container, false)

        val email = arguments?.getString("email") ?: "To Dashboard"

        val welcomeText = view.findViewById<TextView>(R.id.textWelcome)
        val btnAddCourse = view.findViewById<Button>(R.id.btnAddCourse)
        val btnManageCourse = view.findViewById<Button>(R.id.btnManageCourse)
        val btnAddStudent = view.findViewById<Button>(R.id.btnAddStudent)
        val btnManageStudent = view.findViewById<Button>(R.id.btnManageStudent)
        val btnAddExam = view.findViewById<Button>(R.id.btnAddExam)
        val btnLogout = view.findViewById<Button>(R.id.btnLogout)

        welcomeText.text = "Welcome $email"

        btnAddCourse.setOnClickListener {
            findNavController().navigate(R.id.action_dashboardFragment_to_addCourseFragment)
        }

        btnManageCourse.setOnClickListener {
            findNavController().navigate(R.id.action_dashboardFragment_to_manageCourseFragment)
        }

        btnAddStudent.setOnClickListener {
            findNavController().navigate(R.id.action_dashboardFragment_to_addStudentFragment)
        }

        btnManageStudent.setOnClickListener {
            findNavController().navigate(R.id.action_dashboardFragment_to_manageStudentFragment)
        }

        btnAddExam.setOnClickListener {
            findNavController().navigate(R.id.action_dashboardFragment_to_addExamFragment)
        }

        btnLogout.setOnClickListener {
            findNavController().navigate(R.id.action_dashboardFragment_to_loginFragment)
        }

        return view
    }
}
