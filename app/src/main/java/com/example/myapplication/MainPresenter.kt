
import com.example.myapplication.MainActivity
import com.zgq.common.base.mvp.ZBasePresenter
import com.zgq.common.base.mvp.ZBaseView

class MainPresenter(view : ZBaseView) : ZBasePresenter<MainActivity>(view){

    private val view : MainActivity? = view as? MainActivity
    private val model : MainModel = MainModel(this)

    /**
     * 获取banner数据
     */
    fun loadBanner(){
        view?.let {
            model?.loadBanner(it.mListData, it.pageIndex)
        }
    }


    /**
     * banner数据获取成功
     */
    fun onSuccess(oldSize : Int, currentSize : Int){
        view?.onSuccess(oldSize, currentSize)
    }

    override fun clear() {
        model?.clearNetWork()
        super.clear()
    }
}