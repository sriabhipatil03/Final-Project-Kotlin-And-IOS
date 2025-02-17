//
//  UserLoginScreen.swift
//  eLearningApp
//
//  Created by admin on 15/02/25.
//

import SwiftUI

struct UserLoginScreen: View {
    @State private var username = ""
    @State private var password = ""
    @State private var isLoggedIn = false
    
    var body: some View {
        VStack {
            Text("User Login")
                .font(.largeTitle)
                .padding()
            
            TextField("Username", text: $username)
                .textFieldStyle(RoundedBorderTextFieldStyle())
                .padding()
            
            SecureField("Password", text: $password)
                .textFieldStyle(RoundedBorderTextFieldStyle())
                .padding()
            
            Button(action: {
                isLoggedIn = true
            }) {
                Text("Login")
                    .font(.headline)
                    .foregroundColor(.white)
                    .padding()
                    .frame(maxWidth: .infinity)
                    .background(Color.blue)
                    .cornerRadius(8)
                    .padding(.horizontal, 20)
            }
            .fullScreenCover(isPresented: $isLoggedIn) {
                DashBoardView()
            }
            
            Spacer()
        }
    }
}

struct UserLoginScreen_Previews: PreviewProvider {
    static var previews: some View {
        UserLoginScreen()
    }
}
