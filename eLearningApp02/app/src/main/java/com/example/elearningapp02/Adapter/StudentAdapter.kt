package com.example.elearningapp02.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.elearningapp02.Data.Student
import com.example.elearningapp02.R

class StudentAdapter(
    private var students: MutableList<Student>,
    private val onUpdateClick: (Student) -> Unit,
    private val onDeleteClick: (Student) -> Unit
) : RecyclerView.Adapter<StudentAdapter.StudentViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StudentViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_student, parent, false)
        return StudentViewHolder(view)
    }

    override fun onBindViewHolder(holder: StudentViewHolder, position: Int) {
        val student = students[position]
        holder.bind(student)
    }

    override fun getItemCount() = students.size

    fun updateList(newStudents: List<Student>) {
        students.clear()
        students.addAll(newStudents)
        notifyDataSetChanged()
    }

    fun removeStudent(student: Student) {
        val position = students.indexOf(student)
        if (position != -1) {
            students.removeAt(position)
            notifyItemRemoved(position)
        }
    }

    inner class StudentViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val nameText: TextView = itemView.findViewById(R.id.studentName)
        private val emailText: TextView = itemView.findViewById(R.id.studentEmail)
        private val updateButton: ImageView = itemView.findViewById(R.id.btnUpdate)
        private val deleteButton: ImageView = itemView.findViewById(R.id.btnDelete)

        fun bind(student: Student) {
            nameText.text = student.name
            emailText.text = student.email

            updateButton.setOnClickListener { onUpdateClick(student) }
            deleteButton.setOnClickListener { onDeleteClick(student) }
        }
    }

}
