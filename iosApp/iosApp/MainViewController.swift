//
//  MainViewController.swift
//  iosApp
//
//  Created by Heriberto Reyes Esparza on 12/12/20.
//  Copyright © 2020 Gray Raccoon. All rights reserved.
//

import Foundation
import UIKit
import shared

class MainViewController: UIViewController {
    
    @IBOutlet var SampleLabel: UILabel!
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
        self.SampleLabel.text = "Hello World!"
    }
    
}

