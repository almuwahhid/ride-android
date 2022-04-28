package id.ac.uny.riset.ride.menu.grafik.helper;

import com.github.mikephil.charting.data.BarEntry;

import java.util.ArrayList;
import java.util.List;

import id.ac.uny.riset.ride.menu.grafik.model.GrafikUiModel;
import id.ac.uny.riset.ride.utils.Function;

public class GrafikHelper {
    public static List<BarEntry> parseGrafikToBar(List<GrafikUiModel> grafikUiModelList){
        List<BarEntry> parseGrafikToBar = new ArrayList<>();
        int index = 1;
        for (GrafikUiModel grafikUiModel: grafikUiModelList){
//            parseGrafikToBar.add(new BarEntry(Float.valueOf(grafikUiModel.getTanggal_survey().split(" ")[0].replaceAll("-", "")), Float.valueOf(grafikUiModel.getNilai())));
            parseGrafikToBar.add(new BarEntry(index, Float.valueOf(grafikUiModel.getNilai())));
            index++;
        }
        return parseGrafikToBar;
    }

    public static List<String> ranges(int size){
        ArrayList<String> xLabels = new ArrayList<>();
        for (int i = 1; i <= size; i++) {
            xLabels.add("ke - "+i);
        }
        return xLabels;
    }

    public static float getMaxYAxis(int position){
        switch (position){
            case 0:
                return 100;

            case 1:
                return 60;

            case 2:
                return 20;

            default:
                return 100;


        }
    }

    public static String getRange(int size){
        return "ke - " + size;
    }

    public static String dateRange(int position, List<GrafikUiModel> list){
        try {
            return Function.parseTimestampToSimpleFullDate(list.get(position).getTanggal_survey().split(" ")[0]);
        } catch (IndexOutOfBoundsException e){
            return "";
        }
    }

    public static String parseFloatToStringDate(int x){
        String data = String.valueOf(x);
        String th = data.substring(0 ,4);
        String bl = data.substring(4 ,6);
        String tgl = data.substring(6 ,8);
        return th+"-"+bl+"-"+tgl;
    }
}
