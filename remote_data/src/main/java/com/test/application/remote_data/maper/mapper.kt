package com.test.application.remote_data.maper

import com.test.application.core.domain.earthPicture.CentroidCoordinates
import com.test.application.core.domain.earthPicture.EarthPicture
import com.test.application.core.domain.earthPicture.EarthPictureItem
import com.test.application.core.domain.marsPicture.MarsCamera
import com.test.application.core.domain.marsPicture.MarsPhoto
import com.test.application.core.domain.marsPicture.MarsPicture
import com.test.application.core.domain.marsPicture.Rover
import com.test.application.core.domain.pictureOfTheDay.PictureOfTheDay
import com.test.application.remote_data.dto.earthPictureResponse.CentroidCoordinatesResponse
import com.test.application.remote_data.dto.earthPictureResponse.EarthPictureResponse
import com.test.application.remote_data.dto.earthPictureResponse.EarthPictureResponseItem
import com.test.application.remote_data.dto.marsPictureResponse.CameraResponse
import com.test.application.remote_data.dto.marsPictureResponse.MarsPictureResponse
import com.test.application.remote_data.dto.marsPictureResponse.PhotoResponse
import com.test.application.remote_data.dto.marsPictureResponse.RoverResponse
import com.test.application.remote_data.dto.pictureOfTheDay.PictureOfTheDayResponse


fun EarthPictureResponse.toDomain(): EarthPicture {
    val earthPicture = EarthPicture()
    this.forEach { responseItem ->
        earthPicture.add(responseItem.toDomain())
    }
    return earthPicture
}
fun EarthPictureResponseItem.toDomain(): EarthPictureItem {
    return EarthPictureItem(
        caption = this.caption,
        centroidCoordinates = this.centroidCoordinates?.toDomain(),
        date = this.date,
        image = this.image
    )
}

fun CentroidCoordinatesResponse.toDomain(): CentroidCoordinates {
    return CentroidCoordinates(
        lat = this.lat,
        lon = this.lon
    )
}

fun PictureOfTheDayResponse.toDomain(): PictureOfTheDay {
    return PictureOfTheDay(
        copyright = this.copyright,
        date = this.date,
        explanation = this.explanation,
        hdurl = this.hdurl,
        mediaType = this.mediaType,
        serviceVersion = this.serviceVersion,
        title = this.title,
        url = this.url
    )
}

fun MarsPictureResponse.toDomain(): MarsPicture {
    return MarsPicture(
        photos = this.photos.map { photoResponse ->
            photoResponse.toDomain()
        }
    )
}

fun PhotoResponse.toDomain() : MarsPhoto {
    return MarsPhoto(
        camera = this.camera.toDomain(),
        earthDate = this.earthDate,
        id = this.id,
        imgSrc = this.imgSrc,
        rover = this.rover.toDomain(),
        sol = this.sol
    )
}

fun CameraResponse.toDomain() : MarsCamera {
    return MarsCamera(
        fullName = this.fullName,
        id = this.id,
        name = this.name,
        roverId = this.roverId
    )
}

fun RoverResponse.toDomain() : Rover {
    return Rover(
        id = this.id,
        landingDate = this.landingDate,
        launchDate = this.launchDate,
        name = this.name,
        status = this.status
    )
}