package com.vnkotlin.basic.coroutines

import kotlinx.coroutines.experimental.async
import kotlinx.coroutines.experimental.delay
import kotlinx.coroutines.experimental.launch
import kotlinx.coroutines.experimental.runBlocking
import java.util.concurrent.atomic.AtomicInteger
import kotlin.concurrent.thread
import kotlin.system.measureTimeMillis

fun main(args:Array<String>){
    println("START VNKotlin Basic - Coroutines")
    launch {
        delay(1000)
        println("Hello from Corountines")
    }
    Thread.sleep(2000)
    println("END VNKotlin Basic - Coroutines")

    val threadTimeMeasure = measureTimeMillis {
        increaseByThread()
    }

    val coroutineTimeMeasure = measureTimeMillis {
        increaseByCoroutines()
    }

    println("Compare Thread vs Coroutines : ${threadTimeMeasure} <> ${coroutineTimeMeasure}")
}


fun increaseByThread(){
    val c = AtomicInteger()
    for (i in 1..1_000_000)
        thread(start = true) {
            c.addAndGet(i)
        }
    println("By Thread : ${c.get()}")
}

fun increaseByCoroutines(){
    val deferred = (1..1_000_000).map { n ->
        async {
            workload(n)
        }
    }
    launch {
        val sum = deferred.sumBy { it.await() }
        println("By Coroutines : ${sum}")
    }
}

suspend fun workload(n: Int): Int {
    return n
}