package com.android.akshayfaye.urbandictionary.di

import android.app.Application
import dagger.Component
import retrofit2.Retrofit
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton
import dagger.BindsInstance

@Component(modules = [ApiModule::class,
                        FactoryModule::class,
                        AndroidSupportInjectionModule::class])
@Singleton
interface AppComponent{

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder

        fun build(): AppComponent
    }

    fun inject(dictionaryApplication : DictionaryApplication)
}