package com.example.yelpapp.ui.common

import android.content.Context
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatImageView
import com.example.yelpapp.R

class AspectRatioImageView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : AppCompatImageView(context, attrs) {

    private var ratio: Float = 1f

    init {
        val a = context.obtainStyledAttributes(attrs, R.styleable.AspectRatioImageView)
        ratio = a.getFloat(R.styleable.AspectRatioImageView_ratio, 1f)
        a.recycle()
    }


    //Se usa OnMesure para pintar una pantalla si se quiere saber el tamaño final de la vista
    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)

        var width = measuredWidth
        var height = measuredHeight

        if (width==0 && height ==0){
            return
        }

        if (width>0){
            height = (width*ratio).toInt()
        } else if (height>0){
            width=(height/ratio).toInt()

        }
        setMeasuredDimension(width, height)
    }
}
