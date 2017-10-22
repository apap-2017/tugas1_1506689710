package com.example.tp1.service;

import com.example.tp1.dao.LokasiMapper;
import com.example.tp1.model.KecamatanModel;
import com.example.tp1.model.KelurahanModel;
import com.example.tp1.model.KotaModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LokasiServiceDatabase implements LokasiService
{
    @Autowired
    LokasiMapper lokasiDAO;

    @Override
    public List<KotaModel> selectAllKotaForm() {
        return lokasiDAO.selectAllKotaForm();
    }

    @Override
    public List<KecamatanModel> selectAllKecamatanForm() {
        return lokasiDAO.selectAllKecamatanForm();
    }

    @Override
    public List<KelurahanModel> selectAllKelurahanForm() {
        return lokasiDAO.selectAllKelurahanForm();
    }

    @Override
    public KelurahanModel selectKelurahanID(String id_kelurahan) {
        return lokasiDAO.selectKelurahanID(id_kelurahan);
    }

    @Override
    public KecamatanModel seleckKecamatanID(String id_kecamatan) {
        return lokasiDAO.selectKecamatanID(id_kecamatan);
    }

    @Override
    public KotaModel selectKotaID(String id_kota) {
        return lokasiDAO.selectKotaID(id_kota);
    }

    @Override
    public String getIdKelurahan(String nama_kelurahan) {
        return lokasiDAO.getIdKelurahan(nama_kelurahan);
    }

    @Override
    public String getIdKecamatan(String nama_kecamatan) {
        return lokasiDAO.getIdKecamatan(nama_kecamatan);
    }

    @Override
    public String getIdKota(String nama_kota) {
        return lokasiDAO.getIdKota(nama_kota);
    }
}
