package id.ac.uny.riset.ride.data.model

data class PernyataanModel(var id_pernyataan: String,
                           var id_jenis_pernyataan: String,
                           var id_indikator: String,
                           var nama_pernyataan: String,
                           var intervensi_task: String,
                           var nama_jenis_pernyataan: String,
                           var nilai: List<NilaiPernyataanModel>
                           )