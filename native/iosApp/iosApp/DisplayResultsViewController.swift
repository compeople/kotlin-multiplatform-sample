//
//  DisplayResultsController.swift
//  iosApp
//
//  Created by Sabrina Wenngatz on 07.11.18.
//

import UIKit
import commonClient

class DisplayResultsViewController: UIViewController, DisplayResultsView {
   
    @IBOutlet weak var resultsLabel: UILabel!

    private var displayPresenter:DisplayResultsPresenter!
    
    required init?(coder aDecoder: NSCoder) {
        super.init(coder: aDecoder)
        displayPresenter = PresenterProvider.displayResultsPresenter(view: self)
    }
    

    func createView(message: String) {
        resultsLabel.text = message + "$"
    }
    
    //is called from displayPresenter.navigateBack()
    func goBackToUserInputView() {
        self.dismiss(animated: true, completion: nil)
    }
    
    @IBAction func backButton(_ sender: UIButton) {
        displayPresenter.navigateBack()
    }
    
}
