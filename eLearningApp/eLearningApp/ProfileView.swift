//
//  ProfileView.swift
//  eLearningApp
//
//  Created by admin on 15/02/25.
//

import SwiftUI

struct ProfileView: View {
    @Environment(\.managedObjectContext) private var viewContext
    @State private var user: User?

    var body: some View {
        VStack {
            if let user = user {
                Text("Name: \(user.name ?? "Unknown")")
                Text("Email: \(user.email ?? "Unknown")")
                Text("Role: \(user.role ?? "Unknown")")
            }
        }
        .onAppear {
            fetchUserData()
        }
        .navigationTitle("Profile")
    }

    private func fetchUserData() {
        let fetchRequest: NSFetchRequest<User> = User.fetchRequest()
        fetchRequest.predicate = NSPredicate(format: "email == %@", "user@example.com") // Example, fetch based on logged-in user
        do {
            let users = try viewContext.fetch(fetchRequest)
            if let fetchedUser = users.first {
                user = fetchedUser
            }
        } catch {
            print("Error fetching user: \(error)")
        }
    }
}

struct ProfileView_Previews: PreviewProvider {
    static var previews: some View {
        ProfileView()
    }
}
