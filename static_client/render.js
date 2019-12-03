const origin = "http://localhost:8080"

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
"<tr>" +
        "<td>"+sym+"</td>" +
        "<td id="+sym+"Name>"+share.companyName+"</td>" +
        "<td>"+share.sharePrice.value + " "+share.sharePrice.currency+"</td>" +
        "<td id="+sym+"Name>"+share.numberOfSharesAvailable+"</td>" +
        "<td>"+getDateTime(share.lastUpdate)+"</td>" +
      "</tr>"  
);
}

function getDateTime(timestamp) {
  if (timestamp < 0) return "Just now";
  var date = new Date(timestamp*1000);  
  return date.toTimeString() + date.toDateString();
}


function createRemoveButton(companySymbol) {

}

function createEditButton(companySymbol) {

}


function createAlert(message) {
$("alertPane").html(
  '  <div class="alert"> <span class="closebtn" onclick="this.parentElement.style.display=\'none\';">&times;</span>'
 + message +
'</div> ');
}

