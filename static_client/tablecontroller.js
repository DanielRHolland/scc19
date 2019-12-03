function search() {
    $.getJSON( origin+"/share/list/" + $("#numResultsField").val() +"?&st="+ $("#searchField").val() , function( json ) {
      $("#sharesTable tbody").empty();
      json.forEach(share => addShare(share));
     });
}

search();

$.getJSON( origin+"/share/currencies", function( json ) {
  json.forEach(currency => {
$("#currencyField").append($('<option>', {
    value: currency,
    text: currency
}));});});


$( "#searchForm" ).submit(e=>{event.preventDefault();search();});
$("#numResultsField").change(e=>search());

function onShareCreation(share,textStatus) {
    createAlert(share.companySymbol+" added");
    addShare(share);//adds to current view
    $( "#newShareForm" )[0].reset();
}

//newShareForm
$( "#newShareForm" ).submit(function( event ) {
    event.preventDefault();

    var sym = $("#symbolField").val(),
        name = $("#nameField").val(),
        currency = $("#currencyField").val(),
        price = $("#valueField").val(),
        available = $("#availableField").val(),
        url = origin+"/share/";
     
var share = 
    {"sharePrice":
        {"currency":currency,
        "value":parseFloat(price)},
    "companyName":name,
    "companySymbol":sym,
    "numberOfSharesAvailable":parseInt(available),
    "lastUpdate":-1
};
$.ajax(url, {
    data : JSON.stringify(share),
    contentType : 'application/json',
    type : 'POST',
    success: (data,textStatus,jqXHR)=> onShareCreation(share,textStatus)
});
});
