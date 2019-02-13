//
//  KeyValueStore.swift
//  iosApp
//
//  Created by Sabrina Wenngatz on 20.11.18.
//

import Foundation
import commonClient


class KeyValueStoreIOS: UserDefaults, KeyValueStore {
    
    func getValue(key: String) -> String? {
        return UserDefaults.standard.string(forKey: key)
    }
    
    func setValue(key: String, value: String) {
        let defaults = UserDefaults.standard
        defaults.set(value, forKey: key)
    }
}
