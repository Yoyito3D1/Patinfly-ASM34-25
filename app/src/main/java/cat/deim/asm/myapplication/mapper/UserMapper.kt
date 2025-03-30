package cat.deim.asm.myapplication.mapper


import cat.deim.asm.myapplication.entity.User
import cat.deim.asm.myapplication.model.UserModel

fun UserModel.toDomain(): User {
    return User(uuid, username, email, isRenting, scooterRented, creationDate, numberOfRents)
}

fun User.toModel(): UserModel {
    return UserModel(uuid, username, email, isRenting, scooterRented, creationDate, numberOfRents)
}
