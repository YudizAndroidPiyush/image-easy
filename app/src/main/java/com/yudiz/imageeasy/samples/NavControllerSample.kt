package com.yudiz.imageeasy.samples

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.get
import com.yudiz.easy.helpers.ImageEasyBus
import com.yudiz.easy.helpers.ImageEasyEventCallback.Status.BACK_PRESSED
import com.yudiz.easy.helpers.ImageEasyEventCallback.Status.SUCCESS
import com.yudiz.easy.helpers.setupScreen
import com.yudiz.easy.helpers.showStatusBar
import com.yudiz.easy.utility.ARG_PARAM
import com.yudiz.easesample.R
import com.yudiz.imageeasy.commons.Adapter
import com.yudiz.imageeasy.custom.fragmentBody
import com.yudiz.easesample.databinding.ActivityNavControllerSampleBinding
import com.yudiz.imageeasy.options
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers

/**
 * Created By  Piyush Sonara on 20,June,2021
 *  
 */

class NavControllerSample : AppCompatActivity() {
    private lateinit var navController: NavController
    private lateinit var binding: ActivityNavControllerSampleBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNavControllerSampleBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()
        setupScreen()
        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController
        ImageEasyBus.results {
            if (it.status == SUCCESS) {
                showStatusBar()
                navController.navigateUp()
            }
        }
    }

    override fun onBackPressed() {
        if (navController.currentDestination == navController.graph[R.id.CameraFragment]) {
            ImageEasyBus.onBackPressedEvent()
        } else {
            super.onBackPressed()
        }
    }
}

class NavResultsFragment : Fragment() {
    private val recyclerViewAdapter = Adapter()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ImageEasyBus.results(coroutineScope = CoroutineScope(Dispatchers.Main)) {
            when (it.status) {
                SUCCESS -> {
                    recyclerViewAdapter.apply {
                        this.list.clear()
                        this.list.addAll(it.data)
                        notifyDataSetChanged()
                    }
                }
                BACK_PRESSED -> {
                    requireActivity().onBackPressed()
                }
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = fragmentBody(requireActivity(), recyclerViewAdapter) {
        var bundle = bundleOf(ARG_PARAM to options)
        findNavController().navigate(R.id.action_ResultsFragment_to_CameraFragment, bundle)
    }
}