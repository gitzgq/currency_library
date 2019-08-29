
import com.example.myapplication.HomepageBannerBean
import com.example.myapplication.app.App
import com.zgq.common.base.retrofit.result.BaseObserver
import com.zgq.common.base.mvp.ZBaseModel
import com.zgq.common.base.mvp.ZBasePresenter
import com.zgq.common.base.other.ZLog
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class MainModel(basePresenter: ZBasePresenter<*>) : ZBaseModel(basePresenter){

    private val presenter : MainPresenter = basePresenter as MainPresenter

    /**
     * 获取Banner数据
     */
    fun loadBanner(){
        ZLog.e("进来了")
        App.service?.load()?.subscribeOn(Schedulers.io())
                ?.observeOn(AndroidSchedulers.mainThread())
                ?.subscribe(object : BaseObserver<ArrayList<HomepageBannerBean>>() {

                    override fun disposable(d: Disposable) {
                        disposable?.add(d)
                    }

                    override fun onSuccess(data: ArrayList<HomepageBannerBean>?) {
                        presenter.onSuccess(data)
                    }

                    override fun onError(code: Int, errorMsg: String?) {
                        ZLog.e("异常 = $errorMsg")
                    }
                })    }
}