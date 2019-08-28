package com.zgq.common.base.glide

import android.content.Context
import android.content.res.Resources
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.bumptech.glide.request.RequestOptions
import com.zgq.common.base.R

class ZGlide{

    companion object{
        val instence : ZGlide by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) {
            ZGlide()
        }
    }

    private val DEFAULT_IMG : Int = R.mipmap.ic_launcher // 默认图片
    private val ROUND_SIZE : Int = 5 // 圆角大小（dp）
    private val TYPE_DEFAULT : Int = 1 // 加载直角图片
    private val TYPE_ROUND : Int = 2 // 加载圆角图片
    private val TYPE_CIRCLE : Int = 3 // 加载圆形图片
    private val TYPE_ORIGINAL : Int = 4 // 加载原图（显示原图）

    /**
     * 加载直角图片
     */
    fun loadImg(context : Context, img : ImageView, obj : Any){
        loadImg(context, img, obj, DEFAULT_IMG, true)
    }

    fun loadImg(context : Context, img : ImageView, obj : Any, defaultImg : Int){
        loadImg(context, img, obj, defaultImg, true)
    }

    fun loadImg(context : Context, img : ImageView, obj : Any, cache : Boolean){
        loadImg(context, img, obj, DEFAULT_IMG, cache)
    }

    fun loadImg(context : Context, img : ImageView, obj : Any, defaultImg : Int, cache : Boolean){
        glide(context, img, obj, defaultImg, cache, TYPE_DEFAULT, 0, null)
    }

    /**
     * 加载圆角图片
     */
    fun loadRoundImg(context : Context, img : ImageView, obj : Any){
        loadRoundImg(context, img, obj, DEFAULT_IMG, true, ROUND_SIZE, ZRoundTransformation.CornerType.ALL)
    }

    fun loadRoundImg(context : Context, img : ImageView, obj : Any, roundSize: Int){
        loadRoundImg(context, img, obj, DEFAULT_IMG, true, roundSize, ZRoundTransformation.CornerType.ALL)
    }

    fun loadRoundImg(context : Context, img : ImageView, obj : Any, cache : Boolean){
        loadRoundImg(context, img, obj, DEFAULT_IMG, cache, ROUND_SIZE, ZRoundTransformation.CornerType.ALL)
    }

    fun loadRoundImg(context : Context, img : ImageView, obj : Any, defaultImg : Int, cache : Boolean, roundSize : Int, roundType: ZRoundTransformation.CornerType?){
        glide(context, img, obj, defaultImg, cache, TYPE_ROUND, roundSize, roundType)
    }

    /**
     * 加载圆形图片
     */
    fun loadCircleImg(context : Context, img : ImageView, obj : Any){
        loadCircleImg(context, img, obj, DEFAULT_IMG, true)
    }

    fun loadCircleImg(context : Context, img : ImageView, obj : Any, defaultImg : Int){
        loadCircleImg(context, img, obj, defaultImg, true)
    }

    fun loadCircleImg(context : Context, img : ImageView, obj : Any, cache : Boolean){
        loadCircleImg(context, img, obj, DEFAULT_IMG, cache)
    }

    fun loadCircleImg(context : Context, img : ImageView, obj : Any, defaultImg : Int, cache : Boolean){
        glide(context, img, obj, defaultImg, cache, TYPE_CIRCLE, 0, null)
    }

    /**
     * 加载原图
     */
    fun loadOriginal(context : Context, img : ImageView, obj : Any){
        loadOriginal(context, img, obj, DEFAULT_IMG, true)
    }

    fun loadOriginal(context : Context, img : ImageView, obj : Any, defaultImg : Int){
        loadOriginal(context, img, obj, defaultImg, true)
    }

    fun loadOriginal(context : Context, img : ImageView, obj : Any, cache : Boolean){
        loadOriginal(context, img, obj, DEFAULT_IMG, cache)
    }

    fun loadOriginal(context : Context, img : ImageView, obj : Any, defaultImg : Int, cache : Boolean){
        glide(context, img, obj, defaultImg, cache, TYPE_ORIGINAL, 0, null)
    }


    /**
     * 执行加载图片的方法
     */
    fun glide(context: Context?, img: ImageView?, obj: Any, defaultImg: Int, cache: Boolean, type: Int, roundSize: Int, roundType: ZRoundTransformation.CornerType?){
        if(null == context || null == img || null == obj){
            return
        }
        val options = RequestOptions()
        options.error(defaultImg)
        options.placeholder(defaultImg)
        // 是否要缓存
        if(cache){
            options.diskCacheStrategy(DiskCacheStrategy.RESOURCE) // 只缓存加载的图片
        }else{
            options.skipMemoryCache(true) // 不使用内存缓存
            options.diskCacheStrategy(DiskCacheStrategy.NONE) // 不使用磁盘缓存
        }
        // 设置图片样式
        if(type == TYPE_ROUND && null != roundType && roundSize > 0){// 圆角
            options.transform(CenterCrop(), ZRoundTransformation((Resources.getSystem().displayMetrics.density * roundSize).toInt(), 0, roundType))
        }else if(type == TYPE_CIRCLE){ // 圆形
            options.transform(CenterCrop(), CircleCrop())
        }else if(type == TYPE_DEFAULT){ // 直角
            options.centerCrop()
        }else if(type == TYPE_ORIGINAL){ // 原图
            img.adjustViewBounds = true // 图片宽度填满，高度自适配
        }
        Glide.with(context)
             .load(obj)
             .apply(options)
             .thumbnail(0.5f)
             .into(img)



    }

}