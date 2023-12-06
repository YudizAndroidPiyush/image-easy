package com.yudiz.easy.helpers


import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.fragment.app.FragmentManager
import com.yudiz.easy.models.Options
import com.yudiz.easy.utility.ARG_PARAM
import com.yudiz.easy.utility.ARG_PARAM_KEY
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

/**
 * Created By  Piyush Sonara on 17,June,2021
 *  
 */

open class ImageEasyEventCallback {

    enum class Status {
        SUCCESS, BACK_PRESSED
    }

    data class Results(
        var data: List<Uri> = ArrayList(),
        var status: Status = Status.SUCCESS
    )

    private val backPressedEvents = MutableSharedFlow<Any>()
    private val outputEvents = MutableSharedFlow<Results>()

    fun onBackPressedEvent() {
        CoroutineScope(Dispatchers.IO).launch {
            backPressedEvents.emit(Any())
        }

    }


    suspend fun on(
        coroutineScope: CoroutineScope = CoroutineScope(Dispatchers.Main),
        handler: suspend (Any) -> Unit
    ) = coroutineScope.launch(start = CoroutineStart.UNDISPATCHED) {
        backPressedEvents.asSharedFlow().collect {
            handler(it)
        }
    }

    fun returnObjects(
        coroutineScope: CoroutineScope = CoroutineScope(Dispatchers.IO),
        event: Results
    ) = coroutineScope.launch {
        outputEvents.emit(event)
    }


    fun results(
        coroutineScope: CoroutineScope = CoroutineScope(Dispatchers.Main),
        handler: suspend (Results) -> Unit
    ) = coroutineScope.launch(start = CoroutineStart.UNDISPATCHED) {
        outputEvents.asSharedFlow().collect { handler(it) }
    }
}

object ImageEasyBus : ImageEasyEventCallback()


fun AppCompatActivity.addImageEasyToActivity(
    containerId: Int,
    options: Options?,
    resultCallback: ((ImageEasyEventCallback.Results) -> Unit)? = null
) {
    supportFragmentManager.beginTransaction()
        .replace(containerId, com.yudiz.easy.ImageEasyFragment(resultCallback).apply {
            arguments = Bundle().apply {
                putParcelable(ARG_PARAM, options)
            }
        }).commit()
}

fun imageEasyFragment(
    options: Options,
    resultCallback: ((ImageEasyEventCallback.Results) -> Unit)? = null
): com.yudiz.easy.ImageEasyFragment {

    return com.yudiz.easy.ImageEasyFragment(resultCallback).apply {
        arguments = Bundle().apply {
            putParcelable(ARG_PARAM, options)
        }
    }
}

fun FragmentManager.resetMedia(preSelectedUrls: ArrayList<Uri> = ArrayList()) {
    setFragmentResult(
        ARG_PARAM_KEY,
        bundleOf(ARG_PARAM to if (preSelectedUrls.isEmpty()) null else Options().apply {
            this.preSelectedUrls.apply {
                clear()
                addAll(preSelectedUrls)
            }
        })
    )

}
// TODO: 18/06/21 more usability methods to be added
// TODO: 18/06/21 add documentation for usability methods