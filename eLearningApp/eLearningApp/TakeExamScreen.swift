//
//  TakeExamScreen.swift
//  eLearningApp
//
//  Created by admin on 15/02/25.
//

import SwiftUI

struct TakeExamScreen: View {
    @State private var selectedAnswers: [Int?] = Array(repeating: nil, count: 3)
    @State private var showResults = false
    
    let questions = [
        ("What is 2+2?", ["2", "3", "4", "5"], 2),
        ("What is the capital of France?", ["London", "Paris", "Berlin", "Rome"], 1),
        ("Which planet is known as the Red Planet?", ["Earth", "Mars", "Jupiter", "Venus"], 1)
    ]
    
    var body: some View {
        VStack {
            List(0..<questions.count, id: \.self) { index in
                VStack(alignment: .leading) {
                    Text(questions[index].0)
                        .font(.headline)
                    ForEach(0..<questions[index].1.count, id: \.self) { optionIndex in
                        Button(action: {
                            selectedAnswers[index] = optionIndex
                        }) {
                            HStack {
                                Image(systemName: selectedAnswers[index] == optionIndex ? "largecircle.fill.circle" : "circle")
                                    .foregroundColor(.blue)
                                Text(questions[index].1[optionIndex])
                            }
                        }
                        .padding(.vertical, 5)
                    }
                }
            }
            
            Button(action: {
                showResults = true
            }) {
                Text("Submit Exam")
                    .font(.headline)
                    .foregroundColor(.white)
                    .padding()
                    .background(Color.blue)
                    .cornerRadius(8)
            }
            .padding()
            .fullScreenCover(isPresented: $showResults) {
                GetResultsScreen()
            }
        }
        .navigationTitle("Take Exam")
    }
}

struct TakeExamScreen_Previews: PreviewProvider {
    static var previews: some View {
        TakeExamScreen()
    }
}

