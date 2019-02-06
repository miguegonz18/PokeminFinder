package com.vortigo.pokemonfinder.ui.pokemon.search

import com.vortigo.pokemonfinder.data.DataSource
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import timber.log.Timber
import javax.inject.Inject

/**
 * @author rorogarcete
 * @version 0.0.1
 * Fragment representing list of the Pokemons
 * Copyright 2019 Vortigo Inc. All rights reserved
 */
class PokemonSearchPresenter @Inject constructor(
    var dataSource: DataSource,
    val subscriberScheduler : Scheduler = Schedulers.io(),
    val observerScheduler : Scheduler = AndroidSchedulers.mainThread()): PokemonSearchContract.PokemonSerchPresenter {

    private lateinit var view: PokemonSearchContract.PokemonSearchView

    private val subscriptions = CompositeDisposable()

    override fun attachView(t: PokemonSearchContract.PokemonSearchView) {
        view = t
    }

    override fun detachView() {
        subscriptions.clear()
    }

    override fun getPokemonsByName(name: String) {
        view.showProgress()
        val subscr = dataSource.getPokemonsByFilter(name)
            .subscribeOn(subscriberScheduler)
            .observeOn(observerScheduler)
            .subscribe(
                { pokemons ->
                    view.loadPokemons(pokemons)
                    view.hideProgress()
                },
                { throwable ->
                    Timber.e(throwable)
                    view.hideProgress()
                },
                {
                    view.hideProgress()
                }
            )

        subscriptions.add(subscr)
    }

    override fun getPokemonsByType(type: String) {
        view.showProgress()
        val subscr = dataSource.getPokemonsByType(type)
            .subscribeOn(subscriberScheduler)
            .observeOn(observerScheduler)
            .subscribe(
                { pokemons ->
                    view.loadPokemons(pokemons)
                    view.hideProgress()
                },
                { throwable ->
                    Timber.e(throwable)
                    view.hideProgress()
                },
                {
                    view.hideProgress()
                }
            )

        subscriptions.add(subscr)
    }

}