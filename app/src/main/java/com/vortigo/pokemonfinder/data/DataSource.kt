package com.vortigo.pokemonfinder.data

import com.vortigo.pokemonfinder.models.Pokemon
import com.vortigo.pokemonfinder.models.Trainer
import com.vortigo.pokemonfinder.models.Type
import io.reactivex.Observable

/**
 * @author rorogarcete
 * @version 0.0.1
 * DataSource interface for repository pattern
 * Copyright 2019 Vortigo Inc. All rights reserved
 */
interface DataSource {
    fun getTypes(): Observable<List<Type>>

    fun getPokemonsByType(type: String): Observable<List<Pokemon>>

    fun getPokemonsOrderedByType(type: String): Observable<List<Pokemon>>

    fun getPokemonsByFilter(query: String): Observable<List<Pokemon>>

    fun getPokemonDetailById(id: Int): Observable<Pokemon>

    fun saveTrainer(trainer: Trainer): Boolean

    fun getTypePokemonFavorite(): Observable<List<Trainer>>
}