function createCORSRequest(method, url) {
  var xhr = new XMLHttpRequest();
  if ("withCredentials" in xhr) {

    // Check if the XMLHttpRequest object has a "withCredentials" property.
    // "withCredentials" only exists on XMLHTTPRequest2 objects.
    xhr.open(method, url, true);
    xhr.withCredentials = false;

  } else if (typeof XDomainRequest != "undefined") {

    // Otherwise, check if XDomainRequest.
    // XDomainRequest only exists in IE, and is IE's way of making CORS requests.
    xhr = new XDomainRequest();
    xhr.open(method, url);

  } else {

    // Otherwise, CORS is not supported by the browser.
    xhr = null;

  }
  return xhr;
}


var xhr = createCORSRequest('GET', 'http://localhost:8080/share/list');
	console.log('CORS Created');
	if (!xhr) {
	  throw new Error('CORS not supported');
	}

   
   xhr.onload = function() {
    var responseText = xhr.responseText;
    console.log(responseText);
    document.getElementById("p1").innerHTML = responseText;

   };
   
   xhr.onerror = function() {
     console.log('There was an error!');
   };

   
   console.log('1')

   xhr.send();
