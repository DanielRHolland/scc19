function addShare(share) {
  $("#sharesTable tbody").append(
      "<tr>" +
        "<td>"+share.companySymbol+"</td>" +
        "<td>"+share.companyName+"</td>" +
        "<td>"+share.sharePrice.currency+share.sharePrice.value+"</td>" +
        "<td>"+share.numberOfSharesAvailable+"</td>" +
        "<td>"+getDateTime(share.lastUpdate)+"</td>" +
      "</tr>"
  );
}


function getDateTime(timestamp) {
  var date = new Date(timestamp*1000);  
  return date.toTimeString() + date.toDateString();
}



