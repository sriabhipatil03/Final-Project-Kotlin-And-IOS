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
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.elearningapp02.Database.DatabaseHelper
import com.example.elearningapp02.R

class EditCourseFragment : Fragment() {

    private lateinit var dbHelper: DatabaseHelper
    private var courseId: Int = -1
    private lateinit var editTitle: EditText
    private lateinit var editDescription: EditText
    private lateinit var editFilePath: EditText
    private var filePath: String? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_edit_course, container, false)

        dbHelper = DatabaseHelper(requireContext())
        editTitle = view.findViewById(R.id.etCourseTitle)
        editDescription = view.findViewById(R.id.etCourseDescription)
        editFilePath = view.findViewById(R.id.etFilePath)
        val btnUpdate = view.findViewById<Button>(R.id.btnUpdateCourse)
        val btnUploadMaterial = view.findViewById<Button>(R.id.btnUploadMaterial)

        // Get course details from arguments
        val args = arguments
        courseId = args?.getInt("course_id") ?: -1
        val courseTitle = args?.getString("course_title")
        val courseDescription = args?.getString("course_description")
        filePath = args?.getString("course_file_path")

        // Set the existing course details in input fields
        editTitle.setText(courseTitle)
        editDescription.setText(courseDescription)
        editFilePath.setText(filePath)

        // Open file chooser when Upload Material button is clicked
        btnUploadMaterial.setOnClickListener {
            openFileChooser()
        }

        // Update course details when Update button is clicked
        btnUpdate.setOnClickListener {
            val newTitle = editTitle.text.toString().trim()
            val newDescription = editDescription.text.toString().trim()

            if (newTitle.isEmpty() || newDescription.isEmpty()) {
                Toast.makeText(requireContext(), "Please fill all fields!", Toast.LENGTH_SHORT).show()
            } else {
                if (filePath != null) {
                    dbHelper.updateCourse(courseId, newTitle, newDescription, filePath)
                } else {
                    dbHelper.updateCourse(courseId, newTitle, newDescription)
                }
                Toast.makeText(requireContext(), "Course Updated!", Toast.LENGTH_SHORT).show()

                // Return to previous fragment
                requireActivity().supportFragmentManager.popBackStack()
            }
        }

        return view
    }

    // Open file chooser to select a file
    private fun openFileChooser() {
        val intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.type = "application/pdf" // Adjust the type according to the file type you want
        startActivityForResult(intent, 1)
    }

    // Handle the result from file chooser
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == Activity.RESULT_OK && requestCode == 1) {
            data?.data?.let { uri ->
                filePath = getFilePathFromUri(uri)
                editFilePath.setText(filePath)
            }
        }
    }

    // Convert URI to file path (optional: depending on your app’s needs)
    private fun getFilePathFromUri(uri: Uri): String? {
        // Implement logic to get the file path from the URI
        // For example, using ContentResolver to retrieve the path
        return uri.path
    }

    companion object {
        fun newInstance(courseId: Int, courseTitle: String, courseDescription: String, courseFilePath: String?): EditCourseFragment {
            val fragment = EditCourseFragment()
            val args = Bundle()
            args.putInt("course_id", courseId)
            args.putString("course_title", courseTitle)
            args.putString("course_description", courseDescription)
            args.putString("course_file_path", courseFilePath)
            fragment.arguments = args
            return fragment
        }
    }
}
