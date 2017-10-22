package com.example.tp1.service;

import com.example.tp1.dao.KeluargaMapper;
import com.example.tp1.dao.PendudukMapper;
import com.example.tp1.model.KeluargaModel;
import com.example.tp1.model.PendudukModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

@Slf4j
@Service
public class KeluargaServiceDatabase implements KeluargaService
{
    @Autowired
    KeluargaMapper keluargaDAO;

    @Override
    public KeluargaModel selectKeluarga(String nkk) {
        return keluargaDAO.selectKeluarga(nkk);
    }

    public String createNKK(String id_kelurahan) {
        String nkk = nkkTmp(id_kelurahan);
        return generateNKK(nkk);
    }

    public String updateNKK(String id_kelurahan, String oldNKK) {
        String nkk = nkkTmp(id_kelurahan);
        log.info("Old NKK: {}", oldNKK);
        if (oldNKK.substring(0, 12).equalsIgnoreCase(nkk))
        {
            return oldNKK;
        }
        return generateNKK(nkk);
    }

    public String nkkTmp(String id_kelurahan)
    {
        String kodePKK = keluargaDAO.selectKelurahan(id_kelurahan).getKecamatan().getKode_kecamatan().substring(0, 6);
        String waktu = new SimpleDateFormat("ddMMyy").format(new Date());
        return kodePKK + waktu;
    }

    public String generateNKK(String nkk)
    {
        String result = keluargaDAO.nkkMax(nkk + "%");
        if(result == null)
        {
            return nkk + "0001";
        }

        return (Long.parseLong(result) + 1) + "";
    }

    @Override
    public void addKeluarga(KeluargaModel keluarga, String id_kelurahan) {
        keluarga.setNomor_kk(createNKK(id_kelurahan));
        keluargaDAO.addKeluarga(id_kelurahan, keluarga);
    }

    @Override
    public void updateKeluarga(String id_kelurahan, KeluargaModel keluarga, String oldNKK) {
        keluarga.setNomor_kk(updateNKK(id_kelurahan, oldNKK));
        keluargaDAO.updateKeluarga(id_kelurahan, keluarga, oldNKK);
    }

    @Override
    public boolean updateBerlaku(String id) {
        ArrayList<PendudukModel> anggotaKeluarga = new ArrayList<PendudukModel>(keluargaDAO.selectAnggotaKeluarga(id));
        for (PendudukModel penduduk: anggotaKeluarga)
        {
            if(penduduk.getIs_wafat() == 0)
            {
                return false;
            }
        }
        keluargaDAO.updateBerlaku(id);
        return true;
    }
}
