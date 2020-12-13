//
//  LaunchesTableViewCell.swift
//  iosApp
//
//  Created by Heriberto Reyes Esparza on 12/12/20.
//  Copyright © 2020 Gray Raccoon. All rights reserved.
//

import UIKit
import shared

class LaunchesTableViewCell: UITableViewCell {

    public static let KEY: String = "LaunchesTableViewCell"
    
    public static let Nib: UINib = UINib.init(nibName: KEY, bundle: Bundle.main)
    
    @IBOutlet var missionName: UILabel!
    @IBOutlet var launchSuccess: UILabel!
    @IBOutlet var launchYear: UILabel!
    @IBOutlet var details: UILabel!
    
    override func awakeFromNib() {
        super.awakeFromNib()
        // Initialization code
    }

    override func setSelected(_ selected: Bool, animated: Bool) {
        super.setSelected(selected, animated: animated)

        // Configure the view for the selected state
    }
    
    func populate(item: RocketLaunch) {
        self.missionName.text = "Launch Name: \(item.missionName)"
        self.launchYear.text = "Launch Year: \(item.launchYear)"
        self.details.text = "Launch Details: \(item.details ?? "")"
        
        if (item.launchSuccess == nil) {
            self.launchSuccess.text = "No Data"
            self.launchSuccess.textColor = UIColor.gray
        } else if (item.launchSuccess!.boolValue) {
            self.launchSuccess.text = "Successful"
            self.launchSuccess.textColor = UIColor.green
        } else {
            self.launchSuccess.text = "Unsuccessful"
            self.launchSuccess.textColor = UIColor.red
        }
    }
    
}
