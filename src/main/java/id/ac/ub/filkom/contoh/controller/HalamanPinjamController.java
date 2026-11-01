package id.ac.ub.filkom.contoh.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import id.ac.ub.filkom.contoh.service.Bukti;
import id.ac.ub.filkom.contoh.service.PinjamGagalException;
import id.ac.ub.filkom.contoh.service.PinjamService;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import id.ac.ub.filkom.contoh.service.ReservasiGagalException;
import id.ac.ub.filkom.contoh.service.ReservasiService;
import id.ac.ub.filkom.contoh.entity.Reservasi;

@Controller
@RequestMapping("/pinjam")
public class HalamanPinjamController {
        private final PinjamService pinjamService;           // «control» UC-01
        private final ReservasiService reservasiService;     // «control» UC-03

    public HalamanPinjamController(PinjamService pinjamService,
                                   ReservasiService reservasiService) {
        this.pinjamService = pinjamService;
        this.reservasiService = reservasiService;
    }

    @GetMapping
    public String tampilHalaman(HttpSession session, Model model) {
        model.addAttribute("idAnggota", idAnggotaLogin(session));
        model.addAttribute("daftarBuku", pinjamService.daftarBuku());
        return "pinjam"; 
    }
    @PostMapping
    public String pilihBuku(@RequestParam String idBuku,
    HttpSession session, Model model) {
    String idAnggota = idAnggotaLogin(session);
        try {
            Bukti bukti = pinjamService.pinjamBuku(idAnggota, idBuku);
            return tampilBukti(bukti, model);     
            } catch (PinjamGagalException e) {    
            // [13] pesanHabis
            model.addAttribute("pesan", e.getMessage());
            model.addAttribute("bisaReservasi", e.isBisaReservasi());
            model.addAttribute("idBukuGagal", idBuku);      
            return tampilHalaman(session, model); // [14] pesan & reservasi
        } 
    }

    private String tampilBukti(Bukti bukti, Model model) {
        model.addAttribute("bukti", bukti);
        return "bukti"; 
    }

        /** UC-03 [1] klik "Ajukan reservasi" («extend» dari UC-01 alt stok habis). */
    @PostMapping("/reservasi")
    public String ajukanReservasi(@RequestParam String idBuku,
                                  HttpSession session,
                                  RedirectAttributes redirect) {
        String idAnggota = idAnggotaLogin(session);
        try {
            Reservasi r = reservasiService.ajukanReservasi(idAnggota, idBuku);   // [2]
            redirect.addFlashAttribute("sukses",                                 // [16]
                    "Reservasi berhasil diajukan. No. reservasi: " + r.getIdReservasi());
        } catch (ReservasiGagalException e) {                                    // [17]
            redirect.addFlashAttribute("pesan", e.getMessage());                 // [18]
        }
        return "redirect:/pinjam";
    }
        private String idAnggotaLogin(HttpSession session) {
        return "A001";   // sementara di-hardcode
    }
}