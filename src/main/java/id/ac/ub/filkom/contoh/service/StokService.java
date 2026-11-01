package id.ac.ub.filkom.contoh.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import id.ac.ub.filkom.contoh.entity.Buku;
import id.ac.ub.filkom.contoh.repository.BukuRepository;

/** «control» UC-02 Kelola Stok Buku. */
@Service
public class StokService {

    private final BukuRepository bukuRepository;

    public StokService(BukuRepository bukuRepository) {
        this.bukuRepository = bukuRepository;
    }

    public List<Buku> daftarBuku() {
        return bukuRepository.findAll();
    }

    /** [2] aturStok(idBuku, stokBaru) */
    @Transactional
    public Buku aturStok(String idBuku, int stokBaru) {
        // [3] findById -> [4] buku (alur data tidak valid: buku tidak ada)
        Buku buku = bukuRepository.findById(idBuku)
                .orElseThrow(StokGagalException::tidakDitemukan);

        try {
            buku.aturStok(stokBaru);                        // [5] -> [6] ok
        } catch (IllegalArgumentException e) {
            throw StokGagalException.stokTidakValid();      // [11] -> [12]
        }

        return bukuRepository.save(buku);                   // [7] -> [8] -> [9]
    }
}