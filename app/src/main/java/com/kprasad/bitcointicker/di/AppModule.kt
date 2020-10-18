package com.kprasad.bitcointicker.di

import com.kprasad.bitcointicker.network.BitcoinGateway
import com.kprasad.bitcointicker.viewmodel.MainViewModel
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun providesMainViewModel(bitcoinGateway: BitcoinGateway): MainViewModel {
        return MainViewModel(bitcoinGateway)
    }
}