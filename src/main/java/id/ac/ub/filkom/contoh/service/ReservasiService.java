package id.ac.ub.filkom.contoh.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import id.ac.ub.filkom.contoh.entity.Anggota;
import id.ac.ub.filkom.contoh.entity.Buku;
import id.ac.ub.filkom.contoh.entity.Reservasi;
import id.ac.ub.filkom.contoh.repository.AnggotaRepository;
import id.ac.ub.filkom.contoh.repository.BukuRepository;
import id.ac.ub.filkom.contoh.repository.ReservasiRepository;

/** «control» UC-03 Ajukan Reservasi («extend» UC-01 Pinjam Buku). */
@Service
public class ReservasiService {

    private final AnggotaRepository anggotaRepository;
    private final BukuRepository bukuRepository;
    private final ReservasiRepository reservasiRepository;

    public ReservasiService(AnggotaRepository anggotaRepository,
                            BukuRepository bukuRepository,
                            ReservasiRepository reservasiRepository) {
        this.anggotaRepository = anggotaRepository;
        this.bukuRepository = bukuRepository;
        this.reservasiRepository = reservasiRepository;
    }

    /** [2] ajukanReservasi(idAnggota, idBuku) : Reservasi */
    @Transactional
    public Reservasi ajukanReservasi(String idAnggota, String idBuku) {
        // [3] findById -> [4] anggota
        Anggota anggota = anggotaRepository.findById(idAnggota)
                .orElseThrow(() -> ReservasiGagalException.tidakDitemukan("Anggota"));
        // [5] findById -> [6] buku
        Buku buku = bukuRepository.findById(idBuku)
                .orElseThrow(() -> ReservasiGagalException.tidakDitemukan("Buku"));

        // [7] cekStok() -> [8] stok habis / tersedia
        if (buku.cekStok()) {
            throw ReservasiGagalException.masihTersedia();            // [17]
        }

        // [9] existsBy... -> [10] belum direservasi
        if (reservasiRepository.existsByAnggota_IdAnggotaAndBuku_IdBukuAndStatus(
                idAnggota, idBuku, Reservasi.MENUNGGU)) {
            throw ReservasiGagalException.sudahAda();                 // [17]
        }

        // [11] buat(anggota, buku) -> [12] reservasi
        Reservasi reservasi = Reservasi.buat(anggota, buku);
        // [13] save(reservasi) -> [14] ok
        return reservasiRepository.save(reservasi);                   // [15]
    }
}