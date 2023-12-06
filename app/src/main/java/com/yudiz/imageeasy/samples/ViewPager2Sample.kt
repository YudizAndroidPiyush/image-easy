package com.yudiz.imageeasy.samples

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.yudiz.easy.helpers.*
import com.yudiz.easy.utility.WIDTH
import com.yudiz.easesample.R
import com.yudiz.imageeasy.commons.Adapter
import com.yudiz.imageeasy.custom.fragmentBody2
import com.yudiz.easesample.databinding.ActivityViewPager2SampleBinding
import com.yudiz.imageeasy.options

/**
 * Created By  Piyush Sonara on 20,June,2021
 *  
 */

class ViewPager2Sample : AppCompatActivity() {
    private val iconWidth = 150
    private lateinit var binding: ActivityViewPager2SampleBinding
    private val imageEaseFragment = imageEasyFragment(options)
    private val viewPagerResultsFragment = ViewPagerResultsFragment()
    var fragmentList = ArrayList<Fragment>().apply {
        add(imageEaseFragment)
        add(viewPagerResultsFragment)
        add(SampleFragment())
        add(SampleFragment())
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityViewPager2SampleBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupScreen()
        binding.tabLayout.apply {
            addTab(this.newTab().setIcon(R.drawable.ic_camera))
            val titles = arrayOf("Chat", "Status", "Call")
            titles.forEach { title ->
                addTab(this.newTab().setText(title))
            }
            addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
                override fun onTabSelected(tab: TabLayout.Tab?) {
                    binding.viewPager.currentItem = tab!!.position
                }

                override fun onTabUnselected(tab: TabLayout.Tab?) {}

                override fun onTabReselected(tab: TabLayout.Tab?) {
                    binding.viewPager.currentItem = tab!!.position
                }
            })
        }.also {
            it.getTabAt(0)?.view?.layoutParams?.width = iconWidth
            val remainingSize = (WIDTH - iconWidth) / 3
            (1..3).forEach { num ->
                it.getTabAt(num)?.view?.layoutParams?.width = remainingSize
            }
        }
        binding.extraSpacingTop.apply {
            layoutParams.height = statusBarHeight
            requestLayout()
        }

        val mAdapter = ScreenSlidePagerAdapter(this)
        binding.viewPager.apply {
            adapter = mAdapter
            offscreenPageLimit = mAdapter.itemCount
            registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
                var selectedPage = 0
                override fun onPageSelected(position: Int) {
                    super.onPageSelected(position)
                    selectedPage = position
                    binding.tabLayout.getTabAt(position)?.select()
                    when (position) {
                        1 -> {
                            showStatusBar()
                            supportFragmentManager.resetMedia()
                        }
                    }
                }

                override fun onPageScrolled(
                    position: Int,
                    positionOffset: Float,
                    positionOffsetPixels: Int
                ) {
                    super.onPageScrolled(position, positionOffset, positionOffsetPixels)
                    if (position >= 1 && positionOffset >= 0) return
                    val newPositionOffset =
                        1 - if (selectedPage == 1 && (positionOffset * 10).toInt() == 0) 1f else positionOffset
                    binding.topBarLayout.apply {
                        translationY = -height * newPositionOffset
                        requestLayout()
                    }
                }
            })
            setCurrentItem(1, false)
        }

        supportActionBar?.hide()
        ImageEasyBus.results {
            when (it.status) {
                ImageEasyEventCallback.Status.SUCCESS -> {
                    binding.viewPager.currentItem = 1
                    viewPagerResultsFragment.setList(it.data)
                }
                ImageEasyEventCallback.Status.BACK_PRESSED -> {
                    binding.viewPager.currentItem = 1
                }
            }
        }
    }

    override fun onBackPressed() {
        when (binding.viewPager.currentItem) {
            0 -> ImageEasyBus.onBackPressedEvent()
            1 -> super.onBackPressed()
            else -> binding.viewPager.currentItem = binding.viewPager.currentItem - 1
        }
    }

    private inner class ScreenSlidePagerAdapter(fa: FragmentActivity) : FragmentStateAdapter(fa) {
        override fun getItemCount(): Int = fragmentList.size
        override fun createFragment(position: Int): Fragment = fragmentList[position]
    }
}

class SampleFragment : Fragment()
class ViewPagerResultsFragment : Fragment() {
    private val customAdapter = Adapter()
    fun setList(list: List<Uri>) {
        customAdapter.apply {
            this.list.clear()
            this.list.addAll(list)
            notifyDataSetChanged()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = fragmentBody2(requireActivity(), customAdapter)
}