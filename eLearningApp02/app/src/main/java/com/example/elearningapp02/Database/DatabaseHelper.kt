package com.example.elearningapp02.Database


import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import com.example.elearningapp02.Data.ExamQuestion
import com.example.elearningapp02.Data.Student



class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, "eLearningDB", null, 3) { // Updated Version

    override fun onCreate(db: SQLiteDatabase) {
        // Create users table
        val createUsersTable = """
            CREATE TABLE IF NOT EXISTS users (
                id INTEGER PRIMARY KEY AUTOINCREMENT, 
                username TEXT, 
                email TEXT, 
                password TEXT
            )
        """.trimIndent()
        db.execSQL(createUsersTable)

        // Create courses table
        val createCoursesTable = """
            CREATE TABLE IF NOT EXISTS courses (
                id INTEGER PRIMARY KEY AUTOINCREMENT, 
                title TEXT, 
                description TEXT, 
                file_path TEXT
            )
        """.trimIndent()
        db.execSQL(createCoursesTable)

        // Create students table
        Log.d("DatabaseHelper", "Creating students table...")
        val createTableQuery = """
    CREATE TABLE IF NOT EXISTS students (
        id INTEGER PRIMARY KEY AUTOINCREMENT,
        name TEXT ,
        email TEXT ,
        password TEXT 
    );
    """.trimIndent()
        db.execSQL(createTableQuery)
        Log.d("DatabaseHelper", "Students table created.")


        val CREATE_QUESTIONS_TABLE = """
    CREATE TABLE exam_questions (
        id INTEGER PRIMARY KEY AUTOINCREMENT,
        course_id INTEGER,
        question TEXT,
        option_a TEXT,
        option_b TEXT,
        option_c TEXT,
        option_d TEXT,
        correct_answer TEXT,
        FOREIGN KEY (course_id) REFERENCES courses(id)
    )
""".trimIndent()




    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        // Drop all old tables if they exist and recreate them
        if (oldVersion < 2) {
            db.execSQL("DROP TABLE IF EXISTS students")
            db.execSQL("DROP TABLE IF EXISTS courses")
            db.execSQL("DROP TABLE IF EXISTS users")
            onCreate(db)
        }
    }

    // Insert User
    fun insertUser(username: String, email: String, password: String): Boolean {
        val db = writableDatabase
        val values = ContentValues().apply {
            put("username", username)
            put("email", email)
            put("password", password)
        }
        val result = db.insert("users", null, values)
        db.close()
        return result != -1L
    }

    // Check User Login
    fun checkUser(email: String, password: String): Boolean {
        val db = readableDatabase
        val query = "SELECT * FROM users WHERE email = ? AND password = ?"
        val cursor = db.rawQuery(query, arrayOf(email, password))
        val exists = cursor.count > 0
        cursor.close()
        db.close()
        return exists
    }

    // Insert Course
    fun insertCourse(title: String, description: String, filePath: String): Boolean {
        val db = writableDatabase
        val values = ContentValues().apply {
            put("title", title)
            put("description", description)
            put("file_path", filePath)
        }
        val result = db.insert("courses", null, values)
        db.close()
        return result != -1L
    }

    // Get All Courses
    fun getAllCourses(): List<Course> {
        val courseList = mutableListOf<Course>()
        val db = readableDatabase
        val cursor = db.rawQuery("SELECT * FROM courses", null)

        if (cursor.moveToFirst()) {
            do {
                val id = cursor.getInt(0)
                val title = cursor.getString(1)
                val description = cursor.getString(2)
                val filePath = cursor.getString(3)
                courseList.add(Course(id, title, description, filePath))
            } while (cursor.moveToNext())
        }
        cursor.close()
        db.close()
        Log.d("DatabaseHelper", "Fetched courses: ${courseList.size}")
        return courseList
    }

    // Update Course
    fun updateCourse(courseId: Int, title: String, description: String, filePath: String? = null): Boolean {
        val db = writableDatabase
        val values = ContentValues().apply {
            put("title", title)
            put("description", description)
            if (filePath != null) put("file_path", filePath)
        }
        val result = db.update("courses", values, "id=?", arrayOf(courseId.toString()))
        db.close()
        return result > 0
    }

    // Delete Course
    fun deleteCourse(courseId: Int): Boolean {
        val db = writableDatabase
        val result = db.delete("courses", "id=?", arrayOf(courseId.toString()))
        db.close()
        return result > 0
    }

    // Insert Student
    fun insertStudent(name: String, email: String, password: String): Boolean {
        val db = writableDatabase
        val values = ContentValues().apply {
            put("name", name)
            put("email", email)
            put("password", password)
        }
        val result = db.insert("students", null, values)
        db.close()
        return result != -1L
    }


    // Update Student
    fun updateStudent(studentId: Int, name: String, email: String, password: String): Boolean {
        val db = writableDatabase
        val values = ContentValues().apply {
            put("name", name)
            put("email", email)
            put("password", password)
        }
        val result = db.update("students", values, "id=?", arrayOf(studentId.toString()))
        db.close()
        return result > 0
    }

    // Delete Student
    fun deleteStudent(studentId: Int): Boolean {
        val db = writableDatabase
        val result = db.delete("students", "id=?", arrayOf(studentId.toString()))
        db.close()
        return result > 0
    }

    // Get All Students
    fun getAllStudents(): List<Student> {
        val studentList = mutableListOf<Student>()
        val db = readableDatabase
        val cursor = db.rawQuery("SELECT * FROM students", null)

        if (cursor.moveToFirst()) {
            do {
                val id = cursor.getInt(0)
                val name = cursor.getString(1)
                val email = cursor.getString(2)
                val password = cursor.getString(3)
                studentList.add(Student(id, name, email, password))
            } while (cursor.moveToNext())
        }
        cursor.close()
        db.close()
        return studentList
    }

    // Get Student by ID
    fun getStudentById(studentId: Int): Student? {
        val db = readableDatabase
        val cursor = db.rawQuery("SELECT * FROM students WHERE id = ?", arrayOf(studentId.toString()))

        return if (cursor.moveToFirst()) {
            val id = cursor.getInt(0)
            val name = cursor.getString(1)
            val email = cursor.getString(2)
            val password = cursor.getString(3)
            cursor.close()
            Student(id, name, email, password)
        } else {
            cursor.close()
            null
        }
    }


    // Exam code
    fun insertExamQuestion(question: ExamQuestion): Boolean {
        val db = writableDatabase
        val values = ContentValues().apply {
            put("course_id", question.courseId)
            put("question", question.question)
            put("option_a", question.optionA)
            put("option_b", question.optionB)
            put("option_c", question.optionC)
            put("option_d", question.optionD)
            put("correct_answer", question.correctAnswer)
        }
        val result = db.insert("exam_questions", null, values)
        return result != -1L
    }





}

// Course Data Class
data class Course(val id: Int, val title: String, val description: String, val filePath: String)
