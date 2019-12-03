const origin = "http://localhost:8080"

function addShare(share) {
  $("#sharesTable tbody").append(
      "<tr>" +
        "<td>"+share.companySymbol+"</td>" +
        "<td>"+share.companyName+"</td>" +
        "<td>"+share.sharePrice.value+ " " +share.sharePrice.currency+"</td>" +
        "<td>"+share.numberOfSharesAvailable+"</td>" +
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
