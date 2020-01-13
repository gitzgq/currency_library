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

}