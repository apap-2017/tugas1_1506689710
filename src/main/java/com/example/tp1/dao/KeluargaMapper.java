package com.example.tp1.dao;

import com.example.tp1.model.*;
import org.apache.ibatis.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Mapper
public interface KeluargaMapper
{
    @Select("SELECT * FROM KELUARGA WHERE nomor_kk = #{nomor_kk}")
    @Results(value = {
            @Result(property = "anggotaKeluarga", column = "id",
                    javaType = List.class,
                    many = @Many(select = "selectAnggotaKeluarga")),
            @Result(property = "kelurahan", column = "id_kelurahan",
                    javaType = KelurahanModel.class,
                    one = @One(select = "selectKelurahan"))
    })
    KeluargaModel selectKeluarga(@Param("nomor_kk") String nomor_kk);

    @Insert("INSERT INTO KELUARGA " +
            "(nomor_kk, alamat, rt, rw, id_kelurahan, is_tidak_berlaku) VALUE " +
            "(#{keluarga.nomor_kk}, #{keluarga.alamat}, #{keluarga.rt}, #{keluarga.rw}, #{id_kelurahan}, #{keluarga.is_tidak_berlaku})")
    void addKeluarga(@Param("id_kelurahan") String id_kelurahan, @Param("keluarga") KeluargaModel keluarga);

    @Update("UPDATE KELUARGA " +
            "SET nomor_kk = #{keluarga.nomor_kk}, alamat = #{keluarga.alamat}, rt = #{keluarga.rt}, rw = #{keluarga.rw}, id_kelurahan = #{id_kelurahan} " +
            "WHERE nomor_kk = #{oldNKK}")
    void updateKeluarga(@Param("id_kelurahan") String id_kelurahan, @Param("keluarga") KeluargaModel keluarga, @Param("oldNKK") String oldNKK);

    @Update("UPDATE KELUARGA SET is_tidak_berlaku = 1 WHERE id = #{id}")
    void updateBerlaku(@Param("id") String id);

    @Select("SELECT MAX(nomor_kk) FROM KELUARGA WHERE nomor_kk LIKE #{nomor_kk}")
    String nkkMax(@Param("nomor_kk") String nomor_kk);

    @Select("SELECT * FROM KELURAHAN WHERE id = #{id_kelurahan}")
    @Results(value = {
            @Result(property = "kecamatan", column = "id_kecamatan",
                    javaType = KelurahanModel.class,
                    one = @One(select = "selectKecamatan"))
    })
    KelurahanModel selectKelurahan(@Param("id_kelurahan") String id_kelurahan);

    @Select("SELECT * FROM KECAMATAN WHERE id = #{id_kecamatan}")
    @Results(value = {
            @Result(property = "kota", column = "id_kota",
                    javaType = KelurahanModel.class,
                    one = @One(select = "selectKota"))
    })
    KecamatanModel selectKecamatan(@Param("id_kecamatan") String id_kecamatan);

    @Select("SELECT * FROM KOTA WHERE id = #{id_kota}")
    KotaModel selectKota(@Param("id_kota") String id_kota);

    @Select("SELECT * FROM PENDUDUK WHERE id_keluarga = #{id_keluarga}")
    List<PendudukModel> selectAnggotaKeluarga(@Param("id_keluarga") String id_keluarga);
}