package com.mason.resizecalculator.ui.calculator

import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.mason.resizecalculator.databinding.FragmentCalculatorBinding
import com.mason.resizecalculator.databinding.CalculatorLayoutBinding

class CalculatorFragment : Fragment() {
    private var _binding: FragmentCalculatorBinding? = null

    private val calculatorBinding: CalculatorLayoutBinding
        get() = _binding!!.calculator1

    private val calculatorBinding2: CalculatorLayoutBinding?
        get() = _binding!!.calculator2

    private val viewModel: CalculatorViewModel by viewModels()
    private var screenOrientation = Configuration.ORIENTATION_UNDEFINED

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        Log.d(Companion.TAG, "#onCreateView: ")
        _binding = FragmentCalculatorBinding.inflate(inflater, container, false)
        return _binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d(Companion.TAG, "#onViewCreated: ")

        setupCalculator(calculatorBinding, viewModel)
        screenOrientation = Configuration.ORIENTATION_PORTRAIT
        if (resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            setupCalculator(calculatorBinding2, viewModel, Configuration.ORIENTATION_LANDSCAPE)
            screenOrientation = Configuration.ORIENTATION_LANDSCAPE
        }
    }

    /*
    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        Log.e(Companion.TAG, "#onConfigurationChanged: ")

        // 清理當前的 binding
        _binding = null

        // 重新載入佈局
        val inflater = LayoutInflater.from(context)
        val container = view?.parent as? ViewGroup

        if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
            _binding = FragmentCalculatorBinding.inflate(inflater, container, false)
            setupCalculator(_binding?.calculator1, viewModel)
        } else {
//            _bindingLand = FragmentCalculatorLandBinding.inflate(inflater, container, false)
            setupCalculator(_binding?.calculator1, viewModel)
            setupCalculator(_binding?.calculator2, viewModel, 2)
        }

    }
     */

    private fun setupCalculator(
        binding: CalculatorLayoutBinding?,
        viewModel: CalculatorViewModel,
        displayScreen: Int = 1
    ) {
        binding?.apply {
            btn0.setOnClickListener { viewModel.onNumberClick(0, if (displayScreen == 1) 1 else 2) }
            btn1.setOnClickListener { viewModel.onNumberClick(1, if (displayScreen == 1) 1 else 2) }
            btn2.setOnClickListener { viewModel.onNumberClick(2, if (displayScreen == 1) 1 else 2) }
            btn3.setOnClickListener { viewModel.onNumberClick(3, if (displayScreen == 1) 1 else 2) }
            btn4.setOnClickListener { viewModel.onNumberClick(4, if (displayScreen == 1) 1 else 2) }
            btn5.setOnClickListener { viewModel.onNumberClick(5, if (displayScreen == 1) 1 else 2) }
            btn6.setOnClickListener { viewModel.onNumberClick(6, if (displayScreen == 1) 1 else 2) }
            btn7.setOnClickListener { viewModel.onNumberClick(7, if (displayScreen == 1) 1 else 2) }
            btn8.setOnClickListener { viewModel.onNumberClick(8, if (displayScreen == 1) 1 else 2) }
            btn9.setOnClickListener { viewModel.onNumberClick(9, if (displayScreen == 1) 1 else 2) }

            btnPlus.setOnClickListener {
                viewModel.onOperationClick(
                    "+",
                    if (displayScreen == 1) 1 else 2
                )
            }
            btnMinus.setOnClickListener {
                viewModel.onOperationClick(
                    "-",
                    if (displayScreen == 1) 1 else 2
                )
            }
            btnMultiply.setOnClickListener {
                viewModel.onOperationClick(
                    "*",
                    if (displayScreen == 1) 1 else 2
                )
            }
            btnDivide.setOnClickListener {
                viewModel.onOperationClick(
                    "/",
                    if (displayScreen == 1) 1 else 2
                )
            }

            btnEquals.setOnClickListener { viewModel.onEqualsClick(if (displayScreen == 1) 1 else 2) }
            btnClear.setOnClickListener { viewModel.onClearClick(if (displayScreen == 1) 1 else 2) }

            if (displayScreen == 1) {
                viewModel.displayText1.observe(viewLifecycleOwner) { text ->
                    display.text = text
                }
                viewModel.displayFormula1.observe(viewLifecycleOwner) { text ->
                    displayFormula.text = text
                }
            } else {
                viewModel.displayText2.observe(viewLifecycleOwner) { text ->
                    display.text = text
                }
                viewModel.displayFormula2.observe(viewLifecycleOwner) { text ->
                    displayFormula.text = text
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        Log.d(Companion.TAG, "onDestroyView: ")
        _binding = null
    }

    companion object {
        private const val TAG = "CalculatorFragment"
    }
} 