import com.zgq.common.base.base_mvp.ZBaseModel
import com.zgq.common.base.base_mvp.ZBasePresenter

class MainModel(basePresenter: ZBasePresenter<*>) : ZBaseModel(basePresenter) {

    private val presenter: MainPresenter = basePresenter as MainPresenter

}