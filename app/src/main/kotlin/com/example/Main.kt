package com.example

import dev.inmo.krontab.doInfinity
import kotlinx.coroutines.*
import java.time.Instant

suspend fun main() {
    println("OS: ${System.getProperty("os.name")}-${System.getProperty("os.arch")}-${System.getProperty("os.version")}")
    println("JVM: ${System.getProperty("java.specification.version")}-${System.getProperty("java.vm.vendor")}")
    println("Current system time: ${Instant.now()}")
    println("Running krontab...")
    val monitor = CoroutineScope(Dispatchers.Default).launch {
        var i = 0
        while (isActive) {
            println("[$i] Free mem = ${Runtime.getRuntime().freeMemory()}")
            i++
            delay(1000)
        }
    }
    CoroutineScope(Dispatchers.Default).launch {
        doInfinity("0 0 8 /3 *") {
            println(it)
        }
    }.join()

    monitor.join()
}
