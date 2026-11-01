package id.ac.ub.filkom.contoh.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

/** Entity: objek data yang dipetakan ke tabel "buku". */
@Entity
@Table(name = "buku")
public class Buku {

    @Id
    @Column(name = "id_buku", length = 10)
    private String idBuku;

    @Column(name = "judul", length = 200, nullable = false)
    private String judul;

    @Column(name = "stok")
    private int stok;

    protected Buku() { }               // wajib ada untuk JPA

    public Buku(String idBuku, String judul, int stok) {
        this.idBuku = idBuku;
        this.judul = judul;
        this.stok = stok;
    }

    public boolean cekStok(){
        return stok  > 0;
    }
    public void kurangiStok(){
        if(stok <= 0){
            throw new IllegalStateException("Stok sudah habis");
        }
        this.stok --;
    }
        /** +aturStok(stokBaru : int) : void */
    public void aturStok(int stokBaru) {
        if (stokBaru < 0) {
            throw new IllegalArgumentException("Stok tidak boleh negatif");
        }
        this.stok = stokBaru;
    }
    public String getIdBuku() { return idBuku; }
    public String getJudul()  { return judul; }
    public int getStok()      { return stok; }
}
