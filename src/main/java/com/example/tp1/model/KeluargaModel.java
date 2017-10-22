package com.example.tp1.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.Size;
import java.util.List;

@Data
@AllArgsConstructor
public class KeluargaModel
{
    private String nomor_kk;
    @NotEmpty
    private String alamat;
    @NotEmpty @Size(min = 3, max = 3)
    private String rt;
    @NotEmpty @Size(min = 3, max = 3)
    private String rw;
    private KelurahanModel kelurahan;
    private int is_tidak_berlaku;
    private List<PendudukModel> anggotaKeluarga;

    public KeluargaModel()
    {
        this.is_tidak_berlaku = 0;
    }

}
