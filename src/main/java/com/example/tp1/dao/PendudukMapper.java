package com.example.tp1.dao;

import com.example.tp1.model.*;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface PendudukMapper
{
    @Select("SELECT * FROM PENDUDUK WHERE nik = #{nik}")
    @Results(value = {
            @Result(property = "keluarga", column = "id_keluarga",
                    javaType = KeluargaModel.class,
                    one = @One(select = "selectKeluarga"))
    })
    PendudukModel selectPenduduk(@Param("nik") String nik);

    @Select("SELECT P.nik, P.nama, P.jenis_kelamin, P.tanggal_lahir FROM PENDUDUK P " +
            "LEFT JOIN KELUARGA K ON P.id_keluarga = K.id " +
            "LEFT JOIN KELURAHAN KEL ON K.id_kelurahan = KEL.id " +
            "WHERE KEL.id = #{id_kelurahan}")
    List<PendudukModel> selectPendudukKelurahan(@Param("id_kelurahan") String id_kelurahan);

    @Select("SELECT * FROM KELUARGA WHERE id = #{id_keluarga}")
    @Results(value = {
            @Result(property = "kelurahan", column = "id_kelurahan",
                    javaType = KelurahanModel.class,
                    one = @One(select = "selectKelurahan"))
    })
    KeluargaModel selectKeluarga(@Param("id_keluarga") String id_keluarga);

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

    @Select("SELECT id_keluarga FROM PENDUDUK WHERE nik = #{nik}")
    String selectIDKeluargaPenduduk(@Param("nik") String nik);

    @Insert("INSERT INTO PENDUDUK " +
            "(nik, nama, tempat_lahir, tanggal_lahir, jenis_kelamin, is_wni, id_keluarga, agama, pekerjaan, status_perkawinan, status_dalam_keluarga, golongan_darah, is_wafat) VALUE " +
            "(#{penduduk.nik}, #{penduduk.nama}, #{penduduk.tempat_lahir}, #{penduduk.tanggal_lahir}, #{penduduk.jenis_kelamin}, #{penduduk.is_wni}, #{id_keluarga}, #{penduduk.agama}, #{penduduk.pekerjaan}, #{penduduk.status_perkawinan}, #{penduduk.status_dalam_keluarga}, #{penduduk.golongan_darah}, #{penduduk.is_wafat})")
    void addPenduduk(@Param("id_keluarga") String id_keluarga, @Param("penduduk") PendudukModel penduduk);

    @Update("UPDATE PENDUDUK " +
            "SET nik = #{penduduk.nik}, nama = #{penduduk.nama}, tempat_lahir = #{penduduk.tempat_lahir}, " +
            "tanggal_lahir = #{penduduk.tanggal_lahir}, jenis_kelamin = #{penduduk.jenis_kelamin}, " +
            "is_wni = #{penduduk.is_wni}, id_keluarga = #{id_keluarga}, agama = #{penduduk.agama}, " +
            "pekerjaan = #{penduduk.pekerjaan}, status_perkawinan = #{penduduk.status_perkawinan}, " +
            "status_dalam_keluarga = #{penduduk.status_dalam_keluarga}, golongan_darah = #{penduduk.golongan_darah}, " +
            "is_wafat = #{penduduk.is_wafat} " +
            "WHERE nik = ${old_nik}")
    void updatePenduduk(@Param("id_keluarga") String id_keluarga, @Param("old_nik") String old_nik,@Param("penduduk") PendudukModel penduduk);

    @Select("SELECT MAX(nik) FROM PENDUDUK WHERE nik LIKE #{nik}")
    String nikMax(@Param("nik") String nik);

    @Update("UPDATE PENDUDUK " +
            "SET is_wafat = 1 " +
            "WHERE nik = #{nik}")
    void updateWafat(@Param("nik") String nik);
}
