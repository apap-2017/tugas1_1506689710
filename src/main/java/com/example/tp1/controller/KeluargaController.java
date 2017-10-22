package com.example.tp1.controller;

import com.example.tp1.model.KeluargaModel;
import com.example.tp1.model.PendudukModel;
import com.example.tp1.service.KeluargaService;
import com.example.tp1.service.LokasiService;
import com.example.tp1.service.PendudukService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
public class KeluargaController
{
    @Autowired
    KeluargaService keluargaDAO;

    @Autowired
    PendudukService pendudukDAO;

    @Autowired
    LokasiService lokasiDAO;

    @GetMapping("/keluarga")
    public String keluargaSubmit(Model model, @RequestParam(value = "nkk", required = true)String nkk)
    {
        if(nkk.equals(""))
        {
            return "index";
        }
        KeluargaModel keluarga = keluargaDAO.selectKeluarga(nkk);

        if (keluarga != null)
        {
            model.addAttribute("keluarga", keluarga);
            return "view-keluarga";
        }
        model.addAttribute("nkk", nkk);
        model.addAttribute("model", "keluarga");
        return "not-found";
    }

    @GetMapping("/keluarga/tambah")
    public String keluargaAdd(Model model)
    {
        model.addAttribute("keluarga", new KeluargaModel());
        model.addAttribute("listKota", lokasiDAO.selectAllKotaForm());
        model.addAttribute("listKecamatan", lokasiDAO.selectAllKecamatanForm());
        model.addAttribute("listKelurahan", lokasiDAO.selectAllKelurahanForm());

        return "keluarga-form";
    }

    @PostMapping("/keluarga/tambah")
    public String keluargaAddSubmit(@RequestParam("id_kelurahan") String id_kelurahan,
                                    @RequestParam("id_kecamatan") String id_kecamatan,
                                    @Valid KeluargaModel keluarga,
                                    BindingResult result,
                                    Model model)
    {
        if(result.hasErrors() || id_kelurahan.equals("") || id_kecamatan.equals(""))
        {
            model.addAttribute("keluarga", keluarga);
            model.addAttribute("listKota", lokasiDAO.selectAllKotaForm());
            model.addAttribute("listKecamatan", lokasiDAO.selectAllKecamatanForm());
            model.addAttribute("listKelurahan", lokasiDAO.selectAllKelurahanForm());

            return "keluarga-form";
        }
        keluargaDAO.addKeluarga(keluarga, id_kelurahan);

        model.addAttribute("perintah", "insert");
        model.addAttribute("nomor_kk", keluarga.getNomor_kk());
        model.addAttribute("model", "keluarga");
        return "success";
    }

    @GetMapping("/keluarga/ubah/{nkk}")
    public String keluargaUpdate(@PathVariable(value = "nkk", required = true) String nkk,
                                 Model model)
    {
        KeluargaModel keluarga = keluargaDAO.selectKeluarga(nkk);
        if(keluarga == null)
        {
            model.addAttribute("nkk", nkk);
            model.addAttribute("model", "keluarga");
            return "not-found";
        }

        model.addAttribute("keluarga", keluarga);

        model.addAttribute("listKota", lokasiDAO.selectAllKotaForm());
        model.addAttribute("listKecamatan", lokasiDAO.selectAllKecamatanForm());
        model.addAttribute("listKelurahan", lokasiDAO.selectAllKelurahanForm());

        model.addAttribute("id_kelurahan", lokasiDAO.getIdKelurahan(keluarga.getKelurahan().getNama_kelurahan()));
        model.addAttribute("id_kecamatan", lokasiDAO.getIdKecamatan(keluarga.getKelurahan().getKecamatan().getNama_kecamatan()));
        model.addAttribute("id_kota", lokasiDAO.getIdKota(keluarga.getKelurahan().getKecamatan().getKota().getNama_kota()));

        return "keluarga-form-update";
    }

    @PostMapping("/keluarga/ubah/{nkk}")
    public String keluargaUpdateSubmit(@RequestParam("id_kelurahan") String id_kelurahan,
                                       @RequestParam("id_kecamatan") String id_kecamatan,
                                       @Valid KeluargaModel keluarga,
                                       BindingResult result, Model model) throws Exception
    {
        if(result.hasErrors())
        {
            return new String("redirect:/keluarga/ubah/" + keluarga.getNomor_kk());
        }
        String oldNKK = keluarga.getNomor_kk();
        KeluargaModel oldKeluargaData = keluargaDAO.selectKeluarga(oldNKK);
        String oldIdKecamatan = lokasiDAO.getIdKecamatan(oldKeluargaData.getKelurahan().getKecamatan().getNama_kecamatan());
        keluargaDAO.updateKeluarga(id_kelurahan, keluarga, oldNKK);

        if(!id_kecamatan.equals(oldIdKecamatan))
        {
            ArrayList<PendudukModel> anggotaKeluarga = new ArrayList<PendudukModel>(oldKeluargaData.getAnggotaKeluarga());
            if (anggotaKeluarga.size() > 0)
            {
                String id_keluarga = pendudukDAO.selectIDKeluargaPenduduk(anggotaKeluarga.get(0).getNik());

                for (PendudukModel penduduk: anggotaKeluarga)
                {
                    pendudukDAO.updatePenduduk(id_keluarga, penduduk.getNik(), penduduk, new SimpleDateFormat("yyyy-MM-dd").format(penduduk.getTanggal_lahir()));
                }
            }
        }
        model.addAttribute("perintah", "update");
        model.addAttribute("nomor_kk", oldNKK);
        model.addAttribute("model", "keluarga");
        return "success";
    }
}