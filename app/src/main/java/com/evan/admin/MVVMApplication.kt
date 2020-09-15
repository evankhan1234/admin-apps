package com.evan.admin

import android.app.Application
import com.evan.admin.data.db.AppDatabase
import com.evan.admin.data.network.MyApi
import com.evan.admin.data.network.NetworkConnectionInterceptor
import com.evan.admin.data.preferences.PreferenceProvider
import com.evan.admin.data.repositories.HomeRepository
import com.evan.admin.data.repositories.QuotesRepository
import com.evan.admin.data.repositories.UserRepository
import com.evan.admin.ui.auth.AuthViewModelFactory
import com.evan.admin.ui.home.HomeViewModel
import com.evan.admin.ui.home.HomeViewModelFactory
import com.evan.admin.ui.home.newsfeed.ownpost.OwnPostDataSource
import com.evan.admin.ui.home.newsfeed.ownpost.OwnPostModelFactory
import com.evan.admin.ui.home.newsfeed.ownpost.OwnPostSourceFactory
import com.evan.admin.ui.home.newsfeed.ownpost.OwnPostViewModel
import com.evan.admin.ui.home.newsfeed.publicpost.PublicPostDataSource
import com.evan.admin.ui.home.newsfeed.publicpost.PublicPostModelFactory
import com.evan.admin.ui.home.newsfeed.publicpost.PublicPostSourceFactory
import com.evan.admin.ui.home.newsfeed.publicpost.PublicPostViewModel
import com.evan.admin.ui.home.profile.ProfileViewModelFactory
import com.evan.admin.ui.home.quotes.QuotesViewModelFactory
import com.evan.admin.ui.home.store.customer.CustomerDataSource
import com.evan.admin.ui.home.store.customer.CustomerModelFactory
import com.evan.admin.ui.home.store.customer.CustomerSourceFactory
import com.evan.admin.ui.home.store.customer.CustomerViewModel
import com.evan.admin.ui.home.store.post.PostDataSource
import com.evan.admin.ui.home.store.post.PostModelFactory
import com.evan.admin.ui.home.store.post.PostSourceFactory
import com.evan.admin.ui.home.store.post.PostViewModel
import com.evan.admin.ui.home.store.products.ProductDataSource
import com.evan.admin.ui.home.store.products.ProductModelFactory
import com.evan.admin.ui.home.store.products.ProductSourceFactory
import com.evan.admin.ui.home.store.products.ProductViewModel

import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.androidXModule
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.provider
import org.kodein.di.generic.singleton

class MVVMApplication : Application(), KodeinAware {

    override val kodein = Kodein.lazy {
        import(androidXModule(this@MVVMApplication))

        bind() from singleton { NetworkConnectionInterceptor(instance()) }
        bind() from singleton { MyApi(instance()) }
        bind() from singleton { AppDatabase(instance()) }
        bind() from singleton { PreferenceProvider(instance()) }
        bind() from singleton { UserRepository(instance(), instance()) }
        bind() from singleton { HomeRepository(instance()) }
        bind() from singleton { QuotesRepository(instance(), instance(), instance()) }
        bind() from provider { AuthViewModelFactory(instance()) }
        bind() from provider { ProfileViewModelFactory(instance()) }
        bind() from provider{ QuotesViewModelFactory(instance()) }
        bind() from provider{ PostViewModel(instance(), instance()) }
        bind() from provider{ ProductViewModel(instance(), instance()) }
        bind() from provider{ CustomerViewModel(instance(), instance()) }
        bind() from provider{ PostModelFactory(instance(), instance()) }
        bind() from provider{ ProductModelFactory(instance(), instance()) }
        bind() from provider{ CustomerModelFactory(instance(), instance()) }
        bind() from provider{ PostDataSource(instance(), instance()) }
        bind() from provider{ ProductDataSource(instance(), instance()) }
        bind() from provider{ CustomerDataSource(instance(), instance()) }
        bind() from provider{ PublicPostDataSource(instance(), instance()) }
        bind() from provider{ OwnPostDataSource(instance(), instance()) }
        bind() from provider{ PostSourceFactory(instance()) }
        bind() from provider{ ProductSourceFactory(instance()) }
        bind() from provider{ CustomerSourceFactory(instance()) }
        bind() from provider{ PublicPostSourceFactory(instance()) }
        bind() from provider{ OwnPostSourceFactory(instance()) }
        bind() from provider { HomeViewModelFactory(instance()) }
        bind() from provider { OwnPostModelFactory(instance(),instance()) }
        bind() from provider { PublicPostModelFactory(instance(),instance()) }
        bind() from provider { PublicPostViewModel(instance(),instance()) }
        bind() from provider { OwnPostViewModel(instance(),instance()) }

    }

}