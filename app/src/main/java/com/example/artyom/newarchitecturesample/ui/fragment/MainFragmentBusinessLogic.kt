package com.example.artyom.newarchitecturesample.ui.fragment

import com.example.artyom.newarchitecturesample.data.PaymentRepository
import javax.inject.Inject

sealed class MainFragmentState {
    class INIT : MainFragmentState()
    class PAY_IN_PROGRESS : MainFragmentState()
    class ERROR(val errorDescription: String) : MainFragmentState()
}

interface MainFragmentView {
    fun setState(state: MainFragmentState)
}

class MainFragmentInteractor @Inject constructor(val paymentRepository: PaymentRepository) {

}

class MainFragmentPresenter @Inject constructor(val view: MainFragmentView,
                                                val interactor: MainFragmentInteractor) {

    fun onPayClicked(){
        view.setState(MainFragmentState.PAY_IN_PROGRESS())
    }

    fun onRetryClicked() {
        view.setState(MainFragmentState.PAY_IN_PROGRESS())
    }

}