//
//  GetResultsScreen.swift
//  eLearningApp
//
//  Created by admin on 15/02/25.
//

import SwiftUI

struct GetResultsScreen: View {
    var body: some View {
        VStack {
            Text("Exam Results")
                .font(.largeTitle)
                .padding()
            
            List {
                Text("Mathematics: 80%")
                Text("Science: 90%")
                Text("History: 75%")
                Text("English: 85%")
            }
            .padding()
            
            Spacer()
        }
        .navigationTitle("Results")
    }
}



struct GetResultsScreen_Previews: PreviewProvider {
    static var previews: some View {
        GetResultsScreen()
    }
}
