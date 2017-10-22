package com.example.tp1.service;

import com.example.tp1.dao.PendudukMapper;
import com.example.tp1.model.KeluargaModel;
import com.example.tp1.model.PendudukModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Slf4j
@Service
public class PendudukServiceDatabase implements PendudukService
{
    @Autowired
    PendudukMapper pendudukDAO;

    @Override
    public PendudukModel selectPenduduk(String nik)
    {
        return pendudukDAO.selectPenduduk(nik);
    }

    public String createNIK(PendudukModel penduduk, String id_keluarga) {
        String nik = nikTmp(penduduk, id_keluarga);
        return generateNIK(nik);
    }

    public String updateNIK(PendudukModel penduduk, String id_keluarga) {
        String nik = nikTmp(penduduk, id_keluarga);

        if (penduduk.getNik().substring(0, 12).equalsIgnoreCase(nik))
        {
            return penduduk.getNik();
        }
        return generateNIK(nik);
    }

    public String nikTmp(PendudukModel penduduk, String id_keluarga)
    {
        KeluargaModel keluarga = pendudukDAO.selectKeluarga(id_keluarga);

        String kodePKK = keluarga.getKelurahan().getKecamatan().getKode_kecamatan().substring(0,6);
        String kodeTgglLahir = new SimpleDateFormat("ddMMyy").format(penduduk.getTanggal_lahir());
        if(penduduk.getJenis_kelamin() == 1)
        {
            kodeTgglLahir = "" + (Integer.parseInt(kodeTgglLahir.substring(0, 1)) + 4) + kodeTgglLahir.substring(1);
        }
        return kodePKK + kodeTgglLahir;
    }

    public String generateNIK(String nik)
    {
        String result = pendudukDAO.nikMax(nik + "%");
        if(result == null)
        {
            return nik + "0001";
        }
        return (Long.parseLong(result) + 1) + "";
    }

    @Override
    public void addPenduduk(PendudukModel penduduk, String id_keluarga, String tggl_lahir) throws Exception {
        penduduk.setTanggal_lahir(setTanggal(tggl_lahir));
        penduduk.setNik(createNIK(penduduk, id_keluarga));
        pendudukDAO.addPenduduk(id_keluarga, penduduk);
    }

    @Override
    public void updatePenduduk(String id_keluarga, String old_nik, PendudukModel penduduk, String tggl_lahir) throws Exception {
        penduduk.setTanggal_lahir(setTanggal(tggl_lahir));
        penduduk.setNik(updateNIK(penduduk, id_keluarga));
        pendudukDAO.updatePenduduk(id_keluarga, old_nik, penduduk);
    }

    public Date setTanggal(String tanggal) throws Exception
    {
        return new SimpleDateFormat("yyyy-MM-dd").parse(tanggal);
    }

    @Override
    public String selectIDKeluargaPenduduk(String nik) {
        return pendudukDAO.selectIDKeluargaPenduduk(nik);
    }

    @Override
    public void updateWafat(String nik) {
        pendudukDAO.updateWafat(nik);
    }

    @Override
    public List<PendudukModel> selectPendudukKelurahan(String id_kelurahan) {
        return pendudukDAO.selectPendudukKelurahan(id_kelurahan);
    }

    @Override
    public List<PendudukModel> palingTuaMuda(List<PendudukModel> listPenduduk) {
        List<PendudukModel> result = new ArrayList<PendudukModel>();
        result.add(0, listPenduduk.get(0));
        result.add(1, listPenduduk.get(0));
        for (PendudukModel penduduk: listPenduduk)
        {
            Date tanggal_lahir = penduduk.getTanggal_lahir();
            if(tanggal_lahir.after(result.get(0).getTanggal_lahir()))
            {
                result.set(0, penduduk);
            } else if(tanggal_lahir.before(result.get(1).getTanggal_lahir()))
            {
                result.set(1, penduduk);
            }
        }
        log.info("termuda {} tertua {}", result.get(0).getTanggal_lahir(), result.get(1).getTanggal_lahir());
        return result;

    }
}
