//
//  DashBoardView.swift
//  eLearningApp
//
//  Created by admin on 15/02/25.
//
import SwiftUI

struct DashBoardView: View {
    @State private var isLoggedOut = false
    
    var body: some View {
        NavigationView {
            VStack {
                Text("Student Dashboard")
                    .font(.largeTitle)
                    .padding()
                
                NavigationLink(destination: ViewSubjectsScreen()) {
                    DashboardButton(title: "View Subjects", icon: "book.fill")
                }
                
                NavigationLink(destination: TakeExamScreen()) {
                    DashboardButton(title: "Take Exam", icon: "pencil.tip")
                }
                
                NavigationLink(destination: GetResultsScreen()) {
                    DashboardButton(title: "View Results", icon: "chart.bar.fill")
                }
                
                Button(action: {
                    isLoggedOut = true
                }) {
                    Text("Logout")
                        .font(.headline)
                        .foregroundColor(.white)
                        .padding()
                        .frame(maxWidth: .infinity)
                        .background(Color.red)
                        .cornerRadius(8)
                        .padding(.horizontal, 20)
                }
                .padding(.top, 20)
                .fullScreenCover(isPresented: $isLoggedOut) {
                    UserLoginScreen()
                }
            }
        }
    }
}

// MARK: - Reusable Dashboard Button
struct DashboardButton: View {
    var title: String
    var icon: String
    
    var body: some View {
        HStack {
            Image(systemName: icon)
                .foregroundColor(.blue)
            Text(title)
                .font(.headline)
        }
        .padding()
        .frame(maxWidth: .infinity)
        .background(Color.gray.opacity(0.2))
        .cornerRadius(8)
        .padding(.horizontal, 20)
    }
}



struct DashBoardView_Previews: PreviewProvider {
    static var previews: some View {
        DashBoardView()
    }
}
