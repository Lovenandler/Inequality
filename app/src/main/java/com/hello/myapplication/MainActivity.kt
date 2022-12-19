package com.hello.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.hello.myapplication.MVP.InequalityPresenter
import com.hello.myapplication.model.Inequality
import com.hello.myapplication.databinding.ActivityMainBinding
import com.hello.myapplication.room.InequalityRoomModel
import com.hello.myapplication.room.NumbersDatabase
import kotlinx.coroutines.launch
import java.text.DecimalFormatSymbols

interface InequalityView{
    fun showResult(result: Int)
    fun showError()
}
class MainActivity : AppCompatActivity(), InequalityView {
    private lateinit var binding: ActivityMainBinding
    private val division = Inequality()
    /*val viewModel: ViewModelInequality by viewModels()
    val scope = CoroutineScope(Dispatchers.Default)*/
    private val presenter = InequalityPresenter()
    private var numbersList = mutableListOf<InequalityRoomModel>()
    lateinit var resultView: TextView
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString(RESULT_KEY, resultView.text.toString())
    }

    override fun onStart() {
        super.onStart()
        presenter.setView(this)
    }

    override fun onStop() {
        super.onStop()
        presenter.setView(null)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        resultView = binding.answer
        /*scope.launch {
            viewModel.resultFlow.collect{
                launch(Dispatchers.Main) {
                    binding.answer.text = it?.toString() ?: ""
                }
            }
        }
        scope.launch {
            viewModel.errorFlow.collect{
                launch(Dispatchers.Main) {
                    val str = when(it){ //через строки сделать
                        ViewModelInequality.errors.NUMBER1 -> "Error in number 1"
                        ViewModelInequality.errors.NUMBER2 -> "Error in number 2"
                        ViewModelInequality.errors.OVERFLOW -> "Overflow"
                    }
                    Toast.makeText(this@MainActivity, str, Toast.LENGTH_LONG).show()
                }
            }
        }*/
        lifecycleScope.launch {
            val numbersList = NumbersDatabase(this@MainActivity).getNumbersDao().getAllData()
            val firstNumber = findViewById<EditText>(R.id.EnterA)
            val secondNumber = findViewById<EditText>(R.id.EnterB)
            if (numbersList.isNotEmpty()){
                firstNumber.apply {
                    val firstNum = numbersList.get(0).firstNum
                    firstNumber.setText(firstNum.toString())
                }
                secondNumber.apply {
                    val secondNum = numbersList.get(0).secondNum
                    secondNumber.setText(secondNum.toString())
                }
                resultView.apply {
                    val res = numbersList.get(0).result
                    resultView.text = res.toString()
                }

            }
        }

        binding.AnswerBtn.setOnClickListener{
            //val numbersList = NumbersDatabase(this@MainActivity).getNumbersDao().getAllData()
            NumbersDatabase(this@MainActivity).getNumbersDao().deleteNumbers()
            val a = binding.EnterA.text.toString()
            val b = binding.EnterB.text.toString()
            if (a.isNotEmpty() && a.toDoubleOrNull()!=null && b.isNotEmpty() && b.toDoubleOrNull()!=null){
                //перевод в число первого edittext
                //сравнить с нулем
                val num = a.toDoubleOrNull()
                if (a.toDoubleOrNull() == 0.0 || b.toDoubleOrNull() == 0.0){
                    binding.answer.text = "0.0"
                    val number = InequalityRoomModel(id = 0, firstNum = num, secondNum = b.toDoubleOrNull())
                    NumbersDatabase(this@MainActivity).getNumbersDao().addNumbers(number)
                    finish()
                    lifecycleScope.launch {
                        val numbersList = NumbersDatabase(this@MainActivity).getNumbersDao().getAllData()
                        val firstNumber = findViewById<EditText>(R.id.EnterA)
                        val secondNumber = findViewById<EditText>(R.id.EnterB)
                        firstNumber.apply {
                            val firstNum = numbersList.get(0).firstNum
                            firstNumber.setText(firstNum.toString())
                        }
                        secondNumber.apply {
                            val secondNum = numbersList.get(0).secondNum
                            secondNumber.setText(secondNum.toString())
                        }
                        resultView.apply {
                            val res = numbersList.get(0).result
                            resultView.text = res.toString()
                        }
                    }
                }else{

                val infinity = DecimalFormatSymbols.getInstance().infinity
                var sign = ""
                if (num != null){
                    if (num > 0) {
                        sign = "-"
                        val ans = linearInequality(a.toDoubleOrNull(), b.toDoubleOrNull())
                        val ansTxt = "$sign$infinity,$ans"
                        resultView.text = ansTxt
                        val number = InequalityRoomModel(id = 0, firstNum = num, secondNum = b.toDoubleOrNull(), result = ans)
                        NumbersDatabase(this@MainActivity).getNumbersDao().addNumbers(number)
                        //finish()
                        lifecycleScope.launch {
                            val numbersList = NumbersDatabase(this@MainActivity).getNumbersDao().getAllData()
                            val firstNumber = findViewById<EditText>(R.id.EnterA)
                            val secondNumber = findViewById<EditText>(R.id.EnterB)
                            firstNumber.apply {
                                val firstNum = numbersList.get(0).firstNum
                                firstNumber.setText(firstNum.toString())
                            }
                            secondNumber.apply {
                                val secondNum = numbersList.get(0).secondNum
                                secondNumber.setText(secondNum.toString())
                            }
                            resultView.apply {
                                val res = "$sign$infinity,${numbersList.get(0).result}"
                                resultView.text = res
                            }
                        }
                    }else if(num<0){
                        sign = "+"
                        val ans = linearInequality(a.toDoubleOrNull(), b.toDoubleOrNull()).toString()+","+sign+infinity
                        resultView.text = ans
                        val number = InequalityRoomModel(id = 0, firstNum = num, secondNum = b.toDoubleOrNull(), result = ans.toDoubleOrNull())
                        NumbersDatabase(this@MainActivity).getNumbersDao().addNumbers(number)
                        //finish()
                        lifecycleScope.launch {
                            val numbersList = NumbersDatabase(this@MainActivity).getNumbersDao().getAllData()
                            val firstNumber = findViewById<EditText>(R.id.EnterA)
                            val secondNumber = findViewById<EditText>(R.id.EnterB)
                            firstNumber.apply {
                                val firstNum = numbersList.get(0).firstNum
                                firstNumber.setText(firstNum.toString())
                            }
                            secondNumber.apply {
                                val secondNum = numbersList.get(0).secondNum
                                secondNumber.setText(secondNum.toString())
                            }
                            resultView.apply {
                                val res = numbersList.get(0).result
                                resultView.text = res.toString()
                            }
                        }
                    }
                    if(binding.answer.text.toString().toDoubleOrNull()?.isNaN() == true){
                        val ans = "0.0"
                        resultView.text = ans
                        val number = InequalityRoomModel(id = 0, firstNum = num, secondNum = b.toDoubleOrNull(), result = ans.toDoubleOrNull())
                        NumbersDatabase(this@MainActivity).getNumbersDao().addNumbers(number)
                        //finish()
                        lifecycleScope.launch {
                            val numbersList = NumbersDatabase(this@MainActivity).getNumbersDao().getAllData()
                            val firstNumber = findViewById<EditText>(R.id.EnterA)
                            val secondNumber = findViewById<EditText>(R.id.EnterB)
                            firstNumber.apply {
                                val firstNum = numbersList.get(0).firstNum
                                firstNumber.setText(firstNum.toString())
                            }
                            secondNumber.apply {
                                val secondNum = numbersList.get(0).secondNum
                                secondNumber.setText(secondNum.toString())
                            }
                            resultView.apply {
                                val res = numbersList.get(0).result
                                resultView.text = res.toString()
                            }
                        }
                    }else if (binding.answer.text.toString().toDoubleOrNull()?.isInfinite() == true) {
                        val ans = "0.0"
                        resultView.text = ans
                        val number = InequalityRoomModel(id = 0, firstNum = num, secondNum = b.toDoubleOrNull(), result = ans.toDoubleOrNull())
                        NumbersDatabase(this@MainActivity).getNumbersDao().addNumbers(number)
                        //finish()
                        lifecycleScope.launch {
                            val numbersList = NumbersDatabase(this@MainActivity).getNumbersDao().getAllData()
                            val firstNumber = findViewById<EditText>(R.id.EnterA)
                            val secondNumber = findViewById<EditText>(R.id.EnterB)
                            firstNumber.apply {
                                val firstNum = numbersList.get(0).firstNum
                                firstNumber.setText(firstNum.toString())
                            }
                            secondNumber.apply {
                                val secondNum = numbersList.get(0).secondNum
                                secondNumber.setText(secondNum.toString())
                            }
                            resultView.apply {
                                val res = numbersList.get(0).result
                                resultView.text = res.toString()
                            }
                        }
                    }
                }
                }

                //если больше то sign= "+"
                //если меньше то sign = "-"
                //перед ним sign

                //проверка на 0 и значение второго числа -> нет решений или бесконечное множество решений
                /*binding.answer.text = *//*division.inequality(a, b)*//*linearInequality(a.toDoubleOrNull(), b.toDoubleOrNull()).toString()*/
            }
            //viewModel.inequality(a,b)
            /*presenter.actionInequality(
                binding.EnterA.text.toString(),
                binding.EnterB.text.toString()
            )*/
         }
    }

    /*override fun onDestroy() {
        super.onDestroy()
        scope.cancel()
    }*/

    override fun showError() {
        Toast.makeText(this,"number incorrect", Toast.LENGTH_LONG).show()
    }

    override fun showResult(result: Int) {
        binding.answer.text = result.toString()
    }
    private fun linearInequality(
        a: Double?,
        b: Double?
    ): Double {
        return -b!! / a!!
    }
    companion object{
        const val RESULT_KEY = "RESULT"
    }

}