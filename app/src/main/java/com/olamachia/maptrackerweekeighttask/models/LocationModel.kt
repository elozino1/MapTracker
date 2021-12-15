package com.olamachia.maptrackerweekeighttask.models

import java.lang.reflect.Constructor

class LocationModel {
    var latitude: Double?=null
    var longitude: Double ?=null

    constructor() {}

    constructor(latitude: Double, longitude: Double) {
        this.latitude = latitude
        this.longitude = longitude
    }

}

