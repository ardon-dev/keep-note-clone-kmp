package com.ardondev.noteskmp

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform