package com.example.tp1.service;

import com.example.tp1.model.PendudukModel;

import java.util.ArrayList;
import java.util.List;

public interface PendudukService
{
    PendudukModel selectPenduduk(String nik);
    void addPenduduk(PendudukModel penduduk, String id_keluarga, String tggl_lahir) throws Exception;
    String selectIDKeluargaPenduduk(String nik);
    void updatePenduduk(String id_keluarga, String old_nik, PendudukModel penduduk, String tggl_lahir) throws Exception;
    void updateWafat(String nik);
    List<PendudukModel> selectPendudukKelurahan(String id_kelurahan);
    List<PendudukModel> palingTuaMuda(List<PendudukModel> listPenduduk);
}