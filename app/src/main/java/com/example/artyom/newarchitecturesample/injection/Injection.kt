package com.example.artyom.newarchitecturesample.injection

import com.example.artyom.newarchitecturesample.App
import com.example.artyom.newarchitecturesample.data.PaymentRepository
import com.example.artyom.newarchitecturesample.data.PaymentRepositoryImpl
import com.example.artyom.newarchitecturesample.ui.MainActivity
import com.example.artyom.newarchitecturesample.ui.fragment.MainFragment
import com.example.artyom.newarchitecturesample.ui.fragment.MainFragmentModule
import dagger.Component
import dagger.Module
import dagger.Provides
import dagger.android.AndroidInjector
import dagger.android.ContributesAndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

/** Created by artyom on 19/03/2018.  */
@Module
class AppModule {

    @Provides
    @Singleton
    fun providesPaymentRepo(paymentRepository: PaymentRepositoryImpl): PaymentRepository = paymentRepository

}

@Module
abstract class FragmentsModule {

    @ContributesAndroidInjector(modules = [MainFragmentModule::class])
    internal abstract fun mainFragment(): MainFragment
}

@Module
abstract class ActivitiesModule {

    @ContributesAndroidInjector
    internal abstract fun mainActivity(): MainActivity
}

@Singleton
@Component(modules = [AppModule::class,
    FragmentsModule::class,
    ActivitiesModule::class,
    AndroidSupportInjectionModule::class])
interface AppComponent : AndroidInjector<App> {

    @Component.Builder
    abstract class Builder : AndroidInjector.Builder<App>()

}

