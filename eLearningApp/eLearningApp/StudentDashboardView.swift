//
//  StudentDashboardView.swift
//  eLearningApp
//
//  Created by admin on 15/02/25.
//

import SwiftUI

struct AdminDashboardView: View {
    var body: some View {
        VStack {
            Text("Welcome, Student!")
                .font(.title)
                .padding()
            
            //    NavigationLink("Manage Courses", destination: //ManageCoursesView())
            //   NavigationLink("View Students", destination: //ViewStudentsView())
        }
        .navigationTitle("Student Dashboard")
    }
}

struct StudentDashboardView_Previews: PreviewProvider {
    static var previews: some View {
        StudentDashboardView()
    }
}
