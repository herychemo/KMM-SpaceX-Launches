//
//  LaunchesTableViewSource.swift
//  iosApp
//
//  Created by Heriberto Reyes Esparza on 12/12/20.
//  Copyright © 2020 Gray Raccoon. All rights reserved.
//

import UIKit
import shared

class LaunchesTableViewSource: NSObject, UITableViewDataSource, UITableViewDelegate {
    
    private var currentTableView: UITableView? = nil
    var items: [RocketLaunch] = []
    
    func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        return self.items.count
    }
    
    func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        currentTableView = tableView
        
        let cell = tableView.dequeueReusableCell(withIdentifier: LaunchesTableViewCell.KEY, for: indexPath) as! LaunchesTableViewCell
        let item = self.items[indexPath.row]
        cell.populate(item: item)
        return cell
    }
    
    func numberOfSections(in tableView: UITableView) -> Int {
        return 1
    }
    
}
