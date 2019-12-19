window.apiKey = ""

function addShare(share) {
    var sym=share.companySymbol;
  	$("#sharesTable tbody").append(
		"<tr id="+sym+"Row>" +
	        "<td class='always'>"+sym+"</td>" +
	        "<td id="+sym+"Name>"+share.companyName+"</td>" +
	        "<td>"+share.sharePrice.value + " "+share.sharePrice.currency+"</td>" +
	        "<td>"+share.numberOfSharesAvailable+"</td>" +
	        "<td>"+getDateTime(share.lastUpdate)+"</td>" +
	    "</tr>"  
	);
    $('body').on('click', "#"+sym+"Row", ()=>openShareModal(share));
}

function getDateTime(timestamp) {
  if (timestamp < 0) return "Just now";
  var date = new Date(timestamp*1000);  
  return date.toTimeString() + date.toDateString();
}

function search() {
console.log("search start")
	$.ajax({
	  url: origin+"/share/list/" + $("#numResultsField").val() +"?&st="+ $("#searchField").val() + "&key="+window.apiKey,
	  type : 'GET',
	  success: (data,textStatus,jqXHR)=> {$("#sharesTable tbody").empty();data.forEach(share => addShare(share));loadCurrencyCodes();}
	});
}

function onShareCreation(share,textStatus) {
    createAlert(share.companySymbol+" added");
    addShare(share);//adds to current view
    $( "#newShareForm" )[0].reset();
}


function loadCurrencyCodes() {
$.ajax({
	  url: origin+"/share/currencies/"+ "?&key="+window.apiKey,
	  type : 'GET',
	  success: (data,textStatus,jqXHR)=> {data.forEach(currency => {
	  	$("#currencyField").append(
	  		$('<option>', {
	  		    value: currency,
	  		    text: currency
	  	    })
	  	    );
	  	});}
	});
}

$( "#searchForm" ).submit(e=>{event.preventDefault();search();});
$("#numResultsField").change(e=>search());

//newShareForm
$( "#newShareForm" ).submit(function( event ) {
    event.preventDefault();
    var url = origin+"/share/"+ "?&key="+window.apiKey;
    
	var share = 
	    {	
	    	"sharePrice":
		        {
			        "currency":$("#currencyField").val(),
			        "value":parseFloat($("#valueField").val())
		        },
		    "companyName": $("#nameField").val(),
		    "companySymbol": $("#symbolField").val(),
		    "numberOfSharesAvailable":parseInt($("#availableField").val()),
		    "lastUpdate":-1
		};

	$.ajax(url, {
	    data : JSON.stringify(share),
	    contentType : 'application/json',
	    type : 'POST',
	    success: (data,textStatus,jqXHR)=> onShareCreation(share,textStatus),
	    error: (jqXHR, textStatus, errorThrown)=>createAlert("Failed to create share","#9e0000")
		});
});


