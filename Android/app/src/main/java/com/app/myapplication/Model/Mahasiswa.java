package com.app.myapplication.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Mahasiswa {


    @SerializedName("id_mhs")
    @Expose
    private String idMhs;
    @SerializedName("NIM")
    @Expose
    private String nim;
    @SerializedName("Nama")
    @Expose
    private String nama;
    @SerializedName("Tempat_Tgl_Lhr")
    @Expose
    private String tempatTglLhr;
    @SerializedName("Jurusan")
    @Expose
    private String jurusan;
    @SerializedName("Foto")
    @Expose
    private String foto;
    @SerializedName("id_kelas")
    @Expose
    private String idKelas;
    @SerializedName("Nama_Kelas")
    @Expose
    private String namaKelas;
    @SerializedName("Tahun_ajaran")
    @Expose
    private String tahunAjaran;

    @SerializedName("Status")
    @Expose
    private int Status;


    public String getIdMhs() {
        return idMhs;
    }

    public void setIdMhs(String idMhs) {
        this.idMhs = idMhs;
    }

    public String getNim() {
        return nim;
    }

    public void setNim(String nim) {
        this.nim = nim;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getTempatTglLhr() {
        return tempatTglLhr;
    }

    public void setTempatTglLhr(String tempatTglLhr) {
        this.tempatTglLhr = tempatTglLhr;
    }

    public String getJurusan() {
        return jurusan;
    }

    public void setJurusan(String jurusan) {
        this.jurusan = jurusan;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public String getIdKelas() {
        return idKelas;
    }

    public void setIdKelas(String idKelas) {
        this.idKelas = idKelas;
    }

    public String getNamaKelas() {
        return namaKelas;
    }

    public void setNamaKelas(String namaKelas) {
        this.namaKelas = namaKelas;
    }

    public String getTahunAjaran() {
        return tahunAjaran;
    }

    public void setTahunAjaran(String tahunAjaran) {
        this.tahunAjaran = tahunAjaran;
    }

    public int getStatus() {
        return Status;
    }

    public void setStatus(int status) {
        Status = status;
    }

}
