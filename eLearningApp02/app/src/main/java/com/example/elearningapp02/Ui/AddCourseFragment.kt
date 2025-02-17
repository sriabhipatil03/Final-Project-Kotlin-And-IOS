package com.example.elearningapp02.Ui

import android.app.Activity
import android.content.Intent
import android.net.Uri
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


class AddCourseFragment : Fragment() {

    private lateinit var dbHelper: DatabaseHelper
    private lateinit var etCourseTitle: EditText
    private lateinit var etCourseDescription: EditText
    private lateinit var tvSelectedFile: TextView
    private lateinit var btnUploadFile: Button
    private lateinit var btnSaveCourse: Button

    private var selectedFileUri: Uri? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_add_course, container, false)

        dbHelper = DatabaseHelper(requireContext())

        etCourseTitle = view.findViewById(R.id.etCourseTitle)
        etCourseDescription = view.findViewById(R.id.etCourseDescription)
        tvSelectedFile = view.findViewById(R.id.tvSelectedFile)
        btnUploadFile = view.findViewById(R.id.btnUploadFile)
        btnSaveCourse = view.findViewById(R.id.btnSaveCourse)

        btnUploadFile.setOnClickListener {
            selectFile()
        }

        btnSaveCourse.setOnClickListener {
            saveCourse()
        }

        return view
    }

    private fun selectFile() {
        val intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.type = "*/*"
        intent.putExtra(Intent.EXTRA_MIME_TYPES, arrayOf("application/pdf", "application/vnd.ms-powerpoint", "video/*"))
        startActivityForResult(intent, FILE_PICKER_REQUEST)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == FILE_PICKER_REQUEST && resultCode == Activity.RESULT_OK) {
            selectedFileUri = data?.data
            tvSelectedFile.text = "Selected: ${selectedFileUri?.lastPathSegment}"
        }
    }

    private fun saveCourse() {
        val title = etCourseTitle.text.toString().trim()
        val description = etCourseDescription.text.toString().trim()
        val filePath = selectedFileUri?.toString() ?: ""

        if (title.isEmpty() || description.isEmpty() || filePath.isEmpty()) {
            Toast.makeText(requireContext(), "Please fill all fields!", Toast.LENGTH_SHORT).show()
            return
        }

        val success = dbHelper.insertCourse(title, description, filePath)
        if (success) {
            Toast.makeText(requireContext(), "Course Added Successfully!", Toast.LENGTH_SHORT).show()
            findNavController().navigateUp()
        } else {
            Toast.makeText(requireContext(), "Failed to add course!", Toast.LENGTH_SHORT).show()
        }
    }

    companion object {
        private const val FILE_PICKER_REQUEST = 1
    }
}
