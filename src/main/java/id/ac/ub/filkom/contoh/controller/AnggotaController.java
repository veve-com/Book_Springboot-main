package id.ac.ub.filkom.contoh.controller;

import id.ac.ub.filkom.contoh.service.AnggotaService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/** Controller: halaman lihat anggota. */
@Controller
public class AnggotaController {

    private final AnggotaService anggotaService;

    public AnggotaController(AnggotaService anggotaService) {
        this.anggotaService = anggotaService;
    }

    // GET http://localhost:8080/anggota
    @GetMapping("/anggota")
    public String lihatAnggota(Model model) {
        model.addAttribute("daftarAnggota", anggotaService.lihatSemuaAnggota());
        return "anggota";            // -> templates/anggota.html
    }
}