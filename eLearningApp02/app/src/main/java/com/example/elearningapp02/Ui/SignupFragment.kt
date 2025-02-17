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
import com.example.elearningapp02.Database.DatabaseHelper
import com.example.elearningapp02.R

class SignupFragment : Fragment() {

    private lateinit var dbHelper: DatabaseHelper

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_signup, container, false)

        dbHelper = DatabaseHelper(requireContext())

        val editUsername = view.findViewById<EditText>(R.id.etAdminUserName)
        val editEmail = view.findViewById<EditText>(R.id.etAdminEmail)
        val editPassword = view.findViewById<EditText>(R.id.etAdminPassword)
        val btnSignup = view.findViewById<Button>(R.id.btnAdminSignup)

        btnSignup.setOnClickListener {
            val username = editUsername.text.toString().trim()
            val email = editEmail.text.toString().trim()
            val password = editPassword.text.toString().trim()

            if (username.isEmpty() || email.isEmpty() || password.isEmpty()) {
                Toast.makeText(requireContext(), "All fields are required!", Toast.LENGTH_SHORT).show()
            } else {
                val isInserted = dbHelper.insertUser(username, email, password)
                if (isInserted) {
                    Toast.makeText(requireContext(), "Signup Successful!", Toast.LENGTH_SHORT).show()
                    // Navigate back to Login Fragment after successful signup
                    findNavController().navigate(R.id.action_signupFragment_to_loginFragment)
                } else {
                    Toast.makeText(requireContext(), "Signup Failed!", Toast.LENGTH_SHORT).show()
                }
            }
        }

        return view
    }
}
