package id.ac.uny.riset.ride.data.model

import java.io.Serializable

data class SurveyModel (var id_survey: String,
                        var id_user: String,
                        var id_status_identitas_religius: String,
                        var tanggal_survey: String
) : Serializable