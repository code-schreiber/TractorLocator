package com.toolslab.tractorlocator.maps

import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.Bitmap.Config.ARGB_8888
import android.graphics.Bitmap.createBitmap
import android.graphics.Canvas
import android.graphics.drawable.VectorDrawable
import android.support.annotation.ColorRes
import android.support.annotation.DrawableRes
import javax.inject.Inject

internal class ResourceOperations @Inject constructor() {

    @Inject
    internal lateinit var resources: Resources

    internal fun color(@ColorRes id: Int) = resources.getColor(id)

    internal fun resourceAsBitmap(@DrawableRes id: Int): Bitmap? =
            getBitmap(resources.getDrawable(id, null) as VectorDrawable)

    private fun getBitmap(vector: VectorDrawable): Bitmap {
        val bitmap = createBitmap(vector.intrinsicWidth, vector.intrinsicHeight, ARGB_8888)
        val canvas = Canvas(bitmap)
        vector.setBounds(0, 0, canvas.width, canvas.height)
        vector.draw(canvas)
        return bitmap
    }

}
