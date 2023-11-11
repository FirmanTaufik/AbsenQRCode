package com.app.myapplication.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Absen {

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
    @SerializedName("id_mhs")
    @Expose
    private String idMhs;
    @SerializedName("NIM")
    @Expose
    private String nim;
    @SerializedName("Nama")
    @Expose
    private String nama;



    @SerializedName("Foto")
    @Expose
    private String Foto;

    public String getFoto() {
        return Foto;
    }

    public void setFoto(String foto) {
        Foto = foto;
    }

    public String getTempat_Tgl_Lhr() {
        return Tempat_Tgl_Lhr;
    }

    public void setTempat_Tgl_Lhr(String tempat_Tgl_Lhr) {
        Tempat_Tgl_Lhr = tempat_Tgl_Lhr;
    }

    @SerializedName("Tempat_Tgl_Lhr")
    @Expose
    private String Tempat_Tgl_Lhr;

    public String getJurusan() {
        return Jurusan;
    }

    public void setJurusan(String jurusan) {
        Jurusan = jurusan;
    }

    @SerializedName("Jurusan")
    @Expose
    private String Jurusan;



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

}
