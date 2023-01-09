package com.example.expirationdateapp.basket

import android.annotation.SuppressLint
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.view.MotionEvent
import android.view.View
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import kotlin.math.max
import kotlin.math.min


internal enum class ButtonsState {
    GONE, LEFT_VISIBLE, RIGHT_VISIBLE
}

class ItemTouchHelperCallback(private var listener: BasketCallBack) : ItemTouchHelper.Callback() {
    private var swipeBack = false
    private var buttonsShowedState = ButtonsState.GONE
    private var buttonInstance: RectF? = null
    private var currentItemViewHolder: RecyclerView.ViewHolder? = null

    override fun getMovementFlags(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
    ): Int {
        val drag_flags = ItemTouchHelper.UP or ItemTouchHelper.DOWN
        val swipe_flags = ItemTouchHelper.START or ItemTouchHelper.END
        return makeMovementFlags(drag_flags, swipe_flags)
    }

    override fun isLongPressDragEnabled(): Boolean {
        return true
    }
    
    /**드래그시 호출*/
    override fun onMove(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        target: RecyclerView.ViewHolder,
    ): Boolean {
        return listener.onItemMove(viewHolder.adapterPosition, target.adapterPosition)
    }

    /**스와이프시 호출*/
    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
        listener.onItemSwipe(viewHolder.adapterPosition)
    }

    /**아이템을 터치하거나 스와이프하거나 뷰에 변화가 생길경우 불러오는 함수*/
    override fun onChildDraw(
        c: Canvas,
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        dX: Float,
        dY: Float,
        actionState: Int,
        isCurrentlyActive: Boolean,
    ) {
        //아이템이 스와이프 됐을경우 버튼을 그려주기 위해서 스와이프가 됐는지 확인
        var dX = dX
        if (actionState == ItemTouchHelper.ACTION_STATE_SWIPE) {
            if (buttonsShowedState != ButtonsState.GONE) {
//                if (buttonsShowedState == ButtonsState.LEFT_VISIBLE) dX = max(dX, buttonWidth)
                if (buttonsShowedState == ButtonsState.RIGHT_VISIBLE) dX =
                    min(dX, -buttonWidth)
                super.onChildDraw(c,
                    recyclerView,
                    viewHolder,
                    dX,
                    dY,
                    actionState,
                    isCurrentlyActive)
            } else {
                setTouchListener(c,
                    recyclerView,
                    viewHolder,
                    dX,
                    dY,
                    actionState,
                    isCurrentlyActive)
            }
            if (buttonsShowedState == ButtonsState.GONE) {
                super.onChildDraw(c,
                    recyclerView,
                    viewHolder,
                    dX,
                    dY,
                    actionState,
                    isCurrentlyActive)
            }
        }
        currentItemViewHolder = viewHolder

        drawButtons(c, currentItemViewHolder)
    }
    
    /**버튼을 그려주는 함수*/
    private fun drawButtons(c: Canvas, viewHolder: RecyclerView.ViewHolder?) {
        val buttonWidthWithOutPadding = buttonWidth - 10
        val corners = 5f
        val itemView: View = viewHolder!!.itemView
        val p = Paint()
        buttonInstance = null


        //오른쪽으로 스와이프 했을때 (왼쪽에 버튼이 보여지게 될 경우)
//        if (buttonsShowedState == ButtonsState.LEFT_VISIBLE) {
//            val leftButton = RectF((itemView.left + 10).toFloat(),
//                (itemView.top + 10).toFloat(),
//                itemView.left + buttonWidthWithOutPadding,
//                (itemView.bottom - 10).toFloat())
//            p.color = Color.BLUE
//            c.drawRoundRect(leftButton, corners, corners, p)
//            drawText("장바구니", c, leftButton, p)
//            buttonInstance = leftButton

        //왼쪽으로 스와이프 했을때 (오른쪽에 버튼이 보여지게 될 경우)
        if (buttonsShowedState == ButtonsState.RIGHT_VISIBLE) {
            val rightButton = RectF(itemView.right - buttonWidthWithOutPadding,
                (itemView.top + 10).toFloat(),
                (itemView.right - 10).toFloat(),
                (itemView.bottom - 10).toFloat())
            p.color = Color.RED
            c.drawRoundRect(rightButton, corners, corners, p)
            drawText("삭제", c, rightButton, p)
            buttonInstance = rightButton
        }
    }
    //homeFragment로 itemview의 itemname 넘겨주기
    //버튼의 텍스트 그려주기
    private fun drawText(text: String, c: Canvas, button: RectF, p: Paint) {
        val textSize = 25f
        p.color = Color.WHITE
        p.isAntiAlias = true
        p.textSize = textSize
        val textWidth: Float = p.measureText(text)
        c.drawText(text, button.centerX() - textWidth / 2, button.centerY() + textSize / 2, p)
    }

    override fun convertToAbsoluteDirection(flags: Int, layoutDirection: Int): Int {
        if (swipeBack) {
            swipeBack = false
            return 0
        }
        return super.convertToAbsoluteDirection(flags, layoutDirection)
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun setTouchListener(
        c: Canvas, recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        dX: Float, dY: Float, actionState: Int,
        isCurrentlyActive: Boolean,
    ) {
        recyclerView.setOnTouchListener { v, event ->
            swipeBack =
                event.action == MotionEvent.ACTION_CANCEL || event.action == MotionEvent.ACTION_UP
            if (swipeBack) {
                if (dX < -buttonWidth) buttonsShowedState =
                    ButtonsState.RIGHT_VISIBLE
//                else if (dX > buttonWidth) buttonsShowedState =
//                    ButtonsState.LEFT_VISIBLE
                if (buttonsShowedState != ButtonsState.GONE) {
                    setTouchDownListener(c,
                        recyclerView,
                        viewHolder,
                        dX,
                        dY,
                        actionState,
                        isCurrentlyActive)
                    setItemsClickable(recyclerView, false)
                }
            }
            false
        }
    }

    /**만들어진 버튼을 눌렀을 때*/
    @SuppressLint("ClickableViewAccessibility")
    private fun setTouchDownListener(
        c: Canvas, recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder, dX: Float, dY: Float,
        actionState: Int, isCurrentlyActive: Boolean,
    ) {
        recyclerView.setOnTouchListener { _, event ->
            if (event.action == MotionEvent.ACTION_DOWN) {
                setTouchUpListener(c,
                    recyclerView,
                    viewHolder,
                    dX,
                    dY,
                    actionState,
                    isCurrentlyActive)
            }
            false
        }
    }
    
    /**손을 뗐을 때*/
    @SuppressLint("ClickableViewAccessibility")
    private fun setTouchUpListener(
        c: Canvas, recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder, dX: Float, dY: Float,
        actionState: Int, isCurrentlyActive: Boolean,
    ) {
        recyclerView.setOnTouchListener { v, event ->
            super@ItemTouchHelperCallback.onChildDraw(c,
                recyclerView,
                viewHolder,
                0f,
                dY,
                actionState,
                isCurrentlyActive)

            recyclerView.setOnTouchListener { v, event -> false }

            setItemsClickable(recyclerView, true)
            swipeBack = false

            if (listener != null && buttonInstance != null && buttonInstance!!.contains(event.x,
                    event.y)
            ) {
//                if (buttonsShowedState == ButtonsState.LEFT_VISIBLE) {
//                    listener.onLeftClick(viewHolder.adapterPosition, viewHolder)
                if (buttonsShowedState == ButtonsState.RIGHT_VISIBLE) {
                    listener.onRightClick(viewHolder.adapterPosition, viewHolder)
                }
            }
            buttonsShowedState = ButtonsState.GONE
            currentItemViewHolder = null
            false
        }
    }

    private fun setItemsClickable(recyclerView: RecyclerView, isClickable: Boolean) {
        for (i in 0 until recyclerView.childCount) {
            recyclerView.getChildAt(i).isClickable = isClickable
        }
    }

    companion object {
        private const val buttonWidth = 115f
    }
}