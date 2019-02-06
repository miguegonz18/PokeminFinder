package com.vortigo.pokemonfinder.ui.trainer

import android.content.Intent
import android.os.Bundle
import com.vortigo.pokemonfinder.PokemonFinderApp
import com.vortigo.pokemonfinder.R
import com.vortigo.pokemonfinder.data.prefs.PokemonPreference
import com.vortigo.pokemonfinder.helper.SpinnerTypeAdapter
import com.vortigo.pokemonfinder.helper.Util
import com.vortigo.pokemonfinder.models.Trainer
import com.vortigo.pokemonfinder.models.Type
import com.vortigo.pokemonfinder.ui.base.BaseActivity
import com.vortigo.pokemonfinder.ui.pokemon.search.PokemonSearchActivity
import kotlinx.android.synthetic.main.activity_select_pokemon.*
import javax.inject.Inject

/**
 * @author rorogarcete
 * @version 0.0.1
 * Activity to select favorite pokemon
 * Copyright 2019 Vortigo Inc. All rights reserved
 */
class SelectPokemonActivity: BaseActivity(), TrainerContract.TrainerView {

    @Inject lateinit var presenter: TrainerContract.TrainerPresenter

    private var trainerName: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_select_pokemon)

        setInit()
        setInjection()
    }

    override fun onResume() {
        super.onResume()
        presenter.attachView(this)
        presenter.getTypes()
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.detachView()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish(ActivityAnimation.SLIDE_RIGHT)
    }

    override fun goToHome() {
        if (!PokemonPreference().getInitDate(this).isEmpty()) {
            PokemonPreference().setTypeFavorite(this, trainerName)
            startActivity(Intent(this, PokemonSearchActivity::class.java), ActivityAnimation.SLIDE_LEFT)
        }
    }

    // TODO Implement loading with ProgressBar
    override fun showProgress() { }

    override fun hideProgress() { }

    // TODO Implement show error con SnackBar
    override fun onEntityError(error: String) {}

    override fun loadTypes(types: List<Type>) {
        val typeAdapter = SpinnerTypeAdapter(this, R.layout.spinner_item, types)
        typeAdapter.setDropDownViewResource(R.layout.spinner_dropdown_item)
        spinner_types.adapter = typeAdapter
    }

    private fun save(name: String) {
        val typeFav = spinner_types.getSelectedItem() as Type
        val t = Trainer(name, typeFav.name, typeFav.thumbnailImage)
        presenter.saveTrainer(t)
    }

    private fun setInit() {
        trainerName = intent.getStringExtra(Util.TRAINER_NAME)

        img_register_trainer.setOnClickListener {
            save(trainerName)
        }
    }

    private fun setInjection() {
        PokemonFinderApp.instance.component.inject(this)
    }

}
