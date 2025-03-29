package cat.deim.asm.myapplication.mapper

import cat.deim.asm.myapplication.entity.Bike
import cat.deim.asm.myapplication.model.BikeModel


fun BikeModel.toDomain(): Bike {
    return Bike(id = uuid, brand = marca, isActive = activo == 1)
}

fun Bike.toModel(): BikeModel {
    return BikeModel(id, brand, if (isActive) 1 else 0)
}