package com.example.tp1.model;

import lombok.Data;

import java.util.Date;

@Data
public class KelurahanModel
{
    private String kode_kelurahan;
    private KecamatanModel kecamatan;
    private String nama_kelurahan;
    private String kode_pos;
}