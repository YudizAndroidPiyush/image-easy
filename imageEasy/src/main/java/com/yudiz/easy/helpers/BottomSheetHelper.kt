package com.yudiz.easy.helpers

import android.view.View
import androidx.core.view.isGone
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import androidx.fragment.app.FragmentActivity
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.yudiz.easy.utility.ImageEasyBindings

/**
 * Created By  Piyush Sonara on 17,June,2021
 *  
 */

internal fun FragmentActivity.setup(
    binding: ImageEasyBindings,
    bottomSheetBehavior: BottomSheetBehavior<View>?,
    callback: (Boolean) -> Unit
) {

    var localState = BottomSheetBehavior.STATE_COLLAPSED
    bottomSheetBehavior?.apply {
        peekHeight = this@setup.toPx(194f).toInt()
        addBottomSheetCallback(object :
            BottomSheetBehavior.BottomSheetCallback() {
            override fun onStateChanged(bottomSheet: View, newState: Int) {

                if (localState == BottomSheetBehavior.STATE_COLLAPSED && newState == BottomSheetBehavior.STATE_DRAGGING) {
                    binding.gridLayout.sendButtonStateAnimation(false)

                }
                binding.fragmentImageEasy.root.requestDisallowInterceptTouchEvent(newState == BottomSheetBehavior.STATE_DRAGGING)

                localState = newState
            }

            override fun onSlide(bottomSheet: View, slideOffset: Float) {
                manipulateVisibility(this@setup, binding, slideOffset)
                if (slideOffset == 1f) {
                    binding.gridLayout.sendButtonStateAnimation(show = false, withAnim = false)
                    callback(true)
                } else if (slideOffset == 0f) {
                    callback(false)
                }
            }
        })
    }

}

private fun manipulateVisibility(
    activity: FragmentActivity, binding: ImageEasyBindings,
    slideOffset: Float
) {
    binding.gridLayout.apply {
        instantRecyclerView.alpha = 1 - slideOffset
        arrowUp.alpha = 1 - slideOffset
        binding.controlsLayout.controlsLayout.alpha = 1 - slideOffset
        topbar.alpha = slideOffset
        recyclerView.alpha = slideOffset
        if (1 - slideOffset == 0f) {
            instantRecyclerView.hide()
            arrowUp.hide()
            binding.controlsLayout.primaryClickButton.hide()
        } else if (instantRecyclerView.isGone && 1 - slideOffset > 0) {
            instantRecyclerView.show()
            arrowUp.show()
            binding.controlsLayout.primaryClickButton.show()
        }
        if (slideOffset > 0 && recyclerView.isInvisible) {
            recyclerView.show()
            topbar.show()
            activity.showStatusBar()

        } else if (recyclerView.isVisible && slideOffset == 0f) {
            activity.hideStatusBar()
            recyclerView.invisible()
            topbar.hide()
        }
    }

}