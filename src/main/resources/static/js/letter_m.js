

let slides1 = document.querySelector('.slides1'),
slide1 = document.querySelectorAll('.slides1 li'),
currentIdx1 = 0,
slideCount1 = slide1.length,
slideWidth1 = 100,
slideMargin1 = 30,
prevBtn1 = document.querySelector('.prev1'),
nextBtn1 = document.querySelector('.next1');

slides1.style.width = (slideWidth1 + slideMargin1)*slideCount1 - slideMargin1 + 'px';

function moveSlide1(num) {
    slides1.style.left = -num * 130 + 'px';
    currentIdx1 = num;
}

nextBtn1.addEventListener('click', function() {

    if(currentIdx1<slideCount1-3){
        moveSlide1(currentIdx1 + 1);
        console.log(currentIdx1);
    } else{
        moveSlide1(0);
    }
});

prevBtn1.addEventListener('click', function() {

    if(currentIdx1 > 0){
        moveSlide1(currentIdx1 - 1);
    } else{
        moveSlide1(slideCount1 - 3);
    }
});