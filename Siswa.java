public class Siswa {
    private String idSiswa, namaSiswa, noTelpon;
    private int tahun;  // tahun angkatan

    public Siswa(String idSiswa, String namaSiswa, String noTelpon, int tahun) {
        this.idSiswa = idSiswa;
        this.namaSiswa = namaSiswa;
        this.noTelpon = noTelpon;
        this.tahun = tahun;
    }

    public String getidSiswa() {return idSiswa;}
    public String getnamaSiswa() {return namaSiswa;}
    public String getnoTelpon() {return noTelpon;}
    public int getTahun() {return tahun;}
}
