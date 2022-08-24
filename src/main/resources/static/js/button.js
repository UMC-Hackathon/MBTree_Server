js/button.js

function colorChange1(e) {
    var color = "white";
    var backcolor= "#31BA82";
    var btnId = document.getElementById(e.getAttribute('id'));
    var IdValue = btnId.id;

    if(IdValue === 'Y1'){
        var otherId = document.getElementById('N1');
        otherId.style.backgroundColor = '#CBCBCB';
        otherId.style.color = '#191919';
    }else if (IdValue === 'N1'){
        var otherId = document.getElementById('Y1');
        otherId.style.backgroundColor = '#CBCBCB';
        otherId.style.color = '#191919';
    }

    btnId.style.backgroundColor = backcolor;
    btnId.style.color = color;
}

function colorChange2(e) {
    var color = "white";
    var backcolor= "#31BA82";
    var btnId = document.getElementById(e.getAttribute('id'));
    var IdValue = btnId.id;

    if(IdValue === 'Y2'){
        var otherId = document.getElementById('N2');
        otherId.style.backgroundColor = '#CBCBCB';
        otherId.style.color = '#191919';
    }else if (IdValue === 'N2'){
        var otherId = document.getElementById('Y2');
        otherId.style.backgroundColor = '#CBCBCB';
        otherId.style.color = '#191919';
    }

    btnId.style.backgroundColor = backcolor;
    btnId.style.color = color;
}

function colorChange3(e) {
    var color = "white";
    var backcolor= "#31BA82";
    var btnId = document.getElementById(e.getAttribute('id'));
    var IdValue = btnId.id;

    if(IdValue === 'Y3'){
        var otherId = document.getElementById('N3');
        otherId.style.backgroundColor = '#CBCBCB';
        otherId.style.color = '#191919';
    }else if (IdValue === 'N3'){
        var otherId = document.getElementById('Y3');
        otherId.style.backgroundColor = '#CBCBCB';
        otherId.style.color = '#191919';
    }

    btnId.style.backgroundColor = backcolor;
    btnId.style.color = color;
}


function colorChange(e) {
    var color = "white";
    var backcolor= "#31BA82";
    var btn = document.getElementById(e.getAttribute('id'));
    btn.style.backgroundColor = backcolor;
    btn.style.color = color;
}