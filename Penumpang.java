package Project_Tugas2_PBO; 

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        // Membuat objek Bus baru
        Bus busTrans = new Bus();
        Scanner sc = new Scanner(System.in); // Scanner untuk menerima input
        int idCounter = 1; // ID otomatis bertambah

        // Loop menu utama agar program terus berjalan
        while (true) {
            System.out.println("\n===== BUS TRANS KOETARADJA =====");
            System.out.println("1. Naikkan Penumpang");
            System.out.println("2. Turunkan Penumpang");
            System.out.println("3. Lihat Status Penumpang");
            System.out.println("4. Keluar");
            System.out.print("Pilihan: ");
            
            int pilihan = 0;
            try {
                pilihan = sc.nextInt(); // Membaca input angka
            } catch (Exception e) {
                System.out.println("Input harus berupa angka.");
                sc.nextLine(); // Membersihkan buffer input
                continue;
            }
            sc.nextLine(); // Membersihkan karakter baris baru (enter)

            if (pilihan == 1) {
                // Input data penumpang
                System.out.print("Nama: ");
                String nama = sc.nextLine();
                
                System.out.print("Umur: ");
                int umur = sc.nextInt();
                
                System.out.print("Hamil (y/n): ");
                String hamilStr = sc.next();
                boolean hamil = hamilStr.equalsIgnoreCase("y"); // Bernilai true jika input 'y'

                // Membuat objek penumpang dan memasukkannya ke bus
                Penumpang p = new Penumpang(nama, idCounter++, umur, hamil);
                busTrans.naikkanPenumpang(p);

            } else if (pilihan == 2) {
                // Input nama penumpang yang akan turun
                System.out.print("Nama penumpang yang turun: ");
                String nama = sc.nextLine();
                busTrans.turunkanPenumpang(nama);

            } else if (pilihan == 3) {
                // Menampilkan laporan
                System.out.println(busTrans.toString());

            } else if (pilihan == 4) {
                System.out.println("Terima kasih.");
                break; // Menghentikan program
            } else {
                System.out.println("Pilihan tidak valid.");
            }
        }
        sc.close();
    }
}
