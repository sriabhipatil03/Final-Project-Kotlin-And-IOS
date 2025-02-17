package com.example.elearningapp02.Ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.elearningapp02.Database.DatabaseHelper
import com.example.elearningapp02.R

class LoginFragment : Fragment() {

    private lateinit var dbHelper: DatabaseHelper

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_login, container, false)

        dbHelper = DatabaseHelper(requireContext())

        val editEmail = view.findViewById<EditText>(R.id.etAdminEmail)
        val editPassword = view.findViewById<EditText>(R.id.etAdminPassword)
        val btnLogin = view.findViewById<Button>(R.id.btnAdminLogin)
        val signUpLink = view.findViewById<TextView>(R.id.tvGoToSignup)

        btnLogin.setOnClickListener {
            val email = editEmail.text.toString().trim()
            val password = editPassword.text.toString().trim()

            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(requireContext(), "Please enter all details!", Toast.LENGTH_SHORT).show()
            } else {
                val isValid = dbHelper.checkUser(email, password)
                if (isValid) {
                    Toast.makeText(requireContext(), "Login Successful!", Toast.LENGTH_SHORT).show()
                    // Navigate to Dashboard Fragment using the Navigation Component
                    findNavController().navigate(R.id.action_loginFragment_to_dashboardFragment)
                } else {
                    Toast.makeText(requireContext(), "Invalid credentials!", Toast.LENGTH_SHORT).show()
                }
            }
        }

        signUpLink.setOnClickListener {
            // Navigate to Signup Fragment using the Navigation Component
            findNavController().navigate(R.id.action_loginFragment_to_signupFragment)
        }

        return view
    }
}
