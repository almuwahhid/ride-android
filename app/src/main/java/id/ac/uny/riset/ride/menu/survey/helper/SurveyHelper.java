package id.ac.uny.riset.ride.menu.survey.helper;

import java.util.ArrayList;
import java.util.List;

import id.ac.uny.riset.ride.data.model.AspekIndikatorModel;
import id.ac.uny.riset.ride.data.model.NilaiPernyataanModel;
import id.ac.uny.riset.ride.data.model.PernyataanModel;
import id.ac.uny.riset.ride.data.model.PickerModel;

public class SurveyHelper {
    public static List<PickerModel> convertFromPernyataan(List<NilaiPernyataanModel> nilaiPernyataanModels){
        List<PickerModel> convertFromPernyataan = new ArrayList<>();

        for (NilaiPernyataanModel nilaiPernyataanModel: nilaiPernyataanModels){
            convertFromPernyataan.add(new PickerModel(nilaiPernyataanModel.getId_nilai_jenis_pernyataan(),
                                                    nilaiPernyataanModel.getNama_nilai(),
                                                    String.valueOf(nilaiPernyataanModel.getNilai_pernyataan())));
        }
        return convertFromPernyataan;
    }
    
    public static List<PernyataanModel> convertFromIndikatorToPernyataan(List<AspekIndikatorModel> list){
        List<PernyataanModel> convertFromIndikatorToPernyataan = new ArrayList<>();

        for (AspekIndikatorModel indikatorModel: list){
            for (PernyataanModel pernyataanModel: indikatorModel.getPernyataan()){
                convertFromIndikatorToPernyataan.add( pernyataanModel);
            }
        }

        return convertFromIndikatorToPernyataan;
    }
}
