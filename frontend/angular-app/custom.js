var defaultLang = 'ru';

var backendServerAddr = "/backend";
var backendTimeout = 5000;

var mailRegexp = /^([0-9a-zA-Z]([-.\w]*[0-9a-zA-Z])*@([0-9a-zA-Z][-\w]*[0-9a-zA-Z]\.)+[a-zA-Z]{2,9})$/;
var userPhoneRegexp = /^([0-9]){10}$/;
var passwordRegexp = /^[a-zA-Z0-9!@^*-.,=]{6,16}$/;
var numberRegexp = /^[0-9]+$/;
var numberWithDotRegexp = /^[0-9.]+$/;

var phoneMaskTemplate = "+7 (999) 999-99-99";

var publicUrls = [
	'/account/login',
	'/account/registration',
	'/account/confirm',
	'/account/error'
];

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

function displayErrorMessage(text){
    if(typeof text === 'undefined'){
        text = '';
    }

    if(text.length>0){
        $('#serverErrorModalBody').html(text);
    }

    $('#serverErrorModal').modal();
}

function closeModal(id){
    $('#'+id).modal('hide');
}

function showModal(id){
    $('#'+id).modal('show');
}

function lastArrayItem(arr){
	return arr[arr.length - 1];
}

function getYearsList(){
	var start = 1920;
	var now = new Date().getFullYear();
	var yearsList = [];
	
	for(var i = start; i < now; i++){
		yearsList.push(i);
	}
	
	return yearsList;
}

function getMonthsList(){
	var monthsList = 
	[
		{id:0, name:'Январь'},
		{id:1, name:'Февраль'},
		{id:2, name:'Март'},
		{id:3, name:'Апрель'},
		{id:4, name:'Май'},
		{id:5, name:'Июнь'},
		{id:6, name:'Июль'},
		{id:7, name:'Август'},
		{id:8, name:'Сентябрь'},
		{id:9, name:'Октябрь'},
		{id:10, name:'Ноябрь'},
		{id:11, name:'Декабрь'}
	];
	
	return monthsList;
}

function getDaysList(year, month){
	var d = new Date(year.toString(), month.toString(), 0).getDate();
	var daysList = [];

	for (var i = 0; i < d; i++) {
		daysList.push(i+1);
	}
	
	return daysList;
}

function getSexArray(){
	var sex =
	[
		{value:'MALE', name:'Мужской'},
		{value:'FEMALE', name:'Женский'}
	];
	
	return sex;
}

function numb2(numb){
    if (numb <= 9){
        return "0" + numb;
    }else{
        return numb;
    }
}
