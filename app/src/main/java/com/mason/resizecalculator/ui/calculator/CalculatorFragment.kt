package com.mason.resizecalculator.ui.calculator

import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.mason.resizecalculator.R
import com.mason.resizecalculator.databinding.CalculatorLayoutBinding
import com.mason.resizecalculator.databinding.CalculatorSwitchLayoutBinding
import com.mason.resizecalculator.databinding.FragmentCalculatorBinding
import com.tomergoldst.tooltips.ToolTip
import com.tomergoldst.tooltips.ToolTipsManager

class CalculatorFragment : Fragment(), ResizableLayout.ResizeCallback {
    private var _binding: FragmentCalculatorBinding? = null

    private val calculatorBindingLeft: CalculatorLayoutBinding
        get() = _binding!!.calculatorLeft

    private val calculatorBindingRight: CalculatorLayoutBinding?
        get() = _binding!!.calculatorRight

    private val calculatorSwitchLayoutBinding: CalculatorSwitchLayoutBinding?
        get() = _binding!!.calculatorSwitchLayout

    private val viewModel: CalculatorViewModel by viewModels()
    private var screenOrientation = Configuration.ORIENTATION_UNDEFINED

    private var tooltipsManager: ToolTipsManager? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCalculatorBinding.inflate(inflater, container, false)
        return _binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupCalculator(calculatorBindingLeft, viewModel)
        screenOrientation = Configuration.ORIENTATION_PORTRAIT
        if (resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            setupCalculator(calculatorBindingRight, viewModel, CalculatorId.RIGHT)
            setupCalculatorSwitchLayout(calculatorSwitchLayoutBinding, viewModel)
            screenOrientation = Configuration.ORIENTATION_LANDSCAPE
            (_binding?.root as? ResizableLayout)?.resizeCallback = this

            view.post {
                popupResize()
            }
        }
    }

    private fun popupResize() {
        calculatorSwitchLayoutBinding?.btnResize?.let { btnResize ->
            _binding?.root?.let { rootView ->
                tooltipsManager = ToolTipsManager()
                val builder = ToolTip.Builder(
                    requireContext(),
                    btnResize,
                    rootView as ViewGroup,
                    resources.getString(R.string.drag_to_resize),
                    ToolTip.POSITION_ABOVE
                )

                builder.setBackgroundColor(
                    resources.getColor(
                        android.R.color.holo_green_light,
                        null
                    )
                )
                val view = tooltipsManager?.show(builder.build())
                // constraintSet need every child view have their own id
                view?.id = getString(R.string.resize_tooltip).hashCode()
            }
        } ?: run {
            Log.e(TAG, "popupResize: btnResize or rootView is null")
        }
    }

    private fun dismissPopup() {
        tooltipsManager?.run {
            findAndDismiss(calculatorSwitchLayoutBinding?.btnResize)
        }
    }

    private fun setupCalculatorSwitchLayout(
        calculatorSwitchLayoutBinding: CalculatorSwitchLayoutBinding?,
        viewModel: CalculatorViewModel
    ) {
        calculatorSwitchLayoutBinding?.apply {
            buttonToLeft.setOnClickListener { viewModel.onMoveToLeftClick() }
            buttonToRight.setOnClickListener { viewModel.onMoveToRightClick() }
            btnResize.setOnClickListener { dismissPopup() }
        }
    }

    private fun setupCalculator(
        binding: CalculatorLayoutBinding?,
        viewModel: CalculatorViewModel,
        calculatorId: CalculatorId = CalculatorId.LEFT
    ) {

        binding?.apply {
            btn0.setOnClickListener { viewModel.onNumberClick(0, calculatorId) }
            btn1.setOnClickListener { viewModel.onNumberClick(1, calculatorId) }
            btn2.setOnClickListener { viewModel.onNumberClick(2, calculatorId) }
            btn3.setOnClickListener { viewModel.onNumberClick(3, calculatorId) }
            btn4.setOnClickListener { viewModel.onNumberClick(4, calculatorId) }
            btn5.setOnClickListener { viewModel.onNumberClick(5, calculatorId) }
            btn6.setOnClickListener { viewModel.onNumberClick(6, calculatorId) }
            btn7.setOnClickListener { viewModel.onNumberClick(7, calculatorId) }
            btn8.setOnClickListener { viewModel.onNumberClick(8, calculatorId) }
            btn9.setOnClickListener { viewModel.onNumberClick(9, calculatorId) }

            btnPlus.setOnClickListener { viewModel.onOperationClick("+", calculatorId) }
            btnMinus.setOnClickListener { viewModel.onOperationClick("-", calculatorId) }
            btnMultiply.setOnClickListener { viewModel.onOperationClick("*", calculatorId) }
            btnDivide.setOnClickListener { viewModel.onOperationClick("/", calculatorId) }

            btnSign.setOnClickListener { viewModel.onSignClick(calculatorId) }
            btnPercent.setOnClickListener { viewModel.onPercentClick(calculatorId) }
            btnDelete.setOnClickListener { viewModel.onDeleteClick(calculatorId) }
            btnDot.setOnClickListener { viewModel.onDecimalClick(calculatorId) }
            btnEquals.setOnClickListener { viewModel.onEqualsClick(calculatorId) }
            btnClear.setOnClickListener { viewModel.onClearClick(calculatorId) }

            if (calculatorId == CalculatorId.LEFT) {
                viewModel.displayResultLeft.observe(viewLifecycleOwner) { display.text = it }
                viewModel.displayFormulaLeft.observe(viewLifecycleOwner) {
                    displayFormula.text = it
                }
            } else {
                viewModel.displayResultRight.observe(viewLifecycleOwner) { display.text = it }
                viewModel.displayFormulaRight.observe(viewLifecycleOwner) {
                    displayFormula.text = it
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        (_binding?.root as? ResizableLayout)?.resizeCallback = null
        dismissPopup()
        _binding = null
    }

    override fun onDragStarted() {
        dismissPopup()
    }

    override fun onDragEnded() {
    }

    override fun onLayoutClicked() {
        dismissPopup()
    }

    companion object {
        private const val TAG = "CalculatorFragment"
    }
}
