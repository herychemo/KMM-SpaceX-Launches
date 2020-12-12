package com.grayraccoon.samples.spacexlaunches.shared


class Greeting {
    fun greeting(): String {
        return "Hello, ${Platform().platform}!"
    }
}
