//
//  User.swift
//  eLearningApp
//
//  Created by admin on 15/02/25.
//

import Foundation
import CoreData

@objc(User)
public class User: NSManagedObject {

    @NSManaged public var id: UUID?
    @NSManaged public var name: String?
    @NSManaged public var email: String?
    @NSManaged public var password: String?
    @NSManaged public var role: String?

    // Helper function to initialize a new User
    static func createUser(name: String, email: String, password: String, role: String, context: NSManagedObjectContext) {
        let newUser = User(context: context)
        newUser.id = UUID()
        newUser.name = name
        newUser.email = email
        newUser.password = password
        newUser.role = role

        do {
            try context.save()
        } catch {
            print("Error saving user: \(error)")
        }
    }
}
