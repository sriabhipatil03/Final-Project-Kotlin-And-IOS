//
//  ViewSubjectsScreen.swift
//  eLearningApp
//
//  Created by admin on 15/02/25.
//

import SwiftUI

struct ViewSubjectsScreen: View {
    let subjects = ["Mathematics", "Science", "History", "English"]
    
    var body: some View {
        List(subjects, id: \.self) { subject in
            HStack {
                Text(subject)
                Spacer()
                Button(action: {
                    print("\(subject) downloaded")
                }) {
                    Image(systemName: "arrow.down.circle.fill")
                        .foregroundColor(.blue)
                }
            }
            .padding()
        }
        .navigationTitle("Subjects")
    }
}





