package com.app.myapplication.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Home {
    @SerializedName("profile")
    @Expose
    private Profile profile;
    @SerializedName("mengajar")
    @Expose
    private List<Mengajar> mengajar;

    public Profile getProfile() {
        return profile;
    }

    public void setProfile(Profile profile) {
        this.profile = profile;
    }

    public List<Mengajar> getMengajar() {
        return mengajar;
    }

    public void setMengajar(List<Mengajar> mengajar) {
        this.mengajar = mengajar;
    }

    public class Profile {

        @SerializedName("id_dosen")
        @Expose
        private String idDosen;
        @SerializedName("Username")
        @Expose
        private String username;
        @SerializedName("Password")
        @Expose
        private String password;
        @SerializedName("NIDN")
        @Expose
        private String nidn;
        @SerializedName("Nama_dosen")
        @Expose
        private String namaDosen;

        public String getIdDosen() {
            return idDosen;
        }

        public void setIdDosen(String idDosen) {
            this.idDosen = idDosen;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public String getNidn() {
            return nidn;
        }

        public void setNidn(String nidn) {
            this.nidn = nidn;
        }

        public String getNamaDosen() {
            return namaDosen;
        }

        public void setNamaDosen(String namaDosen) {
            this.namaDosen = namaDosen;
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
        @SerializedName("Nama_dosen")
        @Expose
        private String namaDosen;
        @SerializedName("id_kelas")
        @Expose
        private String idKelas;
        @SerializedName("nama_kelas")
        @Expose
        private String namaKelas;

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

        public String getNamaDosen() {
            return namaDosen;
        }

        public void setNamaDosen(String namaDosen) {
            this.namaDosen = namaDosen;
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

    }
}