package id.ac.ub.filkom.contoh.service;

/** Alur gagal UC-03 Ajukan Reservasi. */
public class ReservasiGagalException extends RuntimeException {

    private ReservasiGagalException(String pesan) {
        super(pesan);
    }

    public static ReservasiGagalException tidakDitemukan(String apa) {
        return new ReservasiGagalException(apa + " tidak ditemukan");
    }

    public static ReservasiGagalException masihTersedia() {
        return new ReservasiGagalException(
                "Buku masih tersedia, silakan langsung dipinjam");
    }

    public static ReservasiGagalException sudahAda() {
        return new ReservasiGagalException(
                "Kamu sudah mereservasi buku ini");
    }
}