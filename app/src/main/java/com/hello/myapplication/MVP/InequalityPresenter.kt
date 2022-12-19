package com.hello.myapplication.MVP

import com.hello.myapplication.InequalityView
import com.hello.myapplication.model.Inequality

class InequalityPresenter {
    private var inequalityModel = Inequality()
    private var inequalityView: InequalityView? = null
    private var lastAnswer: Int? = null
    fun setView(inequalityView: InequalityView?){
        this.inequalityView = inequalityView
        val answer = lastAnswer
        if (answer!=null)
            inequalityView?.showResult(answer)
    }
    /*fun actionInequality(a: String, b: String){
        val a = a.toIntOrNull()
        if (a==null) inequalityView?.showError()
        else {
            val b = b.toIntOrNull()?:0
            val result = inequalityModel.inequality(a, b)
            inequalityView?.showResult(result)
        }
    }*/
}
