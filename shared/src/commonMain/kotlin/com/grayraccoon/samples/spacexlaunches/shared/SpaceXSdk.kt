package com.grayraccoon.samples.spacexlaunches.shared

import com.grayraccoon.samples.spacexlaunches.shared.cache.Database
import com.grayraccoon.samples.spacexlaunches.shared.cache.DatabaseDriverFactory
import com.grayraccoon.samples.spacexlaunches.shared.entity.RocketLaunch
import com.grayraccoon.samples.spacexlaunches.shared.network.SpaceXApi

class SpaceXSdk (databaseDriverFactory: DatabaseDriverFactory) {
    private val database = Database(databaseDriverFactory)
    private val api = SpaceXApi()

    @Throws(Exception::class)
    suspend fun getLaunches(forceReload: Boolean): List<RocketLaunch> {
        val cachedLaunches: List<RocketLaunch> = database.getAllLaunches()
        return if (cachedLaunches.isNotEmpty() && !forceReload)
            cachedLaunches
        else
            api.getAllLaunches().also {
                database.clearDatabase()
                database.createLaunches(it)
            }
    }

}
