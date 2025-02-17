package com.example.elearningapp02.Ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.elearningapp02.Data.ExamQuestion
import com.example.elearningapp02.Database.DatabaseHelper
import com.example.elearningapp02.R

class AddExamFragment : Fragment() {

    private lateinit var dbHelper: DatabaseHelper
    private lateinit var courseSpinner: Spinner
    private lateinit var etQuestion: EditText
    private lateinit var etOptionA: EditText
    private lateinit var etOptionB: EditText
    private lateinit var etOptionC: EditText
    private lateinit var etOptionD: EditText
    private lateinit var correctAnswerSpinner: Spinner
    private lateinit var btnSaveQuestion: Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_add_exam_question, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        dbHelper = DatabaseHelper(requireContext())

        courseSpinner = view.findViewById(R.id.spinnerCourse)
        etQuestion = view.findViewById(R.id.etQuestion)
        etOptionA = view.findViewById(R.id.etOptionA)
        etOptionB = view.findViewById(R.id.etOptionB)
        etOptionC = view.findViewById(R.id.etOptionC)
        etOptionD = view.findViewById(R.id.etOptionD)
        correctAnswerSpinner = view.findViewById(R.id.spinnerCorrectAnswer)
        btnSaveQuestion = view.findViewById(R.id.btnSaveQuestion)

        loadCourses()

        btnSaveQuestion.setOnClickListener {
            saveQuestion()
        }
    }

    private fun loadCourses() {
        val courses = dbHelper.getAllCourses() // Get all courses from the database
        Log.d("AddExamFragment", "Courses loaded: ${courses.size}")

        if (courses.isEmpty()) {
            Toast.makeText(requireContext(), "No courses available", Toast.LENGTH_SHORT).show()
            return
        }

        val courseNames = courses.map { it.title }

        // Check the list of course names
        Log.d("AddExamFragment", "Course names: $courseNames")

        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_dropdown_item, courseNames)
        courseSpinner.adapter = adapter
    }


    private fun saveQuestion() {
        val selectedCourseId = courseSpinner.selectedItemPosition + 1  // Ensure selectedItemPosition is not -1
        if (selectedCourseId == 0) {
            Toast.makeText(requireContext(), "Please select a course.", Toast.LENGTH_SHORT).show()
            return
        }

        val questionText = etQuestion.text.toString().trim()
        val optionA = etOptionA.text.toString().trim()
        val optionB = etOptionB.text.toString().trim()
        val optionC = etOptionC.text.toString().trim()
        val optionD = etOptionD.text.toString().trim()

        val selectedAnswer = correctAnswerSpinner.selectedItem
        if (selectedAnswer == null) {
            Toast.makeText(requireContext(), "Please select a correct answer.", Toast.LENGTH_SHORT).show()
            return
        }

        val correctAnswer = selectedAnswer.toString().trim()

        if (questionText.isNotEmpty() && optionA.isNotEmpty() && optionB.isNotEmpty() &&
            optionC.isNotEmpty() && optionD.isNotEmpty() && correctAnswer.isNotEmpty()) {

            val question = ExamQuestion(0, selectedCourseId, questionText, optionA, optionB, optionC, optionD, correctAnswer)

            if (dbHelper.insertExamQuestion(question)) {
                Toast.makeText(requireContext(), "Question Added Successfully!", Toast.LENGTH_SHORT).show()
                findNavController().popBackStack() // Navigate back after adding the question
            } else {
                Toast.makeText(requireContext(), "Failed to add question.", Toast.LENGTH_SHORT).show()
            }
        } else {
            Toast.makeText(requireContext(), "Please fill all fields!", Toast.LENGTH_SHORT).show()
        }
    }

}
