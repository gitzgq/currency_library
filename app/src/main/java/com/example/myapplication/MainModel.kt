import com.example.myapplication.bean.Template101Bean
import com.example.myapplication.net.request.RequestUtil
import com.example.myapplication.net.response.BaseObserver
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
    fun loadBanner(mListData: ArrayList<Any>?, pageIndex: Int) {
        RequestUtil.loadHomeBanner(object : BaseObserver<ArrayList<Template101Bean>>() {

            override fun onDisposable(d: Disposable) {
                disposable.add(d)
            }

            override fun onError(code: Int, errorMsg: String?) {
                ZLog.e("异常 = $errorMsg")
            }

            override fun onSuccess(data: ArrayList<Template101Bean>, code: Int) {
                ZLog.e("数据个数1 = ${data.size}")
                var oldSize = mListData?.size ?: 0
                var currSize = data?.size
                if (pageIndex == 1) {
                    mListData?.clear()
                }
                if (null != data) {
                    mListData?.addAll(data)
                }
                presenter?.onSuccess(oldSize, currSize)
            }

            override fun onEmptyData(code: Int, msg: String?) {
            }

        })
    }
}