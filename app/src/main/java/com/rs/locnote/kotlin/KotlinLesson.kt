package com.rs.locnote.kotlin

import java.lang.StringBuilder

fun main() {
    val fruits = listOf("Apple", "Banana", "Orange", "Pear", "Mongo")
    val result = StringBuilder().apply {
        append("Start eating fruits.\n")
        for (fruit in fruits){
            append(fruit).append("\n")
        }
        append("Ate all fruits.")
    }
    println(result)
}