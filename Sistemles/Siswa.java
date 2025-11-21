package Sistemles;
public class Siswa {
    private String idSiswa, namaSiswa, noTelpon;

    public Siswa(String idSiswa, String namaSiswa, String noTelpon) {
        this.idSiswa = idSiswa;
        this.namaSiswa = namaSiswa;
        this.noTelpon = noTelpon;
    }

    public String getidSiswa() {return idSiswa;}
    public String getnamaSiswa() {return namaSiswa;}
    public String getnoTelpon() {return noTelpon;}
}
