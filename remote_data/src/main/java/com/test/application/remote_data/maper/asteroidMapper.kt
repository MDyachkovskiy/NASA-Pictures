package com.test.application.remote_data.maper

import com.test.application.core.domain.asteroids.Asteroid
import com.test.application.core.domain.asteroids.AsteroidsList
import com.test.application.core.domain.asteroids.CloseApproachData
import com.test.application.core.domain.asteroids.EstimatedDiameter
import com.test.application.core.domain.asteroids.Kilometers
import com.test.application.core.domain.asteroids.MissDistance
import com.test.application.core.domain.asteroids.NearEarthObjects
import com.test.application.core.domain.asteroids.RelativeVelocity
import com.test.application.remote_data.dto.asteroidList.AsteroidResponse
import com.test.application.remote_data.dto.asteroidList.AsteroidsListResponse
import com.test.application.remote_data.dto.asteroidList.CloseApproachDataResponse
import com.test.application.remote_data.dto.asteroidList.EstimatedDiameterResponse
import com.test.application.remote_data.dto.asteroidList.KilometersResponse
import com.test.application.remote_data.dto.asteroidList.MissDistanceResponse
import com.test.application.remote_data.dto.asteroidList.NearEarthObjectsResponse
import com.test.application.remote_data.dto.asteroidList.RelativeVelocityResponse

fun AsteroidsListResponse.toDomain() : AsteroidsList {
    return AsteroidsList(
        elementCount = this.elementCount,
        nearEarthObjects = this.nearEarthObjects.toDomain()
    )
}

fun NearEarthObjectsResponse.toDomain() : NearEarthObjects {
    return NearEarthObjects(
        asteroids = this.asteroids.map {asteroidResponse ->
            asteroidResponse.toDomain()
        }
    )
}

fun AsteroidResponse.toDomain() : Asteroid {
    return Asteroid(
        closeApproachData = this.closeApproachData.map {closeApproachDataResponse ->
            closeApproachDataResponse.toDomain()
        },
        estimatedDiameter = this.estimatedDiameter.toDomain(),
        id = this.id,
        isPotentiallyHazardousAsteroid = this.isPotentiallyHazardousAsteroid,
        isSentryObject = this.isSentryObject,
        name = this.name
    )
}

fun CloseApproachDataResponse.toDomain() : CloseApproachData {
    return CloseApproachData(
        missDistance = this.missDistance.toDomain(),
        relativeVelocity = this.relativeVelocity.toDomain()
    )
}

fun MissDistanceResponse.toDomain() : MissDistance {
    return MissDistance(
        kilometers = this.kilometers
    )
}

fun RelativeVelocityResponse.toDomain() : RelativeVelocity {
    return RelativeVelocity(
        kilometersPerSecond = this.kilometersPerSecond
    )
}

fun EstimatedDiameterResponse.toDomain() : EstimatedDiameter {
    return EstimatedDiameter(
        kilometers = this.kilometers.toDomain()
    )
}

fun KilometersResponse.toDomain() : Kilometers {
    return Kilometers(
        estimatedDiameterMax = this.estimatedDiameterMax,
        estimatedDiameterMin = this.estimatedDiameterMin
    )
}