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

        if (statusPrioritas) {
            // Jika Prioritas: Utamakan kursi prioritas -> kursi biasa -> berdiri
            if (listPenumpangPrioritas.size() < MAX_PRIORITAS) {
                listPenumpangPrioritas.add(p);
                statusNaik = true;
            } else if (listPenumpangBiasa.size() < MAX_BIASA) {
                listPenumpangBiasa.add(p);
                statusNaik = true;
            } else if (listPenumpangBerdiri.size() < MAX_BERDIRI) {
                listPenumpangBerdiri.add(p);
                statusNaik = true;
            }
        } else {
            // Jika Penumpang Biasa: Hanya boleh kursi biasa atau berdiri
            if (listPenumpangBiasa.size() < MAX_BIASA) {
                listPenumpangBiasa.add(p);
                statusNaik = true;
            } else if (listPenumpangBerdiri.size() < MAX_BERDIRI) {
                listPenumpangBerdiri.add(p);
                statusNaik = true;
            }
        }

        // Proses transaksi jika penumpang mendapatkan tempat
        if (statusNaik) {
            p.kurangiSaldo(ONGKOS_BUS);
            pendapatanHarian += ONGKOS_BUS;
            System.out.println("Berhasil: Penumpang atas nama " + p.getNama() + " telah naik.");
            return true;
        } else {
            System.out.println("Gagal: Tidak tersedia kursi yang sesuai untuk penumpang ini.");
            return false;
        }
    }

