package cat.deim.asm.myapplication.mapper

import cat.deim.asm.myapplication.entity.Bike
import cat.deim.asm.myapplication.model.BikeModel


fun BikeModel.toDomain(): Bike {
    return Bike(uuid, bikeType, isAvailable, batteryLevel, creationDate)
}

fun Bike.toModel(): BikeModel {
    return BikeModel(uuid, bikeType, isAvailable, batteryLevel, creationDate)
}