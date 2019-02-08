package com.vortigo.pokemonfinder

import android.app.Application
import android.support.annotation.VisibleForTesting
import com.vortigo.pokemonfinder.data.db.PokemonSeed
import com.vortigo.pokemonfinder.data.prefs.PokemonPreference
import com.vortigo.pokemonfinder.di.DaggerPokemonComponent
import com.vortigo.pokemonfinder.di.PokemonComponent
import com.vortigo.pokemonfinder.di.PokemonModule
import io.realm.Realm
import io.realm.RealmConfiguration
import timber.log.Timber

/**
 * @author rorogarcete
 * @version 0.0.1
 * Application Class
 * Copyright 2019 Vortigo Inc. All rights reserved
 */
class PokemonFinderApp: Application() {

    lateinit var component : PokemonComponent
        @VisibleForTesting set

    override fun onCreate() {
        super.onCreate()
        instance = this

        initGraph()

        initPref()

        initRealm()

        makeSeedData()

        initDebug()
    }

    private fun initGraph() {
        component = DaggerPokemonComponent.builder()
            .pokemonModule(PokemonModule(PokemonFinderApp.instance))
            .build()
    }

    private fun initRealm() {
        Realm.init(this)
        RealmConfiguration.Builder()
            .name(POKEMON_DB)
            .schemaVersion(1)
            .deleteRealmIfMigrationNeeded().build()
    }

    private fun initPref() {
        PokemonPreference().prefs(this)
    }

    private fun makeSeedData() {
        if (PokemonPreference().getInitDate(this).isEmpty()) {
            val realm = Realm.getDefaultInstance()
            val seed = PokemonSeed(this, realm)
            seed.populateData()
        }
    }

    private fun initDebug() {
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }

    companion object {
        lateinit var instance : PokemonFinderApp private set
        var sIsSessionActive: Boolean = false
        var POKEMON_DB = "pokemons-db"
    }

}