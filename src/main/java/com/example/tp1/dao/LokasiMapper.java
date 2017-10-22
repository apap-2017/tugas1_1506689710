package com.example.tp1.dao;

import com.example.tp1.model.KecamatanModel;
import com.example.tp1.model.KelurahanModel;
import com.example.tp1.model.KotaModel;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface LokasiMapper
{
    @Select("SELECT * FROM KOTA")
    @Results(value = {
            @Result(property = "kode_kota", column = "id")
    })
    List<KotaModel> selectAllKotaForm();

    @Select("SELECT * FROM KECAMATAN")
    @Results(value = {
            @Result(property = "kode_kecamatan", column = "id"),
            @Result(property = "kota", column = "id_kota",
                    javaType = KotaModel.class,
                    one = @One(select = "selectKotaFormID"))
    })
    List<KecamatanModel> selectAllKecamatanForm();

    @Select("SELECT * FROM KOTA WHERE id = #{id_kota}")
    @Results(value = {
            @Result(property = "kode_kota", column = "id")
    })
    KotaModel selectKotaFormID(@Param("id_kota") Long id_kota);

    @Select("SELECT * FROM KELURAHAN")
    @Results(value = {
            @Result(property = "kode_kelurahan", column = "id"),
            @Result(property = "kecamatan", column = "id_kecamatan",
                    javaType = KecamatanModel.class,
                    one = @One(select = "selectKecamatanFormID"))
    })
    List<KelurahanModel> selectAllKelurahanForm();

    @Select("SELECT * FROM KECAMATAN WHERE id = #{id_kecamatan}")
    @Results(value = {
            @Result(property = "kode_kecamatan", column = "id")
    })
    KecamatanModel selectKecamatanFormID(@Param("id_kecamatan") Long id_kecamatan);

    @Select("SELECT * FROM KOTA WHERE id = #{id_kota}")
    KotaModel selectKotaID(@Param("id_kota") String id_kota);

    @Select("SELECT * FROM KECAMATAN WHERE id = #{id_kecamatan}")
    @Results(value = {
            @Result(property = "kota", column = "id_kota",
                    javaType = KelurahanModel.class,
                    one = @One(select = "selectKotaID"))
    })
    KecamatanModel selectKecamatanID(@Param("id_kecamatan") String id_kecamatan);

    @Select("SELECT * FROM KELURAHAN WHERE id = #{id_kelurahan}")
    @Results(value = {
            @Result(property = "kecamatan", column = "id_kecamatan",
                    javaType = KelurahanModel.class,
                    one = @One(select = "selectKecamatanID"))
    })
    KelurahanModel selectKelurahanID(@Param("id_kelurahan") String id_kelurahan);

    @Select("SELECT id FROM KELURAHAN WHERE nama_kelurahan = #{nama_kelurahan}")
    String getIdKelurahan(@Param("nama_kelurahan") String nama_kelurahan);

    @Select("SELECT id FROM KECAMATAN WHERE nama_kecamatan = #{nama_kecamatan}")
    String getIdKecamatan(@Param("nama_kecamatan") String nama_kecamatan);

    @Select("SELECT id FROM KOTA WHERE nama_kota = #{nama_kota}")
    String getIdKota(@Param("nama_kota") String nama_kota);
}