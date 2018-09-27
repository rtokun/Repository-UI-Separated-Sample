package com.example.artyom.newarchitecturesample.data

import android.os.Handler
import io.reactivex.Observable
import io.reactivex.subjects.BehaviorSubject
import javax.inject.Inject

enum class PaymentStatus {
    INIT,
    STARTED,
    ERROR,
    SUCCESS
}

interface PaymentRepository {
    fun subscribeToPaymentStatus(): Observable<PaymentStatus>
    fun startPayment()
}

class PaymentRepositoryImpl @Inject constructor() : PaymentRepository {

    private val statusSubject: BehaviorSubject<PaymentStatus> = BehaviorSubject.createDefault(PaymentStatus.INIT)
    private var success = true

    override fun subscribeToPaymentStatus(): Observable<PaymentStatus> {
        return statusSubject
    }

    override fun startPayment() {
        statusSubject.onNext(PaymentStatus.STARTED)
        Handler().apply {
            postDelayed({
                if (success) {
                    this@PaymentRepositoryImpl.statusSubject.onNext(PaymentStatus.SUCCESS)
                } else {
                    this@PaymentRepositoryImpl.statusSubject.onNext(PaymentStatus.ERROR)
                }
                success = !success
            }, 5 * 1000)
        }
    }
}