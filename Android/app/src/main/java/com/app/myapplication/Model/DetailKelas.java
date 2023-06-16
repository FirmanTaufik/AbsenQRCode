package com.app.myapplication.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class DetailKelas {

    @SerializedName("kelas")
    @Expose
    private Kelas kelas;
    @SerializedName("mengajar")
    @Expose
    private Mengajar mengajar;
    @SerializedName("pertemuan")
    @Expose
    private List<Pertemuan> pertemuan;

    public Kelas getKelas() {
        return kelas;
    }

    public void setKelas(Kelas kelas) {
        this.kelas = kelas;
    }

    public Mengajar getMengajar() {
        return mengajar;
    }

    public void setMengajar(Mengajar mengajar) {
        this.mengajar = mengajar;
    }

    public List<Pertemuan> getPertemuan() {
        return pertemuan;
    }

    public void setPertemuan(List<Pertemuan> pertemuan) {
        this.pertemuan = pertemuan;
    }

    public class Kelas {

        @SerializedName("id_kelas")
        @Expose
        private String idKelas;
        @SerializedName("Nama_Kelas")
        @Expose
        private String namaKelas;
        @SerializedName("Tahun_ajaran")
        @Expose
        private String tahunAjaran;

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

    }

    public class Mengajar {

        @SerializedName("id_mk")
        @Expose
        private String idMk;
        @SerializedName("Nama_mk")
        @Expose
        private String namaMk;
        @SerializedName("Kode_mk")
        @Expose
        private String kodeMk;
        @SerializedName("id_dosen")
        @Expose
        private String idDosen;
        @SerializedName("id_kelas")
        @Expose
        private String idKelas;

        public String getIdMk() {
            return idMk;
        }

        public void setIdMk(String idMk) {
            this.idMk = idMk;
        }

        public String getNamaMk() {
            return namaMk;
        }

        public void setNamaMk(String namaMk) {
            this.namaMk = namaMk;
        }

        public String getKodeMk() {
            return kodeMk;
        }

        public void setKodeMk(String kodeMk) {
            this.kodeMk = kodeMk;
        }

        public String getIdDosen() {
            return idDosen;
        }

        public void setIdDosen(String idDosen) {
            this.idDosen = idDosen;
        }

        public String getIdKelas() {
            return idKelas;
        }

        public void setIdKelas(String idKelas) {
            this.idKelas = idKelas;
        }
    }


    public class Pertemuan {

        @SerializedName("Pertemuan")
        @Expose
        private String pertemuan;
        @SerializedName("Tanggal")
        @Expose
        private String tanggal;
        @SerializedName("Id_mk")
        @Expose
        private String idMk;
        @SerializedName("Absen")
        @Expose
        private List<Absen> absen;

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

        public List<Absen> getAbsen() {
            return absen;
        }

        public void setAbsen(List<Absen> absen) {
            this.absen = absen;
        }

    }

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
        @SerializedName("Id_mhs")
        @Expose
        private String idMhs;
        @SerializedName("NIM")
        @Expose
        private String nim;
        @SerializedName("Nama")
        @Expose
        private String nama;

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
}
