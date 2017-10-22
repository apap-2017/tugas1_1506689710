package com.example.tp1.controller;

import com.example.tp1.model.*;
import com.example.tp1.service.KeluargaService;
import com.example.tp1.service.LokasiService;
import com.example.tp1.service.PendudukService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
public class PendudukController
{
    @Autowired
    PendudukService pendudukDAO;

    @Autowired
    KeluargaService keluargaDAO;

    @Autowired
    LokasiService lokasiDAO;

    @RequestMapping("/")
    public String index()
    {
        return "index";
    }

    @GetMapping("/penduduk")
    public String pendudukSubmit(Model model, @RequestParam(value = "nik", required = true)String nik)
    {
        if(nik.equals(""))
        {
            return "index";
        }
        PendudukModel penduduk = pendudukDAO.selectPenduduk(nik);

        if (penduduk != null)
        {
            model.addAttribute("penduduk", penduduk);
            return "view-penduduk";
        }
        model.addAttribute("nik", nik);
        model.addAttribute("model", "penduduk");
        return "not-found";
    }

    @PostMapping("/penduduk/mati")
    public String updateWafat(@RequestParam(value = "nik", required = true) String nik,
                              RedirectAttributes redirectAttributes)
    {
        pendudukDAO.updateWafat(nik);
        keluargaDAO.updateBerlaku(pendudukDAO.selectIDKeluargaPenduduk(nik));
        redirectAttributes.addFlashAttribute("wafat", "success");
        return "redirect:/penduduk?nik=" + nik;
    }

    @GetMapping("/penduduk/tambah")
    public String pendudukAdd(Model model)
    {
        model.addAttribute("penduduk", new PendudukModel());
        return "penduduk-form";
    }

    @PostMapping("/penduduk/tambah")
    public String pendudukAddSubmit(@RequestParam(value = "tggl_lahir", required = true) String tggl_lahir,
                                    @RequestParam(value = "id_keluarga", required = true) String id_keluarga,
                                    @Valid PendudukModel penduduk,
                                    BindingResult result,
                                    Model model) throws Exception
    {
        if(result.hasErrors() || tggl_lahir.equals("") || id_keluarga.equals(""))
        {
            model.addAttribute("penduduk", penduduk);
            return "penduduk-form";
        }
        pendudukDAO.addPenduduk(penduduk, id_keluarga, tggl_lahir);
        model.addAttribute("nik", penduduk.getNik());
        model.addAttribute("perintah", "insert");
        model.addAttribute("model", "penduduk");
        return "success";
    }

    @GetMapping("/penduduk/ubah/{nik}")
    public String pendudukUpdate(@PathVariable(value = "nik", required = true) String nik,
                                 Model model)
    {
        PendudukModel penduduk = pendudukDAO.selectPenduduk(nik);

        if(penduduk == null)
        {
            model.addAttribute("nik", nik);
            model.addAttribute("model", "penduduk");
            return "not-found";
        }

        if(penduduk.getGolongan_darah().charAt(penduduk.getGolongan_darah().length() - 1) == '−')
        {
            penduduk.setGolongan_darah(penduduk.getGolongan_darah().substring(0, penduduk.getGolongan_darah().length() - 1) + "-");
        }
        model.addAttribute("penduduk", penduduk);

        String tggl_lahir = new SimpleDateFormat("yyyy-MM-dd").format(penduduk.getTanggal_lahir());
        model.addAttribute("tggl_lahir", tggl_lahir);
        model.addAttribute("id_keluarga", pendudukDAO.selectIDKeluargaPenduduk(penduduk.getNik()));
        return "penduduk-form-update";
    }

    @PostMapping("/penduduk/ubah/{nik}")
    public String pendudukUpdateSubmit(@RequestParam(value = "tggl_lahir", required = true) String tggl_lahir,
                                       @RequestParam(value = "id_keluarga", required = true) String id_keluarga,
                                       @Valid PendudukModel penduduk,
                                       BindingResult result,
                                       Model model) throws Exception
    {
        if(result.hasErrors() || tggl_lahir.equals("") || id_keluarga.equals(""))
        {
            return new String("redirect:/penduduk/ubah/" + penduduk.getNik());
        }
        String old_nik = penduduk.getNik();
        pendudukDAO.updatePenduduk(id_keluarga, old_nik, penduduk, tggl_lahir);

        model.addAttribute("nik", old_nik);
        model.addAttribute("perintah", "update");
        model.addAttribute("model", "penduduk");
        return "success";
    }

    @GetMapping("/penduduk/cari")
    public String cariPendudukKotaKecamatan(@RequestParam(value = "kt", required = false) String id_kota,
                                            @RequestParam(value = "kc", required = false) String id_kecamatan,
                                            @RequestParam(value = "kl", required = false) String id_kelurahan,
                                            Model model)
    {
        if(id_kota == null || id_kota.equals(""))
        {
            model.addAttribute("listKota", lokasiDAO.selectAllKotaForm());
            return "penduduk-search-form";
        }

        KotaModel kota = lokasiDAO.selectKotaID(id_kota);

        if(kota != null)
        {
            model.addAttribute("kota", kota);

            if(id_kecamatan == null || id_kecamatan.equals(""))
            {
                model.addAttribute("id_kota", id_kota);
                model.addAttribute("listKecamatan", lokasiDAO.selectAllKecamatanForm());
                return "penduduk-search-kecamatan-form";
            }

            KecamatanModel kecamatan = lokasiDAO.seleckKecamatanID(id_kecamatan);

            if(kecamatan != null && kecamatan.getKota().getNama_kota().equals(kota.getNama_kota()))
            {
                model.addAttribute("kecamatan", kecamatan);

                if(id_kelurahan == null || id_kelurahan.equals(""))
                {
                    model.addAttribute("id_kota", id_kota);
                    model.addAttribute("id_kecamatan", id_kecamatan);
                    model.addAttribute("listKelurahan", lokasiDAO.selectAllKelurahanForm());
                    return "penduduk-search-kelurahan-form";
                }
            } else
                {
                    model.addAttribute("model", "lokasi");
                    return "not-found";
                }

            KelurahanModel kelurahan = lokasiDAO.selectKelurahanID(id_kelurahan);

            if(kelurahan !=null && kelurahan.getKecamatan().getNama_kecamatan().equals(kecamatan.getNama_kecamatan()))
            {
                List<PendudukModel> listPenduduk = pendudukDAO.selectPendudukKelurahan(id_kelurahan);
                model.addAttribute("kelurahan", kelurahan);
                model.addAttribute("listPenduduk", listPenduduk);
                List<PendudukModel> listTuaMuda = pendudukDAO.palingTuaMuda(listPenduduk);

                model.addAttribute("listTuaMuda", listTuaMuda);
                return "view-penduduk-kelurahan";
            } else
                {
                    model.addAttribute("model", "lokasi");
                    return "not-found";
                }
        }
        model.addAttribute("model", "lokasi");
        return "not-found";
    }
}