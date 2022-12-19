package com.hello.myapplication

import com.hello.myapplication.model.Inequality
import org.junit.Test

import org.junit.Assert.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class InequalityTest {
    @Test
    fun correctDivision(){
        val div = Inequality()
        assertEquals(-0.52,div.inequality(5.0,2.6), 0.00)
        assertEquals(-0.52,div.inequality(-5.0,-2.6), 0.00)
        assertEquals(0.52,div.inequality(-5.0,2.6), 0.00)
        assertEquals(0.52,div.inequality(5.0,-2.6), 0.00)
        assertEquals(0.0,div.inequality(-5.0,0.0), 0.00)
        assertEquals(0.0,div.inequality(5.0,0.0), 0.00)
        assertEquals(0.0,div.inequality(0.0,0.0), 0.00)
        assertEquals(0.0,div.inequality(0.0,6.0), 0.00)
        assertEquals(0.0,div.inequality(0.0,-6.0), 0.00)
    }

}