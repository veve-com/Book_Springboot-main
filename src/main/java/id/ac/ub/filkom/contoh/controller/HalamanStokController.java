package id.ac.ub.filkom.contoh.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import id.ac.ub.filkom.contoh.service.StokGagalException;
import id.ac.ub.filkom.contoh.service.StokService;
import id.ac.ub.filkom.contoh.entity.Buku;

@Controller
@RequestMapping("/stok")
public class HalamanStokController {

    private final StokService stokService;          // «control»

    public HalamanStokController(StokService stokService) {
        this.stokService = stokService;
    }

    /** Tampilkan daftar buku beserta stok. */
    @GetMapping
    public String tampilHalaman(Model model) {
        model.addAttribute("daftarBuku", stokService.daftarBuku());
        return "stok";                              // templates/stok.html
    }

    /** [1] ubahStok(idBuku, stokBaru) dari aktor Pustakawan. */
    @PostMapping
    public String ubahStok(@RequestParam String idBuku,
                           @RequestParam int stokBaru,
                           RedirectAttributes redirect) {
        try {
            Buku buku = stokService.aturStok(idBuku, stokBaru);          // [2]
            redirect.addFlashAttribute("sukses",                         // [10]
                    "Stok " + buku.getJudul() + " menjadi " + buku.getStok());
        } catch (StokGagalException e) {                                 // [12]
            redirect.addFlashAttribute("pesan", e.getMessage());         // [13]
        }
        return "redirect:/stok";                    // pola Post-Redirect-Get
    }
}