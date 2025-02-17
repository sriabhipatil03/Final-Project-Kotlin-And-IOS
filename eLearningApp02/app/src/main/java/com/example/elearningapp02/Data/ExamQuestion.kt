package com.example.elearningapp02.Data

data class ExamQuestion(
    val id: Int = 0,
    val courseId: Int,
    val question: String,
    val optionA: String,
    val optionB: String,
    val optionC: String,
    val optionD: String,
    val correctAnswer: String
)
