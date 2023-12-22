package com.test.application.remote_data.maper

import com.test.application.core.domain.earthPicture.CentroidCoordinates
import com.test.application.core.domain.earthPicture.EarthPicture
import com.test.application.core.domain.earthPicture.EarthPictureItem
import com.test.application.core.domain.pictureOfTheDay.PictureOfTheDay
import com.test.application.remote_data.dto.earthPictureResponse.CentroidCoordinatesResponse
import com.test.application.remote_data.dto.earthPictureResponse.EarthPictureResponse
import com.test.application.remote_data.dto.earthPictureResponse.EarthPictureResponseItem
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