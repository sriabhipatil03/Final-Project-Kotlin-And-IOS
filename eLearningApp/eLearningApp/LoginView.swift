//
//  LoginView.swift
//  eLearningApp
//
//  Created by admin on 15/02/25.
//

import SwiftUI

struct LoginView: View {
    @Environment(\.managedObjectContext) private var viewContext
    @FetchRequest(entity: User.entity(), sortDescriptors: []) private var users: FetchedResults<User>
    
    @State private var email = ""
    @State private var password = ""
    @State private var isAuthenticated = false
    @State private var showError = false

    var body: some View {
        NavigationView {
            VStack {
                Text("Welcome to E-Learning App")
                    .padding()
                
                Text("Login")
                    .font(.largeTitle)
                    .padding()

                TextField("Email", text: $email)
                    .textFieldStyle(RoundedBorderTextFieldStyle())
                    .padding()

                SecureField("Password", text: $password)
                    .textFieldStyle(RoundedBorderTextFieldStyle())
                    .padding()

                if showError {
                    Text("Invalid Email or password!")
                        .foregroundColor(.red)
                }

                Button("Login") {
                    authenticateUser()
                }
                .buttonStyle(.borderedProminent)
                .padding()

                NavigationLink("Want to Register? Click here ", destination: RegisterView())
                    .padding()
            }
            .fullScreenCover(isPresented: $isAuthenticated) {
               DashBoardView()
            }
        }
    }

    private func authenticateUser() {
        if users.contains(where: { $0.email == email && $0.password == password }) {
            isAuthenticated = true
        } else {
            showError = true
        }
    }
}

struct LoginView_Previews: PreviewProvider {
    static var previews: some View {
        LoginView()
    }
}
