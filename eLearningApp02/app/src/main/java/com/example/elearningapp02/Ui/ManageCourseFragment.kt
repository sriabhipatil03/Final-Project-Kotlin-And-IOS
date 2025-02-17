package com.example.elearningapp02.Ui

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.elearningapp02.Database.DatabaseHelper
import com.example.elearningapp02.R
import com.example.elearningapp02.Database.Course

class ManageCourseFragment : Fragment() {

    private lateinit var dbHelper: DatabaseHelper
    private lateinit var courseListView: ListView
    private lateinit var emptyTextView: TextView
    private lateinit var courseList: MutableList<Course>
    private lateinit var adapter: ArrayAdapter<String>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_manage_course, container, false)

        dbHelper = DatabaseHelper(requireContext())
        courseListView = view.findViewById(R.id.lvCourses)
        emptyTextView = view.findViewById(R.id.tvEmptyCourses)

        loadCourses()

        courseListView.onItemClickListener = AdapterView.OnItemClickListener { _, _, position, _ ->
            showOptionsDialog(position)
        }

        return view
    }

    private fun loadCourses() {
        courseList = dbHelper.getAllCourses().toMutableList()
        if (courseList.isEmpty()) {
            emptyTextView.visibility = View.VISIBLE
            courseListView.visibility = View.GONE
        } else {
            emptyTextView.visibility = View.GONE
            courseListView.visibility = View.VISIBLE

            val courseTitles = courseList.map { it.title }
            adapter = ArrayAdapter(requireContext(), android.R.layout.simple_list_item_1, courseTitles)
            courseListView.adapter = adapter
        }
    }

    private fun showOptionsDialog(position: Int) {
        val options = arrayOf("Edit", "Delete")
        val course = courseList[position]

        AlertDialog.Builder(requireContext())
            .setTitle("Manage Course")
            .setItems(options) { _, which ->
                when (which) {
                    0 -> editCourse(course)
                    1 -> deleteCourse(course.id)
                }
            }
            .show()
    }

    private fun editCourse(course: Course) {
        // Create a Bundle with the course details to pass to the next fragment
        val bundle = Bundle().apply {
            putInt("COURSE_ID", course.id)
            putString("COURSE_TITLE", course.title)
            putString("COURSE_DESCRIPTION", course.description)
            putString("COURSE_FILE_PATH", course.filePath)
        }

        // Find the NavController and navigate to the EditCourseFragment using the Bundle
        val navController = findNavController()
        navController.navigate(R.id.action_manageCourseFragment_to_editCourseFragment, bundle)
    }

    private fun deleteCourse(courseId: Int) {
        AlertDialog.Builder(requireContext())
            .setTitle("Delete Course")
            .setMessage("Are you sure you want to delete this course?")
            .setPositiveButton("Yes") { _, _ ->
                if (dbHelper.deleteCourse(courseId)) {
                    Toast.makeText(requireContext(), "Course deleted", Toast.LENGTH_SHORT).show()
                    loadCourses()
                } else {
                    Toast.makeText(requireContext(), "Failed to delete course", Toast.LENGTH_SHORT).show()
                }
            }
            .setNegativeButton("No", null)
            .show()
    }
}
