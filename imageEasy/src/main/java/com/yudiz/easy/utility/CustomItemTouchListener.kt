package com.yudiz.easy.utility

import android.view.MotionEvent
import androidx.recyclerview.widget.RecyclerView

/**
 * Created By  Piyush Sonara on 17,June,2021
 *  
 */

class CustomItemTouchListener(private val binding: ImageEasyBindings) :
    RecyclerView.OnItemTouchListener {
    override fun onInterceptTouchEvent(rv: RecyclerView, e: MotionEvent): Boolean {
        val action = e.action
        return if (binding.gridLayout.instantRecyclerView.canScrollHorizontally(RecyclerView.FOCUS_FORWARD)) {
            when (action) {
                MotionEvent.ACTION_MOVE -> binding.fragmentImageEasy.root.requestDisallowInterceptTouchEvent(true)
            }
            false
        } else {
            when (action) {
                MotionEvent.ACTION_MOVE -> binding.fragmentImageEasy.root.requestDisallowInterceptTouchEvent(false)
            }
            binding.gridLayout.instantRecyclerView.removeOnItemTouchListener(this)
            true
        }
    }

    override fun onTouchEvent(rv: RecyclerView, e: MotionEvent) {}
    override fun onRequestDisallowInterceptTouchEvent(disallowIntercept: Boolean) {}
}