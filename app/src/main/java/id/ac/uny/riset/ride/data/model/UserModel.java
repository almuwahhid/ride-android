package id.ac.uny.riset.ride.data.model;

public class UserModel {
    public String id_user, email, password, nama, jenis_kelamin, tgl_lahir, fakultas, alamat_asal, alamat_tinggal, agama, no_wa, confirmation_code, aktif;

    public UserModel() {
    }

    public UserModel(String id_user, String email, String password, String nama, String jenis_kelamin, String tgl_lahir, String fakultas, String alamat_asal, String alamat_tinggal, String agama, String no_wa, String confirmation_code, String aktif) {
        this.id_user = id_user;
        this.email = email;
        this.password = password;
        this.nama = nama;
        this.jenis_kelamin = jenis_kelamin;
        this.tgl_lahir = tgl_lahir;
        this.fakultas = fakultas;
        this.alamat_asal = alamat_asal;
        this.alamat_tinggal = alamat_tinggal;
        this.agama = agama;
        this.no_wa = no_wa;
        this.confirmation_code = confirmation_code;
        this.aktif = aktif;
    }

    public UserModel(String email, String password, String nama, String jenis_kelamin, String tgl_lahir, String fakultas, String alamat_asal, String alamat_tinggal, String agama, String no_wa) {
        this.email = email;
        this.password = password;
        this.nama = nama;
        this.jenis_kelamin = jenis_kelamin;
        this.tgl_lahir = tgl_lahir;
        this.fakultas = fakultas;
        this.alamat_asal = alamat_asal;
        this.alamat_tinggal = alamat_tinggal;
        this.agama = agama;
        this.no_wa = no_wa;
    }

    public void setUserModel(String email, String password, String nama, String jenis_kelamin, String fakultas, String alamat_asal, String alamat_tinggal, String agama, String no_wa) {
        this.email = email;
        this.password = password;
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

    public String getPassword() {
        return password;
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

    public String getConfirmation_code() {
        return confirmation_code;
    }

    public String getAktif() {
        return aktif;
    }
}
