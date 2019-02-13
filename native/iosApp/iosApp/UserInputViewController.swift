//
//  UserInputViewController.swift
//  iosApp
//
//  Created by Sabrina Wenngatz on 07.11.18.
//

import UIKit
import commonClient

class UserInputViewController: UIViewController, UserInputView {
    
    @IBOutlet weak var userSurnameTextField: UITextField!
    @IBOutlet weak var userVornameTextField: UITextField!
    @IBOutlet weak var birthdayDatePicker: UIDatePicker!
    @IBOutlet weak var genderSegmentControl: UISegmentedControl!
    @IBOutlet weak var activityIndicator: UIActivityIndicatorView!
    
    private var userPresenter:UserInputPresenter!
 

    required init?(coder aDecoder: NSCoder) {
        super.init(coder: aDecoder)
        userPresenter = PresenterProvider.userInputPresenter(view: self)
   
    }
    
    override func viewWillAppear(_ animated: Bool) {
        activityIndicator.hidesWhenStopped = true
        activityIndicator.stopAnimating()
    }

    func sentFilledData() {
        let userName = userSurnameTextField.text ?? ""
        let userFirstname = userVornameTextField.text ?? ""
        
        if(validateInput() == UserInputValidator.firstname){
            showError(error: KotlinThrowable(message: "Please enter your firstname"))
        }
        if(validateInput() == UserInputValidator.surname){
            showError(error: KotlinThrowable(message: "Please enter your surname"))
        }
        if(validateInput() == UserInputValidator.success){
            let components = NSCalendar.current.dateComponents([.year, .month, .day], from: birthdayDatePicker.date)
            let birthday = CommonBirthday(day: Int32(components.day ?? 1),month: Int32(components.month ?? 1),year: Int32(components.year ?? 2000))
            let user = CommonUser(firstName: userFirstname, surname: userName, gender: genderSelect(), birthday: birthday)
        
            userPresenter.calculateInsurancePremium(user: user)
        }
        else {
            showError(error: KotlinThrowable(message: "Unknown Error"))
        }
    }
    
    func genderSelect() -> CommonGender{
        switch genderSegmentControl.selectedSegmentIndex {
        case 0:
            return .female
        case 1:
            return .male
        default:
            return .divers
        }
    }
    
    func showInsurancePremium(monthlyCost: Int32) {
        let resultViewController = UIStoryboard(name: "Main", bundle: nil).instantiateViewController(withIdentifier: "ResultViewController") as! DisplayResultsViewController
        self.present(resultViewController, animated: true, completion: ({
            resultViewController.createView(message: String(monthlyCost))
        }))
       
    }
    
    func validateInput() -> UserInputValidator {
        if(userVornameTextField.text == ""){
            return UserInputValidator.firstname
        }
        if(userSurnameTextField.text == ""){
            return UserInputValidator.surname
        }
        return UserInputValidator.success
    }
    
    func logError(error: KotlinThrowable) {
        print(error.message!)
    }
    
    func showError(error: KotlinThrowable) {
        let alertController = UIAlertController(title: "Error", message: error.message ?? "Error", preferredStyle: .alert)
        alertController.addAction(UIAlertAction(title: "Ok", style: .destructive, handler: nil))
        self.present(alertController, animated: true, completion: nil)
    }
    
    @IBAction func continueAction(_ sender: Any) {
        activityIndicator.startAnimating()
        sentFilledData()
    }
    
}

