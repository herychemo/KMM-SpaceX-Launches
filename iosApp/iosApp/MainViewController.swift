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

    @IBOutlet var LaunchesTableView: UITableView!
    
    var refreshControl = UIRefreshControl()
    
    private let spaceXSdk: SpaceXSdk = SpaceXSdk(databaseDriverFactory: DatabaseDriverFactory())
    
    private var items: [RocketLaunch] = []
    private var launchesTableViewSource: LaunchesTableViewSource = LaunchesTableViewSource()
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
        LaunchesTableView.register(LaunchesTableViewCell.Nib, forCellReuseIdentifier: LaunchesTableViewCell.KEY)
        LaunchesTableView.dataSource = self.launchesTableViewSource
        LaunchesTableView.rowHeight = UITableView.automaticDimension
        LaunchesTableView.estimatedRowHeight = 190
        
        refreshControl.attributedTitle = NSAttributedString(string: "Pull to refresh")
        refreshControl.addTarget(self, action: #selector(self.refresh(_:)), for: .valueChanged)
        LaunchesTableView.addSubview(refreshControl)
        
        LaunchesTableView.reloadData()
    }
    
    override func viewDidAppear(_ animated: Bool) {
        reloadData(needReload: false)
    }
    
    @objc func refresh(_ sender: AnyObject) {
        self.reloadData(needReload: true)
    }
    
    private func reloadData(needReload: Bool = false) {
        if !refreshControl.isRefreshing {
            refreshControl.beginRefreshing()
            
            let offset = CGPoint.init(x: 0, y: -refreshControl.frame.size.height)
            self.LaunchesTableView.setContentOffset(offset, animated: true)
        }
        spaceXSdk.getLaunches(forceReload: needReload, completionHandler: { launches, error in
            if let launches = launches {
                self.items.removeAll()
                self.items += launches
                self.launchesTableViewSource.items = self.items
                self.LaunchesTableView.reloadData()
            } else {
                let errorMessage: String = error?.localizedDescription ?? "Error"
                // TODO: Display error.
            }
            self.refreshControl.endRefreshing()
        })
    }
    
}

