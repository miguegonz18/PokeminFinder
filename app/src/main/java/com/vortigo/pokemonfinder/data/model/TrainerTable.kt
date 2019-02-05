package com.vortigo.pokemonfinder.data.model

import io.realm.RealmList
import io.realm.RealmObject

/**
 * @author rorogarcete
 * @version 0.0.1
 * Representation table Trainer
 * Copyright 2019 Vortigo Inc. All rights reserved
 */
open class TrainerTable(
    var name: String = "",
    var typePokemonFavorite: RealmList<TypeTable> = RealmList()
): RealmObject()