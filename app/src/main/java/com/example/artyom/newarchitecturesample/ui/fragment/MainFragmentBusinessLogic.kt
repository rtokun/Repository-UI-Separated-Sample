package com.example.artyom.newarchitecturesample.ui.fragment

import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.LifecycleObserver
import android.arch.lifecycle.OnLifecycleEvent
import com.example.artyom.newarchitecturesample.data.PaymentRepository
import com.example.artyom.newarchitecturesample.data.PaymentStatus
import io.reactivex.Observable
import io.reactivex.disposables.Disposable
import io.reactivex.rxkotlin.subscribeBy
import javax.inject.Inject

sealed class MainFragmentState {
    class INIT : MainFragmentState()
    class PAY_IN_PROGRESS : MainFragmentState()
    class ERROR(val errorDescription: String) : MainFragmentState()
    class PAY_SUCCESS : MainFragmentState()
}

interface MainFragmentView {
    fun setState(state: MainFragmentState)
}

class MainFragmentInteractor @Inject constructor(private val paymentRepository: PaymentRepository) {

    fun getPaymentStatus(): Observable<PaymentStatus> = paymentRepository.subscribeToPaymentStatus()

    fun pay() {
        paymentRepository.startPayment()
    }

}

class MainFragmentPresenter @Inject constructor(private val view: MainFragmentView,
                                                private val interactor: MainFragmentInteractor)
    : LifecycleObserver {


    private var disposable: Disposable? = null

    fun onPayClicked() {
        view.setState(MainFragmentState.PAY_IN_PROGRESS())
        interactor.pay()
    }

    fun onRetryClicked() {
        view.setState(MainFragmentState.PAY_IN_PROGRESS())
        interactor.pay()
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    fun onViewAttached() {
        disposable = interactor.getPaymentStatus()
            .subscribeBy(
                onNext = { status ->
                    when (status) {
                        PaymentStatus.INIT -> view.setState(MainFragmentState.INIT())
                        PaymentStatus.STARTED -> view.setState(MainFragmentState.PAY_IN_PROGRESS())
                        PaymentStatus.ERROR -> view.setState(MainFragmentState.ERROR("Some error occured"))
                        PaymentStatus.SUCCESS -> view.setState(MainFragmentState.PAY_SUCCESS())
                    }
                },
                onError = {
                    view.setState(MainFragmentState.ERROR(it.localizedMessage))
                },
                onComplete = { println("Done!") }
            )
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    fun onViewDetached() {
        disposable?.let {
            if (!it.isDisposed) {
                it.dispose()
            }
        }
    }

}