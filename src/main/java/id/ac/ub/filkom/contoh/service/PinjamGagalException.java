package id.ac.ub.filkom.contoh.service;

/**
 * Exception untuk alur alternatif/gagal pada UC-01 Pinjam Buku.
 * Dilempar oleh PinjamService, ditangkap oleh HalamanPinjamController.
 */
public class PinjamGagalException extends RuntimeException {

    private final boolean bisaReservasi;

    private PinjamGagalException(String pesan, boolean bisaReservasi) {
        super(pesan);
        this.bisaReservasi = bisaReservasi;
    }

    public boolean isBisaReservasi() {
        return bisaReservasi;
    }

    /** Data tidak valid: idAnggota / idBuku tidak terdaftar. */
    public static PinjamGagalException tidakDitemukan(String apa) {
        return new PinjamGagalException(apa + " tidak ditemukan", false);
    }

    /** Alur 4b: anggota melewati batas pinjam. */
    public static PinjamGagalException melewatiBatas() {
        return new PinjamGagalException("Melewati batas pinjam", false);
    }

    /** Alur 4a / pesan [13] pesanHabis: stok buku habis, boleh reservasi. */
    public static PinjamGagalException stokHabis() {
        return new PinjamGagalException("Stok buku habis", true);
    }
}