package id.ac.uny.riset.ride.menu.grafik.model

import java.io.Serializable

data class GrafikAspekUiModel (var id_aspek: String,
                               var nama_aspek: String,
                               var urutan: String,
                               var survey: List<GrafikUiModel>
): Serializable