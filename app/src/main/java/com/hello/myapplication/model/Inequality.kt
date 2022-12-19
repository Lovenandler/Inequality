package com.hello.myapplication.model

class Inequality(){
    fun inequality(a: Double, b: Double): Double {
        return if (!(-b / a).isInfinite() || !(-b/a).isNaN()){
            -b/a
        }else
            0.0
    /*
        val d = DecimalFormatSymbols.getInstance().infinity*/
        //неравенство с дробными числами, вывод decimal
    }
}