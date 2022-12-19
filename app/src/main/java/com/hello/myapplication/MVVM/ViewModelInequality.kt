package com.hello.myapplication.MVVM

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

/*
class ViewModelInequality: ViewModel() {
    val scope = CoroutineScope(Dispatchers.Default)
    val model = Model()
    enum class errors{
        NUMBER1, NUMBER2, OVERFLOW
    }
    private val _resultFlow = MutableStateFlow<Int?>(null)
    val resultFlow = _resultFlow.asStateFlow()
    private val _error = MutableSharedFlow<errors>()
    val errorFlow = _error.asSharedFlow()
    fun inequality(a: String, b: String){
        scope.launch {
            var error: errors? = null
            val num1 = a.toIntOrNull()
            if (num1==null) error=errors.NUMBER1
            else{
                val num2 = b.toIntOrNull()
                if (num2==null) error=errors.NUMBER2
                else{
                    val res = model.inequality(num1,num2)
                    if (res==null) error = errors.OVERFLOW
                    else _resultFlow.emit(res)
                }
            }
            if (error!=null){
                _error.emit(error)
            }

        }
    }

    override fun onCleared() {
        super.onCleared()
        scope.cancel()
    }

}*/
