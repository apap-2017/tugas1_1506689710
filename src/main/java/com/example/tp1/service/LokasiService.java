package com.example.tp1.service;

import com.example.tp1.model.KecamatanModel;
import com.example.tp1.model.KelurahanModel;
import com.example.tp1.model.KotaModel;

import java.util.List;

public interface LokasiService
{
    List<KotaModel> selectAllKotaForm();
    List<KecamatanModel> selectAllKecamatanForm();
    List<KelurahanModel> selectAllKelurahanForm();
    KotaModel selectKotaID(String id_kota);
    KecamatanModel seleckKecamatanID(String id_kecamatan);
    KelurahanModel selectKelurahanID(String id_kelurahan);
    String getIdKelurahan(String nama_kelurahan);
    String getIdKecamatan(String nama_kecamatan);
    String getIdKota(String nama_kota);
}
