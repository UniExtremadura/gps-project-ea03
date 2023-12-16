package com.example.familycoin.api
import com.example.familycoin.model.FilmModel
import com.google.gson.annotations.SerializedName
import kotlinx.coroutines.yield


data class Film (

    @SerializedName("Title"      ) var Title      : String?           = null,
    @SerializedName("Year"       ) var Year       : String?           = null,
    @SerializedName("Rated"      ) var Rated      : String?           = null,
    @SerializedName("Released"   ) var Released   : String?           = null,
    @SerializedName("Runtime"    ) var Runtime    : String?           = null,
    @SerializedName("Genre"      ) var Genre      : String?           = null,
    @SerializedName("Director"   ) var Director   : String?           = null,
    @SerializedName("Writer"     ) var Writer     : String?           = null,
    @SerializedName("Actors"     ) var Actors     : String?           = null,
    @SerializedName("Plot"       ) var Plot       : String?           = null,
    @SerializedName("Language"   ) var Language   : String?           = null,
    @SerializedName("Country"    ) var Country    : String?           = null,
    @SerializedName("Awards"     ) var Awards     : String?           = null,
    @SerializedName("Poster"     ) var Poster     : String?           = null,
    @SerializedName("Metascore"  ) var Metascore  : String?           = null,
    @SerializedName("imdbRating" ) var imdbRating : String?           = null,
    @SerializedName("imdbVotes"  ) var imdbVotes  : String?           = null,
    @SerializedName("imdbID"     ) var imdbID     : String?           = null,
    @SerializedName("Type"       ) var Type       : String?           = null,
    @SerializedName("Response"   ) var Response   : String?           = null

) {
    fun toFilmModel(): FilmModel {
        return FilmModel(
            filmTitle = this.Title!!,
            filmYear = this.Year!!,
            filmRated = this.Rated!!,
            filmReleased = this.Released!!,
            filmRuntime = this.Runtime!!,
            filmGenre = this.Genre!!,
            filmDirector = this.Director!!,
            filmWriter = this.Writer!!,
            filmActors = this.Actors!!,
            filmPlot = this.Plot!!,
            filmLanguage = this.Language!!,
            filmCountry = this.Country!!,
            filmAwards = this.Awards!!,
            filmPoster = this.Poster!!,
            filmMetascore = this.Metascore!!,
            filmImdbRating = this.imdbRating!!,
            filmImdbVotes = this.imdbVotes!!,
            filmImdbId = this.imdbID!!,
            filmType = this.Type!!,
            filmResponse = this.Response!!
        )
    }
}
