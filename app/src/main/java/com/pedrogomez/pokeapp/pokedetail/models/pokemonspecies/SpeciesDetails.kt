package com.pedrogomez.pokeapp.pokedetail.models.pokemonspecies

import kotlinx.serialization.Serializable

@Serializable
data class SpeciesDetails(
    val base_happiness: Int,
    val capture_rate: Int,
    val egg_groups: List<EggGroup>,
    val evolution_chain: EvolutionChain,
    val flavor_text_entries: List<FlavorTextEntry>,
    val gender_rate: Int,
    val growth_rate: GrowthRate,
    val habitat: Habitat,
    val has_gender_differences: Boolean,
    val hatch_counter: Int,
    val id: Int,
    val is_baby: Boolean,
    val is_legendary: Boolean,
    val is_mythical: Boolean
)