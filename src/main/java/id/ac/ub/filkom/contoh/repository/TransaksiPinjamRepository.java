package id.ac.ub.filkom.contoh.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import id.ac.ub.filkom.contoh.entity.TransaksiPinjam;

public interface TransaksiPinjamRepository extends JpaRepository<TransaksiPinjam, String> {
    
}
