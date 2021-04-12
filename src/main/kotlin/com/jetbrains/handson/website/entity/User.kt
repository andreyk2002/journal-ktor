package com.jetbrains.handson.website.entity

val inMemoryUsers = mutableListOf(
   User("admin", "admin", Role.ADMIN),
    User("1234", "tester", Role.USER)
)

class User(val password:String, val name:String, val role:Role) {
}