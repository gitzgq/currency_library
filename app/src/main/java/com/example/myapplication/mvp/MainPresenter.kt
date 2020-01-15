
import com.example.myapplication.mvp.MainActivity
import com.zgq.common.base.base_mvp.ZBasePresenter
import com.zgq.common.base.base_mvp.ZBaseView

class MainPresenter(view : ZBaseView) : ZBasePresenter<MainActivity>(view){

    private val view : MainActivity? = view as? MainActivity
    private val model : MainModel = MainModel(this)


    override fun clear() {
        model?.clearNetWork()
        super.clear()
    }
}