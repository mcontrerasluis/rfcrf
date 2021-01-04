var FIEL_SAT_HOST="https://aplicaciones.sat.gob.mx/PTSC/fwidget/";
var log_fiel="[widget-fiel]";
var bibliotecasFirmado=["resources/js/fiel/jquery/pop-up-jquery.js","resources/masivo/js/fiel/base64x-1.1.js","resources/masivo/js/fiel/yahoo-min.js","resources/masivo/js/fiel/core.js","resources/masivo/js/fiel/cipher-core.js","resources/masivo/js/fiel/enc-base64.js","resources/masivo/js/fiel/jsbn.js","resources/masivo/js/fiel/jsbn2.js","resources/masivo/js/fiel/base64.js","resources/masivo/js/fiel/base64_1.js","resources/masivo/js/fiel/asn1-1.0.min.js","resources/masivo/js/fiel/asn1hex-1.1.js","resources/masivo/js/fiel/asn1.js","resources/masivo/js/fiel/rsa.js","resources/masivo/js/fiel/rsa2.js","resources/masivo/js/fiel/sha256.js","resources/masivo/js/fiel/x509-1.1.min.js","resources/masivo/js/fiel/crypto-1.1.js","resources/masivo/js/fiel/rsasign-1.2.js","resources/masivo/js/fiel/sjcl.js","resources/masivo/js/fiel/sha1.js","resources/masivo/js/fiel/tripledes.js","resources/masivo/js/fiel/utilerias.js","resources/masivo/js/fiel/llave.privada.min.1.1.js","resources/masivo/js/fiel/validador.controller.js","resources/masivo/js/fiel/validador.fiel.js","resources/masivo/js/fiel/FielUtil-min.1.1.js","resources/masivo/js/fiel/signed.workerd.general.js","resources/masivo/js/fiel/firma.main.general.js"];
function cargaSiguienteScript(a){cargarScript(FIEL_SAT_HOST+bibliotecasFirmado[a],function(){if(a<bibliotecasFirmado.length-1){cargaSiguienteScript(a+1)
}})
}function cargarScript(a,c){var b=document.createElement("script");
b.setAttribute("type","text/javascript");
b.setAttribute("src",a);
if(b.readyState){b.onreadystatechange=function(){if(this.readyState=="complete"||this.readyState=="loaded"){c()
}}
}else{b.onload=c
}(document.getElementsByTagName("head")[0]||document.documentElement).appendChild(b)
}(function(){var g="resources/js/fiel/jquery/jquery-1.6.4.min.js";
var b="1.6.4";
var d="resources/css/style.css";
if(!window.console){console={log:function(){}}
}console.log(log_fiel+" Cargando el widget desde: "+FIEL_SAT_HOST);
c();
function c(){cargaSiguienteScript(0)
}if(window.jQuery===undefined||window.jQuery.fn.jquery!==b){var f=document.createElement("script");
f.setAttribute("type","text/javascript");
f.setAttribute("src",FIEL_SAT_HOST+g);
if(f.readyState){f.onreadystatechange=function(){if(this.readyState=="complete"||this.readyState=="loaded"){e()
}}
}else{f.onload=e
}(document.getElementsByTagName("head")[0]||document.documentElement).appendChild(f)
}else{console.log(log_fiel+" Utilizamos versión jquery existente");
window._jQueryFirmaCentral=window.jQuery;
a()
}function e(){if(window.jQuery!==undefined){window._jQueryFirmaCentral=window.jQuery.noConflict(true);
console.log(log_fiel+" Utilizando version jquery propia")
}a()
}function a(){_jQueryFirmaCentral(document).ready(function(i){var h=i("<link></link>",{rel:"stylesheet",type:"text/css",href:FIEL_SAT_HOST+d});
h.appendTo("head");
i.ajax({url:FIEL_SAT_HOST+"restServices/fwidgetGobMx2.json",jsonp:"callback",dataType:"jsonp",success:function(j){i("#firmado-widget-container").html(j.html)
}})
})
}})();