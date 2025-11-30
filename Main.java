package Project_Tugas2_PBO;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Bus busSistem = new Bus(); // Membuat instance objek Bus
        Scanner inputScanner = new Scanner(System.in);
        int counterID = 1; // Generator ID otomatis

        while (true) {
            System.out.println("\n======================================");
            System.out.println("   SISTEM MANAJEMEN BUS KOETARADJA    ");
            System.out.println("======================================");
            System.out.println("[1] Registrasi Penumpang Masuk");
            System.out.println("[2] Proses Penumpang Turun");
            System.out.println("[3] Tampilkan Data Penumpang");
            System.out.println("[4] Cek Ketersediaan Kursi");
            System.out.println("[5] Laporan Total Pendapatan");
            System.out.println("[6] Top Up Saldo Penumpang");
            System.out.println("[0] Keluar dari Sistem");
            System.out.println("--------------------------------------");
            System.out.print("Masukkan pilihan menu (0-6): ");
            
            int pilihanMenu;
            try {
                pilihanMenu = inputScanner.nextInt();
            } catch (Exception e) {
                System.out.println("Error: Input harus berupa angka.");
                inputScanner.nextLine(); // Membersihkan buffer error
                continue;
            }
            inputScanner.nextLine(); // Membersihkan karakter enter

            if (pilihanMenu == 1) {
                // Proses Input Data Penumpang
                System.out.print("Nama: ");
                String inputNama = inputScanner.nextLine();
                
                System.out.print("Usia: ");
                int inputUmur = inputScanner.nextInt();
                
                System.out.print("Status Hamil (y/n): ");
                String inputHamil = inputScanner.next();
                boolean statusHamil = inputHamil.equalsIgnoreCase("y");

                // Input saldo untuk simulasi
                System.out.print("Saldo Kartu Awal (Rp): "); 
                int inputSaldo = inputScanner.nextInt();

                // Instansiasi objek penumpang baru
                Penumpang pBaru = new Penumpang(inputNama, counterID++, inputUmur, statusHamil);
                
                // Penyesuaian saldo sesuai input pengguna
                pBaru.kurangiSaldo(10000); // Reset saldo default
                pBaru.tambahSaldo(inputSaldo); // Set saldo baru

                // Eksekusi method naikkan penumpang
                busSistem.naikkanPenumpang(pBaru);

            } else if (pilihanMenu == 2) {
                System.out.print("Masukkan nama penumpang yang akan turun: ");
                String namaTurun = inputScanner.nextLine();
                busSistem.turunkanPenumpang(namaTurun);

            } else if (pilihanMenu == 3) {
                System.out.println(busSistem.toString());

            } else if (pilihanMenu == 4) {
                // Menampilkan statistik kursi
                System.out.println("\n--- Info jumlah penumpang ---");
                System.out.println("Kursi Biasa     : " + busSistem.getJumlahBiasa() + " terisi");
                System.out.println("Kursi Prioritas : " + busSistem.getJumlahPrioritas() + " terisi");
                System.out.println("Area Berdiri    : " + busSistem.getJumlahBerdiri() + " terisi");

            } else if (pilihanMenu == 5) {
                // Menampilkan pendapatan
                System.out.println("\n Total Pendapatan Bus: Rp " + busSistem.getTotalPendapatan());

            } else if (pilihanMenu == 6) {
                // --- INI LOGIKA TOP UP (AKU TAMBAHIN DI SINI) ---
                System.out.print("Nama Penumpang: ");
                String namaTopUp = inputScanner.nextLine();
                System.out.print("Nominal Top Up (Rp): ");
                int nominal = inputScanner.nextInt();
                busSistem.topUpSaldoPenumpang(namaTopUp, nominal);

            } else if (pilihanMenu == 0) {
                System.out.println("Sistem dihentikan. Terima kasih!!!.");
                break;
            } else {
                System.out.println("Pilihan tidak valid, silakan coba kembali.");
            }
        }
        inputScanner.close();
    }
}
