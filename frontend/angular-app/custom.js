var defaultLang = 'ru';

var backendServerAddr = "/backend";
var backendTimeout = 5000;

var mailRegexp = /^([0-9a-zA-Z]([-.\w]*[0-9a-zA-Z])*@([0-9a-zA-Z][-\w]*[0-9a-zA-Z]\.)+[a-zA-Z]{2,9})$/;
var userPhoneRegexp = /^([0-9]){10}$/;
var passwordRegexp = /^[a-zA-Z0-9!@^*-.,=]{6,16}$/;
var numberRegexp = /^[0-9]+$/;
var numberWithDotRegexp = /^[0-9.]+$/;

var phoneMaskTemplate = "+7 (999) 999-99-99";

function httpErrors(url, status){
    switch(status){
        case 401:
            redirect403(url);
            break;
        case 403:
            redirect403(url);
            break;
        case 404:
            redirect404();
            break;
        case 0:
            break;
        case -1:
			redirectBackendError();
            break;
        default:
            redirectBackendError();
            break;
    }
}

function redirect403(url){
    redirect("/account/login?backUrl="+url);
}

function redirectBackendError(){
    redirect("/error.html");
}

function redirect404(){
    redirect("/error");
}

function redirectToMainSite(){
    redirect("/");
}

function redirect(url){
    url = url.replace(/&/g,"%26");
    window.location.href=url;
}

function resizeFooter(){
    var contentHeight = $(window).height() - 150;
    $(".content-wrapper").css("min-height", contentHeight + "px");
}
