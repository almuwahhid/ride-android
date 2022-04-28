package id.ac.uny.riset.ride.menu.biodata;

public class BiodataModel {
    public String id_user, email, nama, jenis_kelamin, tgl_lahir, fakultas, alamat_asal, alamat_tinggal, agama, no_wa;

    public BiodataModel() {
    }

    public void setUserModel(String email, String password, String nama, String jenis_kelamin, String fakultas, String alamat_asal, String alamat_tinggal, String agama, String no_wa) {
        this.email = email;
        this.nama = nama;
        this.jenis_kelamin = jenis_kelamin;
        this.fakultas = fakultas;
        this.alamat_asal = alamat_asal;
        this.alamat_tinggal = alamat_tinggal;
        this.agama = agama;
        this.no_wa = no_wa;
    }

    public String getJenis_kelamin() {
        return jenis_kelamin;
    }

    public void setTgl_lahir(String tgl_lahir) {
        this.tgl_lahir = tgl_lahir;
    }

    public String getId_user() {
        return id_user;
    }

    public String getEmail() {
        return email;
    }


    public String getNama() {
        return nama;
    }

    public String getTgl_lahir() {
        return tgl_lahir;
    }

    public String getFakultas() {
        return fakultas;
    }

    public String getAlamat_asal() {
        return alamat_asal;
    }

    public String getAlamat_tinggal() {
        return alamat_tinggal;
    }

    public String getAgama() {
        return agama;
    }

    public String getNo_wa() {
        return no_wa;
    }

}
