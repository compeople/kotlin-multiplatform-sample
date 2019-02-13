//
//  PresenterProvider.swift
//  iosApp
//

import Foundation
import commonClient

class PresenterProvider {

    private let keyValueStore:KeyValueStore
    private let timeService:CommonTimeService
    private let tarifRepo:TarifRepository
    private let tarifService:TarifService
    private let rechenkern: CommonRechenkern
    
    private init() {
        timeService = CommonTimeService()
        keyValueStore = KeyValueStoreIOS()
        tarifRepo = TarifRepository(keyValueStore: keyValueStore)
        tarifService = TarifService(tarifRepository: tarifRepo, tarifClient: TarifClient(),timeService: timeService)
        rechenkern = CommonRechenkern(tarifService: tarifService, timeService: timeService)
    }
    
    private static let instance = PresenterProvider()
    
    static func userInputPresenter(view:UserInputView) -> UserInputPresenter {
        return UserInputPresenter(view:view, rechenkern:instance.rechenkern)
    }
    
    static func displayResultsPresenter(view:DisplayResultsView) -> DisplayResultsPresenter {
        return DisplayResultsPresenter(displayResultsView: view)
    }
    
}
