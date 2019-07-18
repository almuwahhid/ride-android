package id.ac.uny.riset.ride.menu.detailSurveySaya.model

import java.io.Serializable

data class DetailSurveySayaModel (var id_pertanyaan_survey: String,
                                  var id_pernyataan: String,
                                  var nilai_pertanyaan: String,
                                  var nama_pernyataan: String
): Serializable