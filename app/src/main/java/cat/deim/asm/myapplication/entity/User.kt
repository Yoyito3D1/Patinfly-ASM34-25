package cat.deim.asm.myapplication.entity


import java.util.*

data class User(
    val uuid: UUID,
    val username: String,
    val email: String,
    val isRenting: Boolean,
    val scooterRented: UUID?,
    val creationDate: Date,
    val numberOfRents: Int
)
