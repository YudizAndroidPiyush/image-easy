package com.yudiz.easy.helpers

import android.content.Context
import com.bumptech.glide.Glide
import com.bumptech.glide.integration.recyclerview.RecyclerViewPreloader
import com.yudiz.easy.adapters.MainImageAdapter
import com.yudiz.easy.models.Img

/**
 * Created By  Piyush Sonara on 17,June,2021
 *  
 */

internal fun Context.preLoader(adapter: MainImageAdapter): RecyclerViewPreloader<Img> =
    RecyclerViewPreloader(
        Glide.with(this), adapter, adapter.sizeProvider, 30 /*maxPreload*/
    )