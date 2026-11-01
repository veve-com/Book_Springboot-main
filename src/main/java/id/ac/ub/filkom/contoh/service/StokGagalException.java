package id.ac.ub.filkom.contoh.service;

/** Alur gagal UC-02 Kelola Stok Buku. */
public class StokGagalException extends RuntimeException {

    private StokGagalException(String pesan) {
        super(pesan);
    }

    public static StokGagalException tidakDitemukan() {
        return new StokGagalException("Buku tidak ditemukan");
    }

    public static StokGagalException stokTidakValid() {
        return new StokGagalException("Stok tidak boleh negatif");
    }
}