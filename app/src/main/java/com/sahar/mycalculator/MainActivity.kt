package com.sahar.mycalculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import com.sahar.mycalculator.databinding.ActivityMainBinding
import kotlin.math.ln

class MainActivity : AppCompatActivity() {
     lateinit var binding: ActivityMainBinding
     var currentOperation :Operation? =null
    var operator=""
     var firstNumbre :Double =0.0
     var secondNumber = 0.0
    var hasDot = false
    var hasSign = false
    var scientificFun = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        addCallbacks()
    }

    private fun addCallbacks() {
        binding.clearButton.setOnClickListener {
            clearNumber()
        }
        binding.plusButton.setOnClickListener{
            prepareOperation(Operation.Plus , binding.plusButton.text.toString() ,false)
        }
        binding.minusButton.setOnClickListener{
            prepareOperation(Operation.Minus,binding.minusButton.text.toString(),false)
        }
        binding.multiButton.setOnClickListener{
            prepareOperation(Operation.Mul,binding.multiButton.text.toString(),false)
        }
        binding.divButton.setOnClickListener {
            prepareOperation(Operation.Div,binding.divButton.text.toString(),false)
        }
        binding.mode.setOnClickListener {
            prepareOperation(Operation.Mode,binding.mode.text.toString(),false)
        }
        binding.logButton.setOnClickListener {
            prepareOperation(Operation.LOG,binding.logButton.text.toString(),true)
        }
        binding.sinButton.setOnClickListener {
            prepareOperation(Operation.SIN,binding.sinButton.text.toString(),true)
        }
        binding.cosButton.setOnClickListener {
            prepareOperation(Operation.COS,binding.cosButton.text.toString(),true)
        }
        binding.tanButton.setOnClickListener {
            prepareOperation(Operation.TAN,binding.tanButton.text.toString(),true)
        }

        binding.absolute.setOnClickListener {
        prepareOperation(Operation.absolute,binding.absolute.text.toString(),true)
        }
        binding.squareRoot.setOnClickListener {
            prepareOperation(Operation.ROOT,binding.squareRoot.text.toString(),true)
        }
        binding.lnButton.setOnClickListener {
            prepareOperation(Operation.LN,binding.lnButton.text.toString(),true)
        }
         binding.xPowerYbutton.setOnClickListener {
             prepareOperation(Operation.xPowerY,binding.xPowerYbutton.text.toString(),false)
         }

        binding.result.setOnClickListener {

           val result = doCurrentOperation()


            if (result.toString().substringAfter(".") == "0") {
                binding.textNumber.text = result.toString().substringBefore(".")
            }else binding.textNumber.text = result.toString()


            if (firstNumbre.toString().substringAfter(".")== "0" && secondNumber.toString().substringAfter(".")== "0") {
                binding.previoustext.text = firstNumbre.toString().substringBefore(".") + operator + secondNumber.toString().substringBefore(".")
            }
            else binding.previoustext.text = firstNumbre.toString() + operator + secondNumber.toString()

            if (scientificFun){
                if ( secondNumber.toString().substringAfter(".")== "0") {
                    binding.previoustext.text = operator + "(" + secondNumber.toString().substringBefore(".") +")"
                }
                else binding.previoustext.text = operator + "(" +secondNumber.toString() + ")"
            }

        }

    }

    private fun doCurrentOperation() : Double {

        secondNumber = binding.textNumber.text.toString().substringAfter("(").substringBefore(")").toDouble()

        return when(currentOperation){
            Operation.Plus -> firstNumbre + secondNumber
            Operation.Minus -> firstNumbre - secondNumber
            Operation.Mul -> firstNumbre * secondNumber
            Operation.Div -> firstNumbre / secondNumber
            Operation.Mode -> firstNumbre % secondNumber
            Operation.LOG ->  Math.log10(secondNumber)
            Operation.SIN ->  Math.sin(secondNumber)
            Operation.COS ->  Math.cos(secondNumber)
            Operation.TAN ->  Math.tan(secondNumber)
            Operation.xPowerY ->  Math.pow(firstNumbre,secondNumber)
            Operation.absolute ->  Math.abs(secondNumber)
            Operation.ROOT ->  Math.sqrt(secondNumber)
            Operation.LN ->  ln(secondNumber)


            null -> 0.0
        }
    }

    fun prepareOperation(operation: Operation , operatorShap : String, scientificOper : Boolean){
        firstNumbre = if (binding.textNumber.text == "") 1.0
        else binding.textNumber.text.toString().toDouble()
        clearNumber()
        currentOperation = operation
        operator = operatorShap
        scientificFun = scientificOper
        hasDot = false
        hasSign =false
        if (scientificFun) {
            binding.textNumber.text = operator + "("
        }
    }

    private fun clearNumber() {
        binding.textNumber.text=""
        binding.previoustext.text = ""
        hasDot= false
        hasSign =false
    }

    fun onClickNumber(v : View){
        var newDigit = (v as Button).text.toString()
            if (newDigit !="." || !hasDot){

                if (newDigit !="(-)" ||!hasSign){
                    if (newDigit== "(-)") newDigit = "-"
                    if (newDigit == "π") newDigit = 3.1415926535.toString()

                    val oldDigit = binding.textNumber.text.toString()
                    val newTextNumber = oldDigit + newDigit
                    binding.textNumber.text = newTextNumber
                }
                if (newDigit== ".") hasDot =true
            }

        hasSign =true
    }
}