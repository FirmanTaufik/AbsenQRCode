package com.app.myapplication.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Rekap {

    @SerializedName("id_presensi")
    @Expose
    private String idPresensi;
    @SerializedName("Pertemuan")
    @Expose
    private String pertemuan;
    @SerializedName("Tanggal")
    @Expose
    private String tanggal;
    @SerializedName("Id_mk")
    @Expose
    private String idMk;
    @SerializedName("Id_mhs")
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
    private Object foto;

    public String getIdPresensi() {
        return idPresensi;
    }

    public void setIdPresensi(String idPresensi) {
        this.idPresensi = idPresensi;
    }

    public String getPertemuan() {
        return pertemuan;
    }

    public void setPertemuan(String pertemuan) {
        this.pertemuan = pertemuan;
    }

    public String getTanggal() {
        return tanggal;
    }

    public void setTanggal(String tanggal) {
        this.tanggal = tanggal;
    }

    public String getIdMk() {
        return idMk;
    }

    public void setIdMk(String idMk) {
        this.idMk = idMk;
    }

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

    public Object getFoto() {
        return foto;
    }

    public void setFoto(Object foto) {
        this.foto = foto;
    }

}
