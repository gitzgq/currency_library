import com.example.myapplication.app.App
import com.example.myapplication.bean.Template101Bean
import com.example.myapplication.net.BaseObserver
import com.zgq.common.base.retrofit.result.ZBaseObserver
import com.zgq.common.base.mvp.ZBaseModel
import com.zgq.common.base.mvp.ZBasePresenter
import com.zgq.common.base.other.ZLog
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class MainModel(basePresenter: ZBasePresenter<*>) : ZBaseModel(basePresenter) {

    private val presenter: MainPresenter = basePresenter as MainPresenter

    /**
     * 获取Banner数据
     */
    fun loadBanner(mListData : MutableList<Any>?, pageIndex : Int) {
        App.service?.loadBanner()?.subscribeOn(Schedulers.io())
                ?.observeOn(AndroidSchedulers.mainThread())
                ?.subscribe(object : BaseObserver<MutableList<Template101Bean>>() {
                    override fun onSuccess(data: MutableList<Template101Bean>?) {
                        ZLog.e("数据个数1 = ${data?.size}")
                        var oldSize = mListData?.size?: 0
                        var currSize = data?.size?: 0
                        if(pageIndex == 1){
                            mListData?.clear()
                        }
                        if(null != data){
                            mListData?.addAll(data)
                        }
                        presenter?.onSuccess(oldSize, currSize)
                    }

                    override fun disposable(d: Disposable) {
                        disposable?.add(d)
                    }

                    override fun onError(code: Int, errorMsg: String?) {
                        ZLog.e("异常 = $errorMsg")
                    }
                })
    }
}