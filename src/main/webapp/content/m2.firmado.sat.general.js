var FIEL_SAT_HOST = "https://wwwuat.siat.sat.gob.mx/PTSC/fwidget/";
var log_fiel = "[widget-fiel]";
var bibliotecasFirmado = [
                "resources/js/fiel/jquery/pop-up-jquery.js", 
                "resources/masivo/js/fiel/base64x-1.1.js", 
                "resources/masivo/js/fiel/yahoo-min.js", 
                "resources/masivo/js/fiel/core.js", 
                "resources/masivo/js/fiel/cipher-core.js", 
                "resources/masivo/js/fiel/enc-base64.js", 
                "resources/masivo/js/fiel/jsbn.js", 
                "resources/masivo/js/fiel/jsbn2.js", 
                "resources/masivo/js/fiel/base64.js", 
                "resources/masivo/js/fiel/base64_1.js", 
                "resources/masivo/js/fiel/asn1-1.0.min.js", 
                "resources/masivo/js/fiel/asn1hex-1.1.js", 
                "resources/masivo/js/fiel/asn1.js", 
                "resources/masivo/js/fiel/rsa.js", 
                "resources/masivo/js/fiel/rsa2.js", 
                "resources/masivo/js/fiel/sha256.js", 
                "resources/masivo/js/fiel/x509-1.1.min.js", 
                "resources/masivo/js/fiel/crypto-1.1.js", 
                "resources/masivo/js/fiel/rsasign-1.2.js", 
                "resources/masivo/js/fiel/sjcl.js", 
                "resources/masivo/js/fiel/sha1.js", 
                "resources/masivo/js/fiel/tripledes.js", 
                "resources/masivo/js/fiel/utilerias.js", 
                "resources/masivo/js/fiel/llave.privada.min.1.1.js", 
                "resources/masivo/js/fiel/validador.controller.js", 
                "resources/masivo/js/fiel/validador.fiel.js", 
                "resources/masivo/js/fiel/FielUtil-min.1.1.js",                 
                "resources/masivo/js/fiel/signed.workerd.general.js", 
                "resources/masivo/js/fiel/firma.main.general.js"
                ];

function cargaSiguienteScript(i) {
    cargarScript(FIEL_SAT_HOST + bibliotecasFirmado[i], function () {
        if (i < bibliotecasFirmado.length - 1) {
            cargaSiguienteScript(i + 1);
        }
    });
}

function cargarScript(url_js, callback) {
    var script_tag_librerias = document.createElement('script');
    script_tag_librerias.setAttribute("type", "text/javascript");
    script_tag_librerias.setAttribute("src", url_js);
    if (script_tag_librerias.readyState) {
        script_tag_librerias.onreadystatechange = function () {
            if (this.readyState == 'complete' || this.readyState == 'loaded') {
                callback();
            }
        };
    }
    else {
        script_tag_librerias.onload = callback;
    }
    (document.getElementsByTagName("head")[0] || document.documentElement).appendChild(script_tag_librerias);
}
(function () {
    var JQUERY_PATH = "resources/js/fiel/jquery/jquery-1.6.4.min.js";
    var JQUERY_VERSION = '1.6.4';
    var CSS_PATH = "resources/css/style.css";
    //Verificamos si console existe (navegadores antiguos)
    if (!window.console) {
        //Se define objeto dummy
        console = {
            log: function() {
            }
        };
    }

    console.log(log_fiel + ' Cargando el widget desde: ' + FIEL_SAT_HOST);
    loadScripts();

    function loadScripts() {
        cargaSiguienteScript(0);
    }

    /******** Load jQuery if not present *********/    
    if (window.jQuery === undefined || window.jQuery.fn.jquery !== JQUERY_VERSION) {
        var script_tag = document.createElement('script');
        script_tag.setAttribute("type", "text/javascript");
        script_tag.setAttribute("src", FIEL_SAT_HOST + JQUERY_PATH);
        if (script_tag.readyState) {
            script_tag.onreadystatechange = function () {
                // For old versions of IE
                if (this.readyState == 'complete' || this.readyState == 'loaded') {
                    scriptLoadHandler();
                }
            };
        }
        else {
            script_tag.onload = scriptLoadHandler;
        }
        (document.getElementsByTagName("head")[0] || document.documentElement).appendChild(script_tag);
    }
    else {
        // The jQuery version on the window is the one we want to use
        console.log(log_fiel + ' Utilizamos versión jquery existente');
        window._jQueryFirmaCentral = window.jQuery;
        main();
    }

    /******** Called once jQuery has loaded ******/
    function scriptLoadHandler() {

        //Creamos una variable global con nuestra versión de jquery
        if (window.jQuery !== undefined) {

            // Restore $ and window.jQuery to their previous values and store the
            //Quitamos la referencia al jquery actual
            window._jQueryFirmaCentral = window.jQuery.noConflict(true);
            console.log(log_fiel + ' Utilizando version jquery propia');
        }

        // Call our main function
        main();
    }   
   

    /******** Our main function ********/
    function main() {
        _jQueryFirmaCentral(document).ready(function ($) {
            /******* Load CSS *******/
            var css_link = $("<link></link>", 
            {
                rel : "stylesheet", type : "text/css", href : FIEL_SAT_HOST + CSS_PATH
            });
            css_link.appendTo('head');
            
            /******* Load HTML *******/
            $.ajax( {
                url : FIEL_SAT_HOST + "restServices/fwidgetGobMx2.json", jsonp : "callback", dataType : "jsonp", success : function (response) {
                    $('#firmado-widget-container').html(response.html);
                }
            });
        });
    }
})();
