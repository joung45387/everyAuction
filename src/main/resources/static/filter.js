var sucess = document.getElementsByClassName("sucess");
var fail = document.getElementsByClassName("fail");
function showSucceed(){
    for( var i = 0; i < sucess.length; i++ ){
        var section1 = sucess.item(i);
        section1.style.display = "block";
    }
    for( var i = 0; i < fail.length; i++ ){
        var section1 = fail.item(i);
        section1.style.display = "none";
    }
}

function showFailed(){
    for( var i = 0; i < sucess.length; i++ ){
        var section1 = sucess.item(i);
        section1.style.display = "none";
    }
    for( var i = 0; i < fail.length; i++ ){
        var section1 = fail.item(i);
        section1.style.display = "block";
    }
}

function showAll(){
    for( var i = 0; i < sucess.length; i++ ){
        var section1 = sucess.item(i);
        section1.style.display = "block";
    }
    for( var i = 0; i < fail.length; i++ ){
        var section1 = fail.item(i);
        section1.style.display = "block";
    }
}