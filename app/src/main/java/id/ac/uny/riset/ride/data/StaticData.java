package id.ac.uny.riset.ride.data;

import java.util.ArrayList;
import java.util.List;

import id.ac.uny.riset.ride.data.model.PickerModel;

public class StaticData {
    public static List<PickerModel> dataAgama(){
        List<PickerModel> dataAgama = new ArrayList<>();

        PickerModel data = new PickerModel("1", "Islam");
        dataAgama.add(data);

        data = new PickerModel("2", "Kristen");
        dataAgama.add(data);

        data = new PickerModel("3", "Katolik");
        dataAgama.add(data);

        data = new PickerModel("4", "Hindu");
        dataAgama.add(data);

        data = new PickerModel("5", "Budha");
        dataAgama.add(data);

        data = new PickerModel("6", "Kong Hu Cu");
        dataAgama.add(data);

        return dataAgama;
    }

    public static List<PickerModel> dataJenisKelamin(){
        List<PickerModel> dataAgama = new ArrayList<>();

        PickerModel data = new PickerModel("1", "Laki - Laki");
        dataAgama.add(data);

        data = new PickerModel("2", "Perempuan");
        dataAgama.add(data);

        return dataAgama;
    }
}
