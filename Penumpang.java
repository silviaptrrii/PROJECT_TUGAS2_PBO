package Project_Tugas2_PBO;

public class Penumpang {
    // Atribut menggunakan akses private untuk menjaga keamanan data (Encapsulation)
    private String nama;
    private int id;
    private int umur;
    private boolean hamil;
    private int saldo;

    // Konstruktor untuk inisialisasi objek penumpang dengan nilai awal
    public Penumpang(String nama, int id, int umur, boolean hamil) {
        this.nama = nama;
        this.id = id;
        this.umur = umur;
        this.hamil = hamil;
        // Saldo awal ditetapkan sebesar 10.000 sesuai ketentuan sistem
        this.saldo = 10000; 
    }

    // Bagian Accessor (Getter) untuk membaca nilai atribut

    public String getNama() {
        return nama;
    }

    public int getId() {
        return id;
    }

    public int getUmur() {
        return umur;
    }

    public boolean getHamil() {
        return hamil;
    }

    public int getSaldo() {
        return saldo;
    }

    // Metode untuk melakukan penambahan saldo (Top-up)
    public void tambahSaldo(int nominal) {
        this.saldo += nominal;
    }

    // Metode untuk pembayaran ongkos, saldo akan dikurangi
    public void kurangiSaldo(int biaya) {
        this.saldo -= biaya;
    }
}
