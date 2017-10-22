package com.example.tp1.service;

import com.example.tp1.model.KeluargaModel;
import com.example.tp1.model.PendudukModel;

public interface KeluargaService
{
    KeluargaModel selectKeluarga(String nkk);
    void addKeluarga(KeluargaModel keluarga, String id_kelurahan);
    void updateKeluarga(String id_kelurahan, KeluargaModel keluarga, String oldNKK);
    boolean updateBerlaku(String id);
}
