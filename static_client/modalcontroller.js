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

$("#closeModal").click(()=> $("#myModal").css("display","none"));

function getApiKey(username, password) {
	var hash = hashPassword(password);
	var url = origin + "/user/login/?id=" + username +"&hash="+hash;
     $.ajax(url, {
     	    type : 'GET',
     	    success: (data,textStatus,jqXHR)=> {
     	    $("#loginModal").css("display","none");
     	    window.apiKey = data.key;
     	    	    search();},
     	  //  error: (jqXHR, textStatus, errorThrown)=>createAlert("Failed to create share","#9e0000")
     		});
}

function hashPassword(password) {return password;}//todo implement

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
	    getApiKey($("#username").val(),$("#password").val());
	});
	$("#loginModal").css("display","block");
}

function createAlert(message, color="green") {
	$("#alertPane").html(
	  '  <div class="alert" style="background-color:'+color+
	  ';"> <span class="closebtn" onclick="this.parentElement.style.display='+
	  "'none'"+';">&times;</span>' + message + '</div> ');
}

/*window.onclick = function(event) {
  if (event.target == modal) {
    modal.style.display = "none";
  }
} */
