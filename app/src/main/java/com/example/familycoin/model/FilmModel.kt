package com.example.familycoin.model;

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity
data class FilmModel(
    @PrimaryKey val filmTitle: String,
    var filmYear: String?,
    var filmRated: String?,
    var filmReleased: String?,
    var filmRuntime: String?,
    var filmGenre: String?,
    var filmDirector: String?,
    var filmWriter: String?,
    var filmActors: String?,
    var filmPlot: String?,
    var filmLanguage: String?,
    var filmCountry: String?,
    var filmAwards: String?,
    var filmPoster: String?,
    var filmMetascore: String?,
    var filmImdbRating: String?,
    var filmImdbVotes: String?,
    var filmImdbId: String?,
    var filmType: String?,
    var filmResponse: String?

) : Serializable
