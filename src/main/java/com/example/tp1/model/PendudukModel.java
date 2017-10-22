package com.example.tp1.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
@AllArgsConstructor
public class PendudukModel
{
    private String nik;
    @NotEmpty
    private String nama;
    @NotEmpty
    private String tempat_lahir;
    private Date tanggal_lahir;
    private int jenis_kelamin;
    @NotEmpty
    private String agama;
    private KeluargaModel keluarga;
    @NotNull @NotEmpty
    private String pekerjaan;
    private String golongan_darah;
    private String status_perkawinan;
    private String status_dalam_keluarga;
    private int is_wni;
    private int is_wafat;

    public PendudukModel()
    {
        this.is_wni = 1;
        this.is_wafat = 0;
    }
}