package stable.devs.cross

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform