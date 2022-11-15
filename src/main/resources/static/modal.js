function test(sender){
    var testframe = document.createElement("iframe");
    testframe.id="pop";
    testframe.style.position = "fixed";
    testframe.style.left = "25%";
    testframe.style.top = "25%";
    testframe.style.width = "50%";
    testframe.style.height = "50%";
    testframe.src="/bidComplete/"+sender;
    document.getElementById("te").appendChild(testframe);
}

function link(url){
    location.href='bidComplete/'+url;
}
function parentclose(){
    var delpop = document.getElementById("pop");
    delpop.remove();
}
function x(){
    parent.window.parentclose();
    window.close();

    self.close();

    window.opener = window.location.href; self.close();

    window.open('about:blank', '_self').close();
}