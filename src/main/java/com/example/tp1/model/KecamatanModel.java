package com.example.tp1.model;

import lombok.Data;

import java.util.List;

@Data
public class KecamatanModel
{
    private String kode_kecamatan;
    private String nama_kecamatan;
    private KotaModel kota;
}