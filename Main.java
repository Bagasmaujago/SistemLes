import java.util.Scanner;

import Sistemles.Kelas;
import Sistemles.Siswa;
import Sistemles.Transaksi;

import java.util.InputMismatchException;
import java.util.ArrayList;

public class Main {
    static ArrayList<Kelas> daftarKelasTersedia = new ArrayList<>();
    static ArrayList<Siswa> daftarSiswa = new ArrayList<>();
    static ArrayList<Transaksi> riwayatTransaksi = new ArrayList<>();
    static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        dataDummy();
        dataSiswa();
        int pilihan = 0;

        System.out.println("==== Selamat Datang di kelas Les TI A ====");
        System.out.println("==== Selamat Datang di Sistem! ====");

        do {
            System.out.println("======== Menu UTAMA =======");
            System.out.println("1. Tampilkan Daftar Kelas yang Tersedia");
            System.out.println("2. Pendaftaran Siswa Baru");
            System.out.println("3. Pemilihan Kelas Siswa Baru");
            System.out.println("4. Proses Pembayaran");
            System.out.println("5. Cek Riwayat Transaksi");
            System.out.println("6. Lihat Daftar Semua Siswa");
            System.out.println("7. Lihat Daftar Siswa Perkelas");
            System.out.println("8. Keluar Progam");
            System.out.print("Pilih Menu 1-8!: ");

            // Mengatasi jika menginput bukan angka
            try {
                pilihan = scanner.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("Input Tidak Valid Masukkan Angka 1-8!");
                pilihan = 0;
            } finally {
                scanner.nextLine();
            }

            // Swicth pilihan input
            switch (pilihan) {
                case 1 -> tampilkanDaftarKelas();
                case 2 -> registrasiSiswaBaru();
                case 3 -> pendaftaranKelas();
                case 4 -> prosesPembayaran();
                case 5 -> lihatRiwayatTransaksi();
                case 6 -> lihatDaftarSemuaSiswa();
                case 7 -> lihatDaftarSiswaPerKelas();
                case 8 -> System.out.println("\nTerima kasih telah menggunakan sistem ini. Sampai jumpa!");
                default -> {
                    if (pilihan != 0)
                        System.out.println("Pilihan tidak valid. Silakan pilih 1-8.");
                }

            }
            System.out.println();
        } while (pilihan != 8);
        {
            scanner.close();
        }

    }

    // Menampilkan daftar Kelas yang Tersedia
    public static void tampilkanDaftarKelas() {
        System.out.println("===== DAFTAR KELAS YANG TERSEDIA =====");
        if (daftarKelasTersedia.isEmpty()) {
            System.out.println("Belum ada kelas tersedia");
            return;
        }
        System.out.println("_____________________________________________________________");
        System.out.printf("| %-8s | %-25s | %-15s | %-12s |\n", "Kode", "Nama Kelas", "Pengajar", "Harga");
        System.out.println("_____________________________________________________________");

        for (Kelas k : daftarKelasTersedia) {
            System.out.printf("| %-8s | %-25s | %-15s | Rp%,-10d |\n",
                    k.getkodeKelas(), k.getnamaKelas(), k.getpengajar(), k.getharga());

        }
        System.out.println("_____________________________________________________________");
    }

    // Membuat Form Registrasi Siswa Baru
    public static Siswa registrasiSiswaBaru() {
        System.out.println("====== REGISTRASI SISWA BARU =========");
        System.out.print("Masukkan Nama Siswa Baru: ");
        String nama = scanner.nextLine();
        System.out.print("Masukkan Nomor Telpon: ");
        String noTelp = scanner.nextLine();

        // Membuat Id Siswa baru secara otomatis
        String idSiswa = "S-" + String.format("%03d", daftarSiswa.size() + 1);
        Siswa siswaBaru = new Siswa(idSiswa, nama, noTelp);
        daftarSiswa.add(siswaBaru);

        System.out.println("Siswa Baru Berhasil Ditambahkan!");
        System.out.println("ID Siswa: " + idSiswa + "| Nama: " + nama);
        pendaftaranKelas(); 
        return siswaBaru;
    }     

    

    // Menambahkan Siswa Kekelas
    public static void pendaftaranKelas() {
        System.out.println("\n=== PENDAFTARAN KELAS ===");
        System.out.print("Masukkan ID Siswa (atau 'baru'): ");
        String input = scanner.nextLine();

        Siswa siswa = input.equalsIgnoreCase("baru") ? registrasiSiswaBaru() : findSiswaById(input);
        if (siswa == null) {
            System.out.println("Siswa tidak ditemukan. Registrasi baru...");
            siswa = registrasiSiswaBaru();
        }

        System.out.println("Siswa: " + siswa.getnamaSiswa() + " (ID: " + siswa.getidSiswa() + ")");
        tampilkanDaftarKelas();

        System.out.print("Pilih Kode Kelas: ");
        String kodeKelas = scanner.nextLine();
        Kelas kelas = findKelasByKode(kodeKelas);

        if (kelas == null) {
            System.out.println("Kode kelas tidak valid.");
            return;
        }

        kelas.tambahSiswa(siswa);

        String idTrx = "T-" + String.format("%03d", riwayatTransaksi.size() + 1);
        Transaksi trx = new Transaksi(idTrx, siswa.getidSiswa(), kelas.getkodeKelas(), kelas.getharga(), "BELUM LUNAS");
        riwayatTransaksi.add(trx);

        System.out.println("Pendaftaran berhasil!");
        System.out.println("Kelas: " + kelas.getnamaKelas());
        System.out.println("Total Tagihan: Rp" + String.format("%,-10d", kelas.getharga()));
        System.out.println("ID Transaksi: " + idTrx);

        System.out.print("apakah ingin menambahkan kelas lagi? y/n: ");
        String menambahkelas = scanner.nextLine();
        if (menambahkelas.equalsIgnoreCase("y")) {
            pendaftaranKelas();
        } else {
            System.out.println("Pendaftaran kelas selesai.");
        }
    }

    // Memproses Pembayaran yang belum Lunas
    public static void prosesPembayaran() {
        System.out.println("\n=== PROSES PEMBAYARAN ===");
        if (riwayatTransaksi.isEmpty()) {
            System.out.println("Belum ada transaksi yang perlu dibayar.");
            return;
        }

        System.out.print("Masukkan ID Transaksi: ");
        String idTransaksi = scanner.nextLine();
        Transaksi trx = findTransaksiById(idTransaksi);

        if (trx == null) {
            System.out.println("Transaksi tidak ditemukan.");
            return;
        }
        if (trx.getStatusBayar().equals("LUNAS")) {
            System.out.println("Transaksi ini sudah lunas.");
            return;
        }

        Siswa siswa = findSiswaById(trx.getIdSiswa());
        Kelas kelas = findKelasByKode(trx.getKodeKelas());

        System.out.println("─── DETAIL TAGIHAN ───");
        System.out.println("ID Transaksi: " + trx.getIdTransaksi());
        System.out.println("Siswa       : " + (siswa != null ? siswa.getnamaSiswa() : "N/A"));
        System.out.println("Kelas       : " + (kelas != null ? kelas.getnamaKelas() : "N/A"));
        System.out.printf("Total Bayar : Rp%,.0f\n", trx.getTotalBayar());

        double jumlahBayar = 0;
        boolean valid = false;
        while (!valid) {
            try {
                System.out.print("Masukkan Jumlah Uang (Rp): ");
                jumlahBayar = scanner.nextDouble();
                scanner.nextLine();
                valid = true;
            } catch (InputMismatchException e) {
                System.out.println("Input harus angka!");
                scanner.nextLine();
            }
        }

        if (jumlahBayar < trx.getTotalBayar()) {
            System.out.println("Uang tidak cukup!");
        } else {
            double kembalian = jumlahBayar - trx.getTotalBayar();
            trx.setStatusBayar("LUNAS");
            System.out.println("\n=== STRUK PEMBAYARAN ===");
            System.out.println("PEMBAYARAN BERHASIL!");
            System.out.printf("Total Tagihan: Rp%,.0f\n", trx.getTotalBayar());
            System.out.printf("Uang Dibayar : Rp%,.0f\n", jumlahBayar);
            System.out.printf("Kembalian    : Rp%,.0f\n", kembalian);
        }
    }

    // Melihat Riwayat Transaksi
    public static void lihatRiwayatTransaksi() {
        System.out.println("\n=== RIWAYAT TRANSAKSI ===");
        if (riwayatTransaksi.isEmpty()) {
            System.out.println("Belum ada transaksi.");
            return;
        }
        System.out.println(
                "─────────────────────────────────────────────────────────────────────────────────────────────");
        System.out.printf("| %-10s | %-15s | %-20s | %-13s | %-12s |\n", "ID Trx", "Nama Siswa", "Kelas", "Total Bayar",
                "Status");
        System.out.println(
                "─────────────────────────────────────────────────────────────────────────────────────────────");
        for (Transaksi t : riwayatTransaksi) {
            Siswa s = findSiswaById(t.getIdSiswa());
            Kelas k = findKelasByKode(t.getKodeKelas());
            System.out.printf("| %-10s | %-15s | %-20s | Rp%,-10.0f | %-12s |\n",
                    t.getIdTransaksi(),
                    s != null ? s.getnamaSiswa() : "N/A",
                    k != null ? k.getnamaKelas() : "N/A",
                    t.getTotalBayar(),
                    t.getStatusBayar());
        }
        System.out.println(
                "─────────────────────────────────────────────────────────────────────────────────────────────");
    }

    // Menampilkan daftar semua siswa
    public static void lihatDaftarSemuaSiswa() {
        System.out.println("\n=== DAFTAR SEMUA SISWA ===");
        if (daftarSiswa.isEmpty()) {
            System.out.println("Belum ada siswa terdaftar.");
            return;
        }
        System.out.println("─────────────────────────────────────────────────");
        System.out.printf("| %-10s | %-20s | %-15s |\n", "ID Siswa", "Nama", "No. Telepon");
        System.out.println("─────────────────────────────────────────────────");
        for (Siswa s : daftarSiswa) {
            System.out.printf("| %-10s | %-20s | %-15s |\n", s.getidSiswa(), s.getnamaSiswa(), s.getnoTelpon());
        }
        System.out.println("─────────────────────────────────────────────────");
        lihatDaftarSiswaPerKelas();
    }

    // Menampilkan daftar siswa per kelas
    public static void lihatDaftarSiswaPerKelas() {
        System.out.println("\n=== DAFTAR SISWA PER KELAS ===");
        if (daftarKelasTersedia.isEmpty()) {
            System.out.println("Belum ada kelas tersedia.");
            return;
        }
        tampilkanDaftarKelas();
        System.out.print("Masukkan Kode Kelas: ");
        String kode = scanner.nextLine().trim();
        Kelas kelas = findKelasByKode(kode);

        if (kelas == null) {
            System.out.println("Kelas tidak ditemukan.");
            return;
        }

        System.out.println("\nSiswa di kelas: " + kelas.getnamaKelas());
        if (kelas.getDaftarSiswa().isEmpty()) {
            System.out.println("Belum ada siswa yang terdaftar.");
        } else {
            System.out.println("─────────────────────────────────────────────────");
            System.out.printf("| %-10s | %-20s | %-15s |\n", "ID", "Nama", "Telepon");
            System.out.println("─────────────────────────────────────────────────");
            for (Siswa s : kelas.getDaftarSiswa()) {
                System.out.printf("| %-10s | %-20s | %-15s |\n", s.getidSiswa(), s.getnamaSiswa(), s.getnoTelpon());
            }
            System.out.println("─────────────────────────────────────────────────");
        }
    }

    // Fungsi Helper untuk membantu proses pencarian
    private static Siswa findSiswaById(String id) {
        for (Siswa s : daftarSiswa) {
            if (s.getidSiswa().equalsIgnoreCase(id))
                return s;
        }
        return null;
    }

    private static Kelas findKelasByKode(String kode) {
        for (Kelas k : daftarKelasTersedia) {
            if (k.getkodeKelas().equalsIgnoreCase(kode))
                return k;
        }
        return null;
    }

    private static Transaksi findTransaksiById(String id) {
        for (Transaksi t : riwayatTransaksi) {
            if (t.getIdTransaksi().equalsIgnoreCase(id))
                return t;
        }
        return null;
    }

    // Data Dummy
    private static void dataDummy() {
        // === 6 KELAS ===
        Kelas[] kelasList = {
                new Kelas("MAT-01", "Matematika SMA Kelas 12", "Bapak Budi", 500000),
                new Kelas("FIS-01", "Fisika SMA Intensif", "Ibu Ani", 550000),
                new Kelas("BIG-01", "Bahasa Inggris TOEFL", "Mr. John", 450000),
                new Kelas("KIM-01", "Kimia SMA Olimpiade", "Bapak Eko", 520000),
                new Kelas("BIO-01", "Biologi SMA UTBK", "Ibu Rina", 480000),
                new Kelas("INF-01", "Informatika Python", "Bapak Diki", 600000)
        };

        for (Kelas k : kelasList) {
            daftarKelasTersedia.add(k);
        }
    }
    private static void dataSiswa() {
        // === 5 SISWA ===
        Siswa[] siswaList = {
                new Siswa("S-001", "Andi", "081234567890"),
                new Siswa("S-002", "Budi", "082345678901"),
                new Siswa("S-003", "Citra", "083456789012"),
                new Siswa("S-004", "Dewi", "084567890123"),
                new Siswa("S-005", "Eka", "085678901234"),
                new Siswa("S-006", "Fajar", "086789012345"),
                new Siswa("S-007", "Gina", "087890123456")
        };

        for (Siswa s : siswaList) {
            daftarSiswa.add(s);
        }
    }
}
