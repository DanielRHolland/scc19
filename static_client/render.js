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
        "<td>"+share.numberOfSharesAvailable+"</td>" +
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

function openLoginModal() {
$("#login-modal-inner").html(
'<form id="loginForm">'+
       ' Username <input type="text" id="username">'+
       ' Password <input type="text" id="password">'+
      ' <input type="submit" value="Login">'+
'</form>'
);
$("#loginForm").submit(function( event ) {
    event.preventDefault();
    console.log($("#username").val()+$("#password").val());
    $("#loginModal").css("display","none");
});
$("#loginModal").css("display","block");
}

function getDateTime(timestamp) {
  if (timestamp < 0) return "Just now";
  var date = new Date(timestamp*1000);  
  return date.toTimeString() + date.toDateString();
}

function createAlert(message, color="green") {
$("#alertPane").html(
  '  <div class="alert" style="background-color:'+color+';"> <span class="closebtn" onclick="this.parentElement.style.display='+"'none'"+';">&times;</span>'
 + message +
'</div> ');
}

