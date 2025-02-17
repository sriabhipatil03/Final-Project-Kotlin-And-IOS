//
//  eLearningAppApp.swift
//  eLearningApp
//
//  Created by admin on 15/02/25.
//

import SwiftUI

@main
struct eLearningAppApp: App {
    let persistenceController = PersistenceController.shared

    var body: some Scene {
        WindowGroup {
            ContentView()
                .environment(\.managedObjectContext, persistenceController.container.viewContext)
        }
    }
}
