package id.ac.uny.riset.ride.menu.surveySaya.model

import java.io.Serializable

class SurveySayaModel (var id_survey: String,
                       var id_user: String,
                       var id_status_identitas_religius: String,
                       var tanggal_survey: String,
                       var nama_status: String,
                       var deskripsi_status: String
) : Serializable