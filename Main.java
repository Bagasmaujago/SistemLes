import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {
    static ArrayList<Kelas> daftarKelasTersedia = new ArrayList<>();
    static ArrayList<Siswa> daftarSiswa = new ArrayList<>();
    static ArrayList<Transaksi> riwayatTransaksi = new ArrayList<>();
    static ArrayList<Kelas> daftarSemuaKelas = new ArrayList<>(); 
    static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        dataDummy();
        int pilihan = 0;

        System.out.println("==== Selamat Datang di kelas Les TI A ====");
        System.out.println("==== Selamat Datang di Sistem! ====");

        do {
            System.out.println("\n======== Menu UTAMA =======");
            System.out.println("1. Tampilkan Daftar Kelas yang Tersedia");
            System.out.println("2. Pendaftaran Siswa Baru");
            System.out.println("3. Pemilihan Kelas Siswa Baru");
            System.out.println("4. Proses Pembayaran");
            System.out.println("5. Cek Riwayat Transaksi");
            System.out.println("6. Lihat Daftar Siswa");
            System.out.println("7. Lihat kelas yang Diikuti Siswa");
            System.out.println("8. Menu Modifikasi Kelas");
            System.out.println("9. Keluar");
            System.out.print("Pilih Menu 1-9!: ");

            // Mengatasi jika menginput bukan angka
            try {
                pilihan = scanner.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("Input Tidak Valid Masukkan Angka!");
                pilihan = 0;
            } finally {
                scanner.nextLine();
            }
            // pilihan input
            switch (pilihan) {
                case 1 -> tampilkanDaftarKelas();
                case 2 -> registrasiSiswaBaru();
                case 3 -> pendaftaranKelas();
                case 4 -> prosesPembayaran();
                case 5 -> lihatRiwayatTransaksi();
                case 6 -> menuLihatSiswa();
                case 7 -> lihatKelasYangDiikuti();
                case 8 -> menuModifikasiKelas(); // PANGGIL SUB-MENU BARU
                case 9 -> System.out.println("\nTerima kasih telah menggunakan sistem ini. Sampai jumpa!");
                default -> {
                    if (pilihan != 0)
                        System.out.println("Pilihan tidak valid. Silakan pilih 1-9.");
                }
            }
        } while (pilihan != 9);
        
        scanner.close();
    }
    public static void menuModifikasiKelas() {
        int subPilihan = 0;
        do {
            System.out.println("\n=== MENU MODIFIKASI KELAS ===");
            System.out.println("1. Tambah Kelas Baru");
            System.out.println("2. Nonaktifkan Kelas");
            System.out.println("3. Aktifkan Kembali Kelas"); // FITUR BARU
            System.out.println("0. Kembali ke Menu Utama");
            System.out.print("Pilih menu: ");

            try {
                subPilihan = scanner.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("Input harus angka!");
                subPilihan = -1;
            } finally {
                scanner.nextLine();
            }

            switch (subPilihan) {
                case 1 -> tambahKelasBaru();
                case 2 -> nonaktifkanKelas();
                case 3 -> aktifkanKelasKembali();
                case 0 -> System.out.println("Kembali ke menu utama...");
                default -> System.out.println("Pilihan tidak valid.");
            }
        } while (subPilihan != 0);
    }
    // 1. Menampilkan daftar Kelas yang Tersedia (Hanya yang Aktif)
    public static void tampilkanDaftarKelas() {
        System.out.println("\n===== DAFTAR KELAS YANG TERSEDIA =====");
        if (daftarKelasTersedia.isEmpty()) {
            System.out.println("Belum ada kelas tersedia / aktif.");
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

    // 2. Tambah Kelas Baru
    public static void tambahKelasBaru() {
        System.out.println("\n=== TAMBAH KELAS BARU ===");
        System.out.print("Kode Kelas (contoh: FIS-02): ");
        String kode = scanner.nextLine().trim().toUpperCase();

        // Cek apakah kode sudah ada di database (baik aktif maupun nonaktif)
        for (Kelas k : daftarSemuaKelas) {
            if (k.getkodeKelas().equalsIgnoreCase(kode)) {
                System.out.println("Gagal: Kode kelas sudah terdaftar di database!");
                if (!daftarKelasTersedia.contains(k)) {
                    System.out.println("Tips: Kelas ini sedang NONAKTIF. Gunakan menu 'Aktifkan Kembali Kelas' untuk menggunakannya.");
                }
                return;
            }
        }

        System.out.print("Nama Kelas       : ");
        String nama = scanner.nextLine();
        System.out.print("Nama Pengajar    : ");
        String pengajar = scanner.nextLine();
        System.out.print("Harga Kelas (Rp) : ");
        int harga = 0;
        try {
            harga = scanner.nextInt();
        } catch (InputMismatchException e) {
            System.out.println("Harga harus angka!");
            return;
        } finally {
            scanner.nextLine();
        }

        Kelas kelasBaru = new Kelas(kode, nama, pengajar, harga);
        daftarKelasTersedia.add(kelasBaru);
        daftarSemuaKelas.add(kelasBaru); // Tambahkan juga ke master data

        System.out.println("Kelas berhasil ditambahkan dan langsung aktif!");
    }

    // 3. Nonaktifkan Kelas
    public static void nonaktifkanKelas() {
        System.out.println("\n=== NONAKTIFKAN KELAS ===");
        if (daftarKelasTersedia.isEmpty()) {
            System.out.println("Tidak ada kelas yang aktif.");
            return;
        }

        tampilkanDaftarKelas();
        System.out.print("\nMasukkan Kode Kelas yang ingin dinonaktifkan: ");
        String kode = scanner.nextLine().trim().toUpperCase();

        Kelas target = null;
        for (Kelas k : daftarKelasTersedia) {
            if (k.getkodeKelas().equalsIgnoreCase(kode)) {
                target = k;
                break;
            }
        }

        if (target == null) {
            System.out.println("Kelas tidak ditemukan di daftar aktif.");
            return;
        }

        if (!target.getDaftarSiswa().isEmpty()) {
            System.out.println("GAGAL! Kelas ini masih memiliki " + target.getDaftarSiswa().size() + " siswa.");
            System.out.println("Kelas tidak boleh dinonaktifkan jika masih ada siswa.");
            return;
        }

        daftarKelasTersedia.remove(target); // Hanya hapus dari daftarTersedia, tapi tetap ada di daftarSemuaKelas
        System.out.println("Kelas \"" + target.getnamaKelas() + "\" berhasil dinonaktifkan (Diarsipkan).");
    }

    // 4. Aktifkan Kembali Kelas 
    public static void aktifkanKelasKembali() {
        System.out.println("\n=== AKTIFKAN KEMBALI KELAS ===");
        // Cari kelas yang ada di daftarSemuaKelas TAPI TIDAK ADA di daftarKelasTersedia
        ArrayList<Kelas> kelasNonaktif = new ArrayList<>();
        for (Kelas k : daftarSemuaKelas) {
            if (!daftarKelasTersedia.contains(k)) {
                kelasNonaktif.add(k);
            }
        }

        if (kelasNonaktif.isEmpty()) {
            System.out.println("Tidak ada kelas nonaktif yang bisa dipulihkan.");
            return;
        }

        System.out.println("Daftar Kelas Nonaktif:");
        System.out.println("-------------------------------------------------");
        for (Kelas k : kelasNonaktif) {
            System.out.printf("- %s | %s (Pengajar: %s)\n", k.getkodeKelas(), k.getnamaKelas(), k.getpengajar());
        }
        System.out.println("-------------------------------------------------");

        System.out.print("Masukkan Kode Kelas untuk diaktifkan kembali: ");
        String kode = scanner.nextLine().trim().toUpperCase();

        Kelas target = null;
        for (Kelas k : kelasNonaktif) {
            if (k.getkodeKelas().equalsIgnoreCase(kode)) {
                target = k;
                break;
            }
        }

        if (target != null) {
            daftarKelasTersedia.add(target);
            System.out.println("Kelas " + target.getnamaKelas() + " BERHASIL diaktifkan kembali!");
        } else {
            System.out.println("Kode kelas tidak valid atau sudah aktif.");
        }
    }
    // Membuat Form Registrasi Siswa Baru
    public static Siswa registrasiSiswaBaru() {
        System.out.println("====== REGISTRASI SISWA BARU =========");
        System.out.print("Masukkan Nama Siswa Baru: ");
        String nama = scanner.nextLine();
        System.out.print("Masukkan Nomor Telpon: ");
        String noTelp = scanner.nextLine();

        System.out.print("Masukkan Tahun Siswa (contoh: 2024): ");
        int tahun = 0;
        try {
            tahun = scanner.nextInt();
        } catch(InputMismatchException e) {
            tahun = 2024; // default
        } finally {
            scanner.nextLine();
        }

        String idSiswa = "S-" + String.format("%03d", daftarSiswa.size() + 1);

        Siswa siswaBaru = new Siswa(idSiswa, nama, noTelp, tahun);
        daftarSiswa.add(siswaBaru);

        System.out.println("Siswa Baru Berhasil Ditambahkan!");
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

        System.out.println("─── Detail Tagihan ───");
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
            // Gunakan findKelasByKodeDiSemua() jika kelas sudah dinonaktifkan agar nama tetap muncul
            Kelas k = findKelasByKodeInAll(t.getKodeKelas()); 
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
    }

    public static void menuLihatSiswa() {
        while (true) {
            System.out.println("\n=== MENU LIHAT DATA SISWA ===");
            System.out.println("1. Lihat Semua Siswa");
            System.out.println("2. Lihat Siswa Per Kelas");
            System.out.println("3. Lihat Siswa Berdasarkan Tahun");
            System.out.println("0. Kembali");
            System.out.print("Pilih menu: ");

            int p = -1;
            try {
                 p = scanner.nextInt();
            } catch (Exception e) {}
            scanner.nextLine();

            switch (p) {
                case 1 -> lihatDaftarSemuaSiswa();
                case 2 -> lihatDaftarSiswaPerKelas();
                case 3 -> lihatDaftarSiswaPerTahun();
                case 0 -> {
                    return;
                }
                default -> System.out.println("Pilihan tidak valid!");
            }
        }
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

    public static void lihatDaftarSiswaPerTahun() {
        System.out.print("\nMasukkan tahun siswa: ");
        int tahun = 0;
        try {
             tahun = scanner.nextInt();
        } catch(Exception e) {}
        scanner.nextLine();

        System.out.println("\n=== DAFTAR SISWA TAHUN " + tahun + " ===");
        boolean ditemukan = false;

        System.out.println("─────────────────────────────────────────────────");
        System.out.printf("| %-10s | %-20s | %-15s |\n", "ID", "Nama", "Telepon");
        System.out.println("─────────────────────────────────────────────────");

        for (Siswa s : daftarSiswa) {
            if (s.getTahun() == tahun) {
                System.out.printf("| %-10s | %-20s | %-15s |\n",
                        s.getidSiswa(), s.getnamaSiswa(), s.getnoTelpon());
                ditemukan = true;
            }
        }

        if (!ditemukan) {
            System.out.println("Tidak ada siswa pada tahun tersebut.");
        }

        System.out.println("─────────────────────────────────────────────────");
    }

    // Lihat kelas yang diikuti siswa
    public static void lihatKelasYangDiikuti() {
        System.out.println("\n=== LIHAT KELAS YANG DIIKUTI SISWA ===");
        System.out.print("Masukkan Nama atau ID Siswa: ");
        String input = scanner.nextLine().trim();

        // Cari siswa (by ID atau Nama)
        Siswa siswa = findSiswaById(input);
        if (siswa == null) {
            siswa = findSiswaByNama(input);
        }

        if (siswa == null) {
            System.out.println("Siswa tidak ditemukan.");
            return;
        }

        System.out.println("\nNama        : " + siswa.getnamaSiswa());
        System.out.println("ID Siswa    : " + siswa.getidSiswa());
        System.out.println("\nKELAS YANG DIIKUTI:");

        boolean adaKelas = false;

        for (Transaksi trx : riwayatTransaksi) {
            if (trx.getIdSiswa().equals(siswa.getidSiswa())) {

                Kelas kelas = findKelasByKodeInAll(trx.getKodeKelas()); // Cari di semua kelas (termasuk nonaktif)
                if (kelas != null) {
                    adaKelas = true;
                    String statusKelas = daftarKelasTersedia.contains(kelas) ? "Aktif" : "Nonaktif";
                    System.out.println("- " + kelas.getkodeKelas()
                            + " | " + kelas.getnamaKelas()
                            + " | Bayar: " + trx.getStatusBayar()
                            + " | Ket: " + statusKelas);
                }
            }
        }

        if (!adaKelas) {
            System.out.println("- Belum mengikuti kelas apapun");
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

    private static Siswa findSiswaByNama(String nama) {
        for (Siswa s : daftarSiswa) {
            if (s.getnamaSiswa().equalsIgnoreCase(nama)) {
                return s;
            }
        }
        return null;
    }

    // Mencari hanya di kelas yang aktif
    private static Kelas findKelasByKode(String kode) {
        for (Kelas k : daftarKelasTersedia) {
            if (k.getkodeKelas().equalsIgnoreCase(kode))
                return k;
        }
        return null;
    }

    // Mencari di semua kelas (Aktif maupun Nonaktif) - berguna untuk riwayat
    private static Kelas findKelasByKodeInAll(String kode) {
        for (Kelas k : daftarSemuaKelas) {
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
        // === 1. MEMBUAT 6 KELAS ===
        daftarKelasTersedia.add(new Kelas("MAT-01", "Matematika SMA Kelas 12", "Bapak Budi", 500000));
        daftarKelasTersedia.add(new Kelas("FIS-01", "Fisika SMA Intensif", "Ibu Ani", 550000));
        daftarKelasTersedia.add(new Kelas("BIG-01", "Bahasa Inggris TOEFL", "Mr. John", 450000));
        daftarKelasTersedia.add(new Kelas("KIM-01", "Kimia SMA Olimpiade", "Bapak Eko", 520000));
        daftarKelasTersedia.add(new Kelas("BIO-01", "Biologi SMA UTBK", "Ibu Rina", 480000));
        daftarKelasTersedia.add(new Kelas("INF-01", "Informatika Python", "Bapak Diki", 600000));

        daftarSemuaKelas.addAll(daftarKelasTersedia);

        // === 2. MEMBUAT 60 NAMA SISWA ===
        String[] namaSiswa = {
            // 1-10
            "Andi Pratama", "Siti Rahma", "Dina Lestari", "Fajar Aditya", "Riko Prasetyo",
            "Melisa Handayani", "Bagus Firmansyah", "Rani Octavia", "Aldi Kurniawan", "Nia Safitri",
            // 11-20
            "Rafi Ramadhan", "Putri Anindya", "Kevin Saputra", "Yulia Febriani", "Dimas Herlambang",
            "Zahra Aulia", "Farhan Nur", "Intan Maharani", "Steven Hartanto", "Laila Amalia",
            // 21-30
            "Mira", "Jordan Halim", "Sofia Mei", "Rizky Akbar", "Clara Widjaya",
            "Hamzah Karim", "Nadia Putri", "Theo Januar", "Ayunda Kinasih", "Rehan Ardi",
            // 31-40
            "Luthfi Ramli", "Shafira Hanum", "Adnan Yusuf", "Karina Dewi", "William Seno",
            "Fitri Andriana", "Genta Mahesa", "Nabila Kumalasari", "Arya Wirawan", "Selin Oktaviani",
            // 41-50
            "Arman Putra", "Vina Kharisma", "Dewa Hartono", "Sheila Putu", "Ferry Nugraha",
            "Hanna Rosalina", "Yoga Pradana", "Laras Nirmala", "Denny Alvaro", "Fadhila Khairun",
            // 51-60
            "Bryan Jonathan", "Citra Indah", "Damar Wibowo", "Sherly Anggraini", "Aldi Firmanto",
            "Yessi Komalasari", "Rangga Setiawan", "Fira Zahra", "Neo Pratomo", "Revi Andrea"
        };

        // === 3. INPUT SISWA DENGAN TAHUN BERBEDA ===
        for (int i = 0; i < namaSiswa.length; i++) {
            String id = "S-" + String.format("%03d", (i + 1));
            String telp = "08" + (long) (Math.random() * 10000000000L);
            int tahun;
            if (i < 20) {
                tahun = 2023;
            } else if (i < 40) {
                tahun = 2024;
            } else {
                tahun = 2025;
            }
            Siswa s = new Siswa(id, namaSiswa[i], telp, tahun);
            daftarSiswa.add(s);
        }

        // === 4. MASUKKAN SISWA KE KELAS ===
        for (int i = 0; i < daftarKelasTersedia.size(); i++) {
            Kelas kelas = daftarKelasTersedia.get(i);
            int start = i * 10;
            int end = start + 10;
            for (int j = start; j < end; j++) {
                if (j < daftarSiswa.size()) {
                    kelas.tambahSiswa(daftarSiswa.get(j));
                }
            }
        }

        // === 5. GENERATE TRANSAKSI ===
        int trxCounter = 1;
        for (Kelas kelas : daftarKelasTersedia) {
            for (Siswa s : kelas.getDaftarSiswa()) {
                String idTrx = "T-" + String.format("%03d", trxCounter++);
                String status = Math.random() < 0.5 ? "LUNAS" : "BELUM LUNAS";
                Transaksi t = new Transaksi(idTrx, s.getidSiswa(), kelas.getkodeKelas(), kelas.getharga(), status);
                riwayatTransaksi.add(t);
            }
        }
    }
}