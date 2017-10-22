$(document).ready(function () {
    $('#kecamatan option, #kelurahan option').hide();
    var id = $('#kota').children("option:selected").attr('id');
    $("#kecamatan option[id='" + id + "']").show();
    var id = $('#kecamatan').children("option:selected").attr('value');
    $("#kelurahan option[id='" + id + "']").show();

    $('#kota').on('change', function(){
        $('#kecamatan').val($('#kecamatan option:first').val());
        $('#kelurahan').val($('#kelurahan option:first').val());
        $('#kecamatan option, #kelurahan option').hide();
        var id = $(this).children("option:selected").attr('id');
        $("#kecamatan option[id='" + id + "']").show();
    });
    $('#kecamatan').on('change', function(){
        $('#kelurahan').val($('#kelurahan option:first').val());
        $('#kelurahan option').hide();
        var id = $(this).children("option:selected").attr('value');
        $("#kelurahan option[id='" + id + "']").show();
    });

    //  src: https://jsfiddle.net/et55L2p2/

})