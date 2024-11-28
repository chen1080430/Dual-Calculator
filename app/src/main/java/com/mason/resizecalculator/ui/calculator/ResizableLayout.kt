package com.mason.resizecalculator.ui.calculator

import android.content.Context
import android.util.AttributeSet
import androidx.constraintlayout.widget.ConstraintLayout
import android.view.MotionEvent
import android.view.View
import android.annotation.SuppressLint
import android.graphics.Rect
import androidx.constraintlayout.widget.ConstraintSet
import androidx.customview.widget.ViewDragHelper
import com.mason.resizecalculator.R

class ResizableLayout @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    interface ResizeCallback {
        fun onDragStarted()
        fun onDragEnded()
        fun onLayoutClicked()
    }

    var resizeCallback: ResizeCallback? = null

    private var dragHelper: ViewDragHelper
    private var currentRatio = 0.46f
    private val minRatio = 0.2f
    private val maxRatio = 0.8f
    private var screenWidth = 0
    private var lastDragPositionX = 0
    private var switchWeight = 8
    private var initialTouchX = 0f
    private var initialTouchY = 0f
    private lateinit var calculator1: View
    private lateinit var calculator2: View
    private lateinit var divider: View
    private lateinit var btnLeft: View
    private lateinit var btnRight: View
    private lateinit var btnResize: View

    init {
        dragHelper = ViewDragHelper.create(this, DragCallback())
    }

    override fun onFinishInflate() {
        super.onFinishInflate()
        calculator1 = findViewById(R.id.calculator_left)
        calculator2 = findViewById(R.id.calculator_right)
        divider = findViewById(R.id.divider_resize)
        btnLeft = findViewById(R.id.button_to_left)
        btnRight = findViewById(R.id.button_to_right)
        btnResize = findViewById(R.id.btn_resize)
    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)
        if (changed && this::btnResize.isInitialized) {
            val btnLocationXY = intArrayOf(0, 0)
            val layoutLocationXY = intArrayOf(0, 0)
            btnResize.getLocationOnScreen(btnLocationXY)
            getLocationOnScreen(layoutLocationXY)
            lastDragPositionX = btnLocationXY[0] - layoutLocationXY[0] + btnResize.width / 2
        }
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        screenWidth = w
    }

    override fun onInterceptTouchEvent(ev: MotionEvent): Boolean {
        val locationXY = intArrayOf(0, 0)
        this.getLocationOnScreen(locationXY)
        when (ev.action) {
            MotionEvent.ACTION_DOWN -> {
                initialTouchX = ev.x + locationXY[0]
                initialTouchY = ev.y + locationXY[1]
                resizeCallback?.onLayoutClicked()
            }
        }
        return dragHelper.shouldInterceptTouchEvent(ev)
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent): Boolean {
        dragHelper.processTouchEvent(event)
        return true
    }

    private inner class DragCallback : ViewDragHelper.Callback() {

        override fun tryCaptureView(child: View, pointerId: Int): Boolean {
            if (child.id == R.id.calculator_switch_layout) {
                val rect = Rect()
                btnResize.getGlobalVisibleRect(rect)

                val isInResizeButton = rect.contains(
                    initialTouchX.toInt(),
                    initialTouchY.toInt()
                )
                return isInResizeButton
            }
            return false
        }

        override fun onViewDragStateChanged(state: Int) {
            setCalculatorDraggingState(state == ViewDragHelper.STATE_DRAGGING)
            super.onViewDragStateChanged(state)
        }

        override fun clampViewPositionHorizontal(child: View, left: Int, dx: Int): Int {
            val minX = (screenWidth * minRatio).toInt()
            val maxX = (screenWidth * maxRatio).toInt() - child.width / 2
            return left.coerceIn(minX, maxX)
        }

        override fun onViewPositionChanged(
            changedView: View,
            left: Int,
            top: Int,
            dx: Int,
            dy: Int
        ) {
            var centerX = changedView.width / 2 + left
            if (centerX != lastDragPositionX) {
                lastDragPositionX = centerX
            }
        }

        override fun onViewCaptured(capturedChild: View, activePointerId: Int) {
            super.onViewCaptured(capturedChild, activePointerId)
            resizeCallback?.onDragStarted()
        }

        override fun onViewReleased(releasedChild: View, xvel: Float, yvel: Float) {
            super.onViewReleased(releasedChild, xvel, yvel)
            resizeCallback?.onDragEnded()
            updateCalculatorsSize()
        }

        override fun getViewHorizontalDragRange(child: View): Int = (screenWidth * 0.1f).toInt()
    }

    private fun setCalculatorDraggingState(dragging: Boolean) {
        calculator1.alpha = if (dragging) 0.5f else 1.0f
        calculator2.alpha = if (dragging) 0.5f else 1.0f
        divider.visibility = if (dragging) View.VISIBLE else View.INVISIBLE
        btnLeft.visibility = if (dragging) View.INVISIBLE else View.VISIBLE
        btnRight.visibility = if (dragging) View.INVISIBLE else View.VISIBLE
        btnResize.visibility = if (dragging) View.INVISIBLE else View.VISIBLE
    }

    private fun updateCalculatorsSize() {
        val constraintSet = ConstraintSet()
        constraintSet.clone(this)
        currentRatio = lastDragPositionX.toFloat() / screenWidth

        val calculatorWeight = 100 - switchWeight
        val leftWeight = (currentRatio * calculatorWeight).toInt()
        val rightWeight = ((1 - currentRatio) * calculatorWeight).toInt()

        constraintSet.setHorizontalWeight(R.id.calculator_left, leftWeight.toFloat())
        constraintSet.setHorizontalWeight(R.id.calculator_switch_layout, switchWeight.toFloat())
        constraintSet.setHorizontalWeight(R.id.calculator_right, rightWeight.toFloat())

        constraintSet.applyTo(this)
    }

    companion object {
        private const val TAG = "ResizableLayout"
    }
}