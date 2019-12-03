function addShare(share) {
    var sym=share.companySymbol;
  $("#sharesTable tbody").append(
     /* "<tr>" +
        "<td>"+sym+"</td>" +
        "<td id="+sym+"Name>"+share.companyName+"</td>" +
        "<td>"+
        "<div id="+sym+"value>"+share.sharePrice.value+ "</div> " 
        +"<div id="+sym+"currency>" + share.sharePrice.currency+"</div></td>" +
        "<td id="+sym+"Name>"+share.numberOfSharesAvailable+"</td>" +
        "<td>"+getDateTime(share.lastUpdate)+"</td>" +
      "</tr>"*/
"<tr id="+sym+"Row>" +
        "<td>"+sym+"</td>" +
        "<td id="+sym+"Name>"+share.companyName+"</td>" +
        "<td>"+share.sharePrice.value + " "+share.sharePrice.currency+"</td>" +
        createQuantityCell(share) +
        "<td>"+getDateTime(share.lastUpdate)+"</td>" +
      "</tr>"  
);
    $('body').on('click', "#"+sym+"Row", ()=>openShareModal(share));
}

function openShareModal(share) {
$("#modal-inner").html(
'<form id="'+share.companySymbol+'ShareForm">'+
       '   <p>Symbol: '+share.companySymbol+'</p>'+
       '   <p>Name: '+share.companyName+'</p>'+
       '   <p>Price: '+share.sharePrice.value+' '+share.sharePrice.currency+'</p>'+
       '   <p>Shares Available: '+share.numberOfSharesAvailable+'</p>'+
        '</form>'
);
$("#myModal").css("display","block");
}


function getDateTime(timestamp) {
  if (timestamp < 0) return "Just now";
  var date = new Date(timestamp*1000);  
  return date.toTimeString() + date.toDateString();
}

function createQuantityCell(share) {
var id = share.companySymbol+"Quantity";
    return "<td id="+id+">"+share.numberOfSharesAvailable+"</td>";;
}

function createToggle(share){
var id = share.companySymbol+"Quantity";
    var on = false;
    return ()=> {
console.log(id);
on = !on;
if (on) {
$("#"+id).html( "<td id="+id+">"+share.numberOfSharesAvailable+"</td>");
} else {
$("#"+id).html( "<td id="+id+"><input id="+id+"Input>"+share.numberOfSharesAvailable+"</input></td>");
}
}
}
function createRemoveButton(companySymbol) {

}

function createEditButton(companySymbol) {

}


function createAlert(message, color="green") {
$("#alertPane").html(
  '  <div class="alert" style="background-color:'+color+';"> <span class="closebtn" onclick="this.parentElement.style.display='+"'none'"+';">&times;</span>'
 + message +
'</div> ');
}

