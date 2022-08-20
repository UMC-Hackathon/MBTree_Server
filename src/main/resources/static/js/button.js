function colorChange(e) {
    var color = "white";
    var backcolor= "#31BA82";
    var btn = document.getElementById(e.getAttribute('id'));
    btn.style.backgroundColor = backcolor;
    btn.style.color = color;
}