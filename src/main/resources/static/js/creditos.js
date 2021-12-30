$(document).ready(function() {
    //calculando el interes generado
    $('#customer').select2({
        theme: "bootstrap-5",
        dropdownParent: $("#customer").parent()
    });
    $('#interest_rate').on('blur', function(){
        let importe = parseFloat($('#importe').val());
        let utility = importe *(parseFloat($(this).val())/100);
        let total_pago = importe + utility;

        localStorage.setItem('utility', utility);
        localStorage.setItem('total', total_pago);
        $('#utility').text( utility);
        console.log( utility);
    })
    //calculando el pago segun el numero de cuotas
    $('#cantidad_cuotas').on('blur', function(){
     let pago=0;
     let total = parseFloat(localStorage.getItem('total'));
        pago = total/parseFloat($('#cantidad_cuotas').val());
     $("#pago").val(pago);
     $("#total").val(total);

    })
})