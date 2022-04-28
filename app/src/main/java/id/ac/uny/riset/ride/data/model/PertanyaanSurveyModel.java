package id.ac.uny.riset.ride.data.model;

public class PertanyaanSurveyModel {
    String id_pertanyaan_survey, id_survey, id_pernyataan, nilai_pertanyaan, nama_nilai_pertanyaan;

    public PertanyaanSurveyModel(String id_survey, String id_pernyataan, String nilai_pertanyaan, String nama_nilai_pertanyaan) {
        this.id_survey = id_survey;
        this.id_pernyataan = id_pernyataan;
        this.nilai_pertanyaan = nilai_pertanyaan;
        this.nama_nilai_pertanyaan = nama_nilai_pertanyaan;
    }

    public String getId_pertanyaan_survey() {
        return id_pertanyaan_survey;
    }

    public void setId_pertanyaan_survey(String id_pertanyaan_survey) {
        this.id_pertanyaan_survey = id_pertanyaan_survey;
    }

    public String getId_survey() {
        return id_survey;
    }

    public void setId_survey(String id_survey) {
        this.id_survey = id_survey;
    }

    public String getId_pernyataan() {
        return id_pernyataan;
    }

    public void setId_pernyataan(String id_pernyataan) {
        this.id_pernyataan = id_pernyataan;
    }

    public String getNilai_pertanyaan() {
        return nilai_pertanyaan;
    }

    public void setNilai_pertanyaan(String nilai_pertanyaan) {
        this.nilai_pertanyaan = nilai_pertanyaan;
    }

    public String getNama_nilai_pertanyaan() {
        return nama_nilai_pertanyaan;
    }

    public void setNama_nilai_pertanyaan(String nama_nilai_pertanyaan) {
        this.nama_nilai_pertanyaan = nama_nilai_pertanyaan;
    }
}
