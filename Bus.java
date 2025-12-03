package Project_Tugas2_PBO;

import java.util.ArrayList;

public class Bus {
    // Menggunakan ArrayList sebagai penyimpanan data penumpang agar dinamis
    private ArrayList<Penumpang> listPenumpangBiasa;
    private ArrayList<Penumpang> listPenumpangPrioritas;
    private ArrayList<Penumpang> listPenumpangBerdiri;

    // Nilai ketetapan untuk ongkos dan variabel akumulasi pendapatan
    private static final int ONGKOS_BUS = 2000;
    private int pendapatanHarian;

    // Batas kapasitas maksimum untuk setiap kategori kursi
    private final int MAX_BIASA = 16;
    private final int MAX_PRIORITAS = 4;
    private final int MAX_BERDIRI = 20;

    // Menyiapkan kondisi awal bus (kosong dan pendapatan 0)
    public Bus() {
        listPenumpangBiasa = new ArrayList<>();
        listPenumpangPrioritas = new ArrayList<>();
        listPenumpangBerdiri = new ArrayList<>();
        pendapatanHarian = 0;
    }

    // Metode Bantuan untuk pengecekan jumlah penumpang
    public int getJumlahBiasa() { return listPenumpangBiasa.size(); }
    public int getJumlahPrioritas() { return listPenumpangPrioritas.size(); }
    public int getJumlahBerdiri() { return listPenumpangBerdiri.size(); }
    public int getTotalPendapatan() { return pendapatanHarian; }

    // Logika utama menaikkan penumpang ke dalam bus
    public boolean naikkanPenumpang(Penumpang p) {
        // 1. Validasi saldo penumpang terlebih dahulu
        if (p.getSaldo() < ONGKOS_BUS) {
            System.out.println("Gagal: Saldo kartu tidak mencukupi (Sisa: " + p.getSaldo() + ")");
            return false;
        }

        // 2. Validasi kapasitas total bus (Maksimal 40 orang)
        int totalSaatIni = listPenumpangBiasa.size() + listPenumpangPrioritas.size() + listPenumpangBerdiri.size();
        if (totalSaatIni >= 40) {
            System.out.println("Info: Kapasitas bus telah penuh total.");
            return false;
        }

        // 3. Pengecekan status prioritas (Lansia > 60, Anak < 10, atau Hamil)
        boolean statusPrioritas = p.getUmur() > 60 || p.getUmur() < 10 || p.getHamil();
        boolean statusNaik = false;
        String jenisKursi = "";

        if (statusPrioritas) {
            // Jika Prioritas: Utamakan kursi prioritas -> kursi biasa -> berdiri
            if (listPenumpangPrioritas.size() < MAX_PRIORITAS) {
                listPenumpangPrioritas.add(p);
                jenisKursi = "KURSI PRIORITAS";
                statusNaik = true;
            } else if (listPenumpangBiasa.size() < MAX_BIASA) {
                listPenumpangBiasa.add(p);
                jenisKursi = "KURSI BIASA (Prioritas Penuh)";
                statusNaik = true;
            } else if (listPenumpangBerdiri.size() < MAX_BERDIRI) {
                listPenumpangBerdiri.add(p);
                jenisKursi = "AREA BERDIRI";
                statusNaik = true;
            }
        } else {
            // Jika Penumpang Biasa: Hanya boleh kursi biasa atau berdiri
            if (listPenumpangBiasa.size() < MAX_BIASA) {
                listPenumpangBiasa.add(p);
                jenisKursi = "KURSI BIASA";
                statusNaik = true;
            } else if (listPenumpangBerdiri.size() < MAX_BERDIRI) {
                listPenumpangBerdiri.add(p);
                jenisKursi = "AREA BERDIRI";
                statusNaik = true;
            }
        }

        // Proses transaksi jika penumpang mendapatkan tempat
        if (statusNaik) {
            p.kurangiSaldo(ONGKOS_BUS);
            pendapatanHarian += ONGKOS_BUS;
            System.out.println("Berhasil: " + p.getNama() + " masuk ke " + jenisKursi + ".");
            return true;
        } else {
            System.out.println("Gagal: Tidak tersedia kursi yang sesuai untuk penumpang ini.");
            return false;
        }
    }

    // Logika menurunkan penumpang berdasarkan pencarian nama
    public boolean turunkanPenumpang(String namaTarget) {
        for (int i = 0; i < listPenumpangBiasa.size(); i++) {
            if (listPenumpangBiasa.get(i).getNama().equalsIgnoreCase(namaTarget)) {
                listPenumpangBiasa.remove(i);
                System.out.println("Sukses: " + namaTarget + " turun dari Kursi Biasa.");
                return true;
            }
        }
        for (int i = 0; i < listPenumpangPrioritas.size(); i++) {
            if (listPenumpangPrioritas.get(i).getNama().equalsIgnoreCase(namaTarget)) {
                listPenumpangPrioritas.remove(i);
                System.out.println("Sukses: " + namaTarget + " turun dari Kursi Prioritas.");
                return true;
            }
        }
        for (int i = 0; i < listPenumpangBerdiri.size(); i++) {
            if (listPenumpangBerdiri.get(i).getNama().equalsIgnoreCase(namaTarget)) {
                listPenumpangBerdiri.remove(i);
                System.out.println("Sukses: " + namaTarget + " turun dari Area Berdiri.");
                return true;
            }
        }
        System.out.println("Peringatan: Data penumpang dengan nama " + namaTarget + " tidak ditemukan.");
        return false;
    }

    public void topUpSaldoPenumpang(String nama, int nominal) {
        if (cekDanTopUp(listPenumpangBiasa, nama, nominal)) return;
        if (cekDanTopUp(listPenumpangPrioritas, nama, nominal)) return;
        if (cekDanTopUp(listPenumpangBerdiri, nama, nominal)) return;
        
        System.out.println("Gagal: Penumpang bernama " + nama + " tidak ditemukan di dalam bus.");
    }

    private boolean cekDanTopUp(ArrayList<Penumpang> list, String nama, int nominal) {
        for (Penumpang p : list) {
            if (p.getNama().equalsIgnoreCase(nama)) {
                p.tambahSaldo(nominal);
                System.out.println("Sukses: Saldo " + nama + " bertambah Rp " + nominal + ". Total: " + p.getSaldo());
                return true;
            }
        }
        return false;
    }

    // Menampilkan laporan status bus dalam bentuk String
    public String toString() {
        String laporan = "\n=== LAPORAN STATUS BUS ===\n";
        
        laporan += ">> Kursi Biasa (" + getJumlahBiasa() + "/" + MAX_BIASA + "):\n";
        if (listPenumpangBiasa.isEmpty()) laporan += "   - (Tidak ada penumpang)\n";
        else for (Penumpang p : listPenumpangBiasa) laporan += "   - " + p.getNama() + " (Saldo: " + p.getSaldo() + ")\n";

        laporan += "\n>> Kursi Prioritas (" + getJumlahPrioritas() + "/" + MAX_PRIORITAS + "):\n";
        if (listPenumpangPrioritas.isEmpty()) laporan += "   - (Tidak ada penumpang)\n";
        else for (Penumpang p : listPenumpangPrioritas) laporan += "   - " + p.getNama() + " (Saldo: " + p.getSaldo() + ")\n";

        laporan += "\n>> Area Berdiri (" + getJumlahBerdiri() + "/" + MAX_BERDIRI + "):\n";
        if (listPenumpangBerdiri.isEmpty()) laporan += "   - (Tidak ada penumpang)\n";
        else for (Penumpang p : listPenumpangBerdiri) laporan += "   - " + p.getNama() + " (Saldo: " + p.getSaldo() + ")\n";
        
        return laporan;
    }
}
