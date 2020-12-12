package com.grayraccoon.samples.spacexlaunches.shared.cache

import com.grayraccoon.samples.spacexlaunches.shared.entity.Links
import com.grayraccoon.samples.spacexlaunches.shared.entity.Rocket
import com.grayraccoon.samples.spacexlaunches.shared.entity.RocketLaunch


internal class Database(databaseDriverFactory: DatabaseDriverFactory) {
    private val database = AppDatabase(databaseDriverFactory.createDriver())
    private val dbQuery = database.appDatabaseQueries

    internal fun clearDatabase() {
        dbQuery.transaction {
            dbQuery.removeAllLaunches()
            dbQuery.removeAllRockets()
        }
    }

    internal fun getAllLaunches(): List<RocketLaunch> {
        return dbQuery.selectAllLaunchesInfo(::mapLaunchSelecting).executeAsList()
    }

    internal fun createLaunches(launches: List<RocketLaunch>) {
        dbQuery.transaction {
            launches.forEach { launch ->
                insertRocketIfDoesNotExist(launch.rocket)
                insertLaunch(launch)
            }
        }
    }


    private fun mapLaunchSelecting(
        flightNumber: Long,
        missionName: String,
        launchYear: Int,
        rocketId: String,
        details: String?,
        launchSuccess: Boolean?,
        launchDateUTC: String,
        missionPatchUrl: String?,
        articleUrl: String?,
        name: String?,
        type: String?
    ): RocketLaunch {
        return RocketLaunch(
            flightNumber = flightNumber.toInt(),
            missionName = missionName,
            launchYear = launchYear,
            details = details,
            launchDateUTC = launchDateUTC,
            launchSuccess = launchSuccess,
            rocket = Rocket(id = rocketId,
                name = name!!,
                type = type!!),
            links = Links(
                missionPatchUrl = missionPatchUrl,
                articleUrl = articleUrl
            )
        )
    }

    private fun insertLaunch(launch: RocketLaunch) {
        dbQuery.insertLaunch(
            flightNumber = launch.flightNumber.toLong(),
            missionName = launch.missionName,
            launchYear = launch.launchYear,
            rocketId = launch.rocket.id,
            details = launch.details,
            launchSuccess = launch.launchSuccess ?: false,
            launchDateUTC = launch.launchDateUTC,
            missionPatchUrl = launch.links.missionPatchUrl,
            articleUrl = launch.links.articleUrl
        )
    }

    private fun insertRocketIfDoesNotExist(rocket: Rocket) {
        val existingRocket = dbQuery.selectRocketById(rocket.id).executeAsOneOrNull()
        if (existingRocket != null) return
        insertRocket(rocket)
    }

    private fun insertRocket(rocket: Rocket) {
        dbQuery.insertRocket(
            id = rocket.id,
            name = rocket.name,
            type = rocket.type
        )
    }

}
