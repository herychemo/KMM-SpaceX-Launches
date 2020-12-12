package com.grayraccoon.samples.spacexlaunches.shared.network

import com.grayraccoon.samples.spacexlaunches.shared.entity.RocketLaunch
import io.ktor.client.HttpClient
import io.ktor.client.features.json.JsonFeature
import io.ktor.client.features.json.serializer.KotlinxSerializer
import io.ktor.client.request.*
import kotlinx.serialization.json.Json

class SpaceXApi {

    companion object {
        private const val LAUNCHES_ENDPOINT: String = "https://api.spacexdata.com/v3/launches"
    }

    private var httpClient: HttpClient = HttpClient {
        install(JsonFeature) {
            val json = Json { ignoreUnknownKeys = true }
            serializer = KotlinxSerializer(json)
        }
    }

    suspend fun getAllLaunches(): List<RocketLaunch> {
        return httpClient.get(LAUNCHES_ENDPOINT)
    }

}