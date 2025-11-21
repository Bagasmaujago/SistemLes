import java.util.ArrayList;

public class Kelas {
    private String kodeKelas, namaKelas, pengajar;
    private Integer harga;
    private ArrayList<Siswa> daftarSiswa = new ArrayList<>();
    
    public Kelas(String kodeKelas, String namaKelas, String pengajar, Integer harga) {
        this.kodeKelas = kodeKelas;
        this.namaKelas = namaKelas;
        this.pengajar = pengajar;
        this.harga = harga;
    
    }
    public String getkodeKelas() {return kodeKelas;}
    public String getnamaKelas() {return namaKelas;}
    public String getpengajar() {return pengajar;}
    public Integer getharga() {return harga;}
    public ArrayList<Siswa> getDaftarSiswa() {return daftarSiswa;}
    

    public void tambahSiswa(Siswa siswa) {
        if (!daftarSiswa.contains(siswa)) {
            daftarSiswa.add(siswa);
        }
    }
}

