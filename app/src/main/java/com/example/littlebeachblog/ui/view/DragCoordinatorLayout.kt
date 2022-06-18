package com.example.littlebeachblog.ui.view

import android.animation.FloatEvaluator
import android.animation.ObjectAnimator
import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.animation.doOnEnd
import androidx.lifecycle.*
import com.example.littlebeachblog.ui.view.listener.AppBarStateChangeListener
import net.mikaelzero.mojito.tools.dp2px
import kotlin.math.abs

/**
 * Created by AK on 2022/2/23 21:21.
 * God bless my code!
 * 拖动 -> 只能向下拖拽
 * +回弹+透明度变化
 */
class DragCoordinatorLayout @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : CoordinatorLayout(context, attrs), DefaultLifecycleObserver {

    private val animaBack = ObjectAnimator()        //动画 -> 收缩
    private val animaExpand = ObjectAnimator()      //动画 -> 展开
    private val animaAlpha = ObjectAnimator.ofFloat(1f)       //动效 -> 透明度: 用于元素共享恢复时的处理
    private val floatEvaluator = FloatEvaluator()

    private var startY = 0f      //开始的位置
    private var distance = 0f    //拖动的相对距离

    private var maxDrag = dp2px(276 - (2 * 56))     //最大拖动距离
    private var mDragRate = 0.5f   //阻尼效果：显示下拉高度/手指真实下拉高度
    private var currentAlpha = 0f    //当前透明度

    private var toolBarIsState = AppBarStateChangeListener.State.EXPANDED    //toolBar的状态 -> 完全展开时整体下拉
    private var onRelease: (() -> Unit)? = null
    private var onAlphaChanged: ((alpha: Float) -> Unit)? = null

    /**
     * 初始化动画/事件
     */
    init {
        animaBack.duration = 300
        animaExpand.duration = 300
        animaAlpha.duration = 500
        animaBack.addUpdateListener {
            onAlphaChanged?.invoke(floatEvaluator.evaluate(it.animatedFraction, currentAlpha, 0f))
            translationY = floatEvaluator.evaluate(it.animatedFraction, distance, 0f)
        }
        animaExpand.addUpdateListener {
            onAlphaChanged?.invoke(floatEvaluator.evaluate(it.animatedFraction, currentAlpha, 1f))
            translationY = floatEvaluator.evaluate(it.animatedFraction, distance, maxDrag)
        }
        animaExpand.doOnEnd {
            currentAlpha = 1f
            onRelease?.invoke()
        }
        animaAlpha.addUpdateListener {
            onAlphaChanged?.invoke(floatEvaluator.evaluate(it.animatedFraction, currentAlpha, 0f))
        }
    }

    override fun dispatchTouchEvent(ev: MotionEvent): Boolean {
        if (toolBarIsState == AppBarStateChangeListener.State.COLLAPSED) {
            return super.dispatchTouchEvent(ev)
        }
        when (ev.action) {
            MotionEvent.ACTION_DOWN -> {
                if (toolBarIsState == AppBarStateChangeListener.State.EXPANDED) startY = ev.rawY
                animaBack.cancel()
            }

            MotionEvent.ACTION_MOVE -> {
                distance = (ev.rawY - startY) * mDragRate
                if (isNeedMove()) {
                    if (startY == 0f) startY = ev.rawY
                    translationY = distance
                    currentAlpha = distance / maxDrag
                    onAlphaChanged?.invoke(currentAlpha)
                    if (ev.rawY > startY) return true
                }
            }

            MotionEvent.ACTION_UP -> {
                if (abs(ev.rawY - startY) > 20) {
                    ev.action = MotionEvent.ACTION_CANCEL
                }
                startY = 0f
                if (distance >= maxDrag / 2) {
                    if (isNeedMove()) {
                        animationExpand()
                    }
                } else {
                    if (isNeedMove()) {
                        animationBack()
                    }
                }
            }
        }
        return super.dispatchTouchEvent(ev)
    }

    override fun canScrollVertically(direction: Int): Boolean {
        return true
    }

    /**
     * 是否需要移动
     */
    private fun isNeedMove() =
        distance >= 0 && distance <= maxDrag && toolBarIsState == AppBarStateChangeListener.State.EXPANDED

    /**
     * 当生命周期为onStop时 -> 重置状态
     * 将在onStop的时候自动折叠收缩背景
     */
    override
    fun onStop(owner: LifecycleOwner) {
        super.onStop(owner)
        translationY = 0f
        onAlphaChanged?.invoke(currentAlpha)    //背景可见 -> 配合元素共享
    }

    /**
     * 当生命周期为onResume时 -> 背景从可见到隐藏的过渡
     */
    override fun onResume(owner: LifecycleOwner) {
        super.onResume(owner)
        animaAlpha.start()
    }

    /**
     * 开启移动动画 -> 回弹
     */
    private fun animationBack() {
        animaBack.setFloatValues(distance)
        animaBack.start()
    }

    /**
     * 开启移动动画 -> 展开
     */
    private fun animationExpand() {
        animaExpand.setFloatValues(distance)
        animaExpand.start()
    }


    /**
     * 透明度变化监听
     */
    fun setOnAlphaChangeListener(block: (Float) -> Unit) {
        this.onAlphaChanged = block
    }

    /**
     * 当手指释放,且到达临界点时的回调
     */
    fun setOnReleaseListener(block: () -> Unit) {
        this.onRelease = block
    }

    /**
     * 设置回弹动画持续时间
     */
    fun setAnimDuration(duration: Long) {
        this.animaBack.duration = duration
        this.animaExpand.duration = duration
    }

    /**
     * 设置最大拖拽距离
     */
    fun setMaxDrag(dp: Int) {
        this.maxDrag = dp2px(dp)
    }

    /**
     * 设置阻尼效果：显示下拉高度/手指真实下拉高度
     * rate -> 阻尼率 ：越小越拖不动，默认：0.5f ,等于 1 时跟随手指移动
     */
    fun setDragRate(rate: Float) {
        this.mDragRate = rate
    }

    /**
     * 设置toolBar的拉伸状态
     */
    fun setToolBarState(state: AppBarStateChangeListener.State) {
        this.toolBarIsState = state
    }
}