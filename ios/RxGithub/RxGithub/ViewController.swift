//
//  ViewController.swift
//  RxGithub
//
//  Created by Sergii Frost on 2018-03-29.
//  Copyright © 2018 Frost°. All rights reserved.
//

import UIKit
import RxSwift
import RxCocoa

class ViewController: UIViewController {
    
    var disposeBag = DisposeBag()

    @IBOutlet weak var inputTextView: UITextView!
    @IBOutlet weak var textCounterLabel: UILabel!
    @IBOutlet weak var inputTextViewNotCool: UITextView!    
    @IBOutlet weak var watLabel: UILabel!
    
    @IBOutlet weak var usernameTextField: UITextField!
    @IBOutlet weak var passwordTextField: UITextField!
    @IBOutlet weak var loginButton: UIButton!
    
    
    override func viewDidLoad() {
        super.viewDidLoad()
        initBindings()
        initLoginBindings()
        //initTextView()
    }
    
    func initTextView() {
        inputTextView.delegate = self
        inputTextViewNotCool.delegate = self
    }
    
    func initBindings() {
        inputTextView.rx.text.orEmpty
            .map { input -> Int in
                return input.count
            }.subscribe({ [weak self] countEvent in
                self?.textCounterLabel.text = "\(countEvent.element ?? 0)"
            }).disposed(by: disposeBag)
     
        inputTextView.rx.text.orEmpty.map { input -> UIColor in
            let count = input.count
            if count > 10 && count <= 50 {
                return UIColor.green
            } else if count > 50 {
                return UIColor.red
            } else {
                return UIColor.blue
            }
            }.subscribe({ (colorEvent) in
                self.textCounterLabel.textColor = colorEvent.element
            }).disposed(by: disposeBag)
        
        inputTextViewNotCool.rx.text.orEmpty.map { input -> String in
            return String(input.reversed())
            }.subscribe { reversedEvent in
                self.watLabel.text = reversedEvent.element
            }.disposed(by: disposeBag)
    }
    
    func initLoginBindings() {
        let usernameObservable = usernameTextField.rx.text.orEmpty.map { username -> Bool in
            return username.count > 10
        }.asObservable().distinctUntilChanged()
        
        let passwordObservable = passwordTextField.rx.text.orEmpty.map { password -> Bool in
            return password.count > 6
        }.asObservable().distinctUntilChanged()
        
//        //text color
//        usernameObservable.map { isValid -> UIColor in
//            return isValid ? UIColor.blue : UIColor.red
//        }.subscribe({ colorEvent in
//            self.usernameTextField.textColor = colorEvent.element
//        }).disposed(by: disposeBag)
//
//        //text color
//        passwordObservable.map { isValid -> UIColor in
//            return isValid ? UIColor.blue : UIColor.red
//        }.subscribe({ colorEvent in
//            self.passwordTextField.textColor = colorEvent.element
//        }).disposed(by: disposeBag)

        //handle button state
        
        Observable.combineLatest([usernameObservable, passwordObservable])
            .asObservable()
            .map { values -> Bool in
                //Look for any FALSE in array
                return values.first(where: { value -> Bool in return value == false }) ?? true
        }.bind(to: loginButton.rx.isEnabled).disposed(by: disposeBag)
    }
}

extension ViewController: UITextViewDelegate {
    func textViewDidChange(_ textView: UITextView) {
        if textView == inputTextView {
            let count = textView.text.count
            textCounterLabel.text = "\(textView.text.count)"
            if count > 10 && count <= 50 {
                textCounterLabel.textColor = UIColor.green
            } else if count > 50 {
                textCounterLabel.textColor = UIColor.red
            } else {
                textCounterLabel.textColor = UIColor.blue
            }
        } else if textView == inputTextViewNotCool {
            watLabel.text = String(inputTextViewNotCool.text.reversed())
        }
    }
}
