package com.hello.myapplication.model

class Model {
    fun inequality(a: Int, b: Int): Int? {
        return try {
            -b / a
        } catch (e: ArithmeticException) {
            null
        }
        //неравенство с дробными числами, вывод decimal
    }
}