//
//  Persistence.swift
//  eLearningApp
//
//  Created by admin on 15/02/25.
//

import CoreData

struct PersistenceController {
    static let shared = PersistenceController()

    let container: NSPersistentContainer
    
    
    init(){
        container = NSPersistentContainer(name: "eLearningApp")
        container.loadPersistentStores{
             (storeDescription , error ) in
            if let error = error as NSError? {
                fatalError("Unresolved error \(error) , \(error.userInfo)")
            }
        }
    }
    
    var context: NSManagedObjectContext{
        return container.viewContext
    }
  
}
