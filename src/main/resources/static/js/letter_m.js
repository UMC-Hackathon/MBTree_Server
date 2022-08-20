var slides = document.querySelector('.slides1'),
slide = document.querySelectorAll('.slides1 li'),
currentIdx = 0,
slideCount = slide.length,
slideWidth = 100,
slideMargin = 30,
prevBtn = document.querySelector('.prev1'),
nextBtn = document.querySelector('.next1');

slides.style.width = (slideWidth + slideMargin)*slideCount - slideMargin + 'px';

function moveSlide(num) {
    slides.style.left = -num * 130 + 'px';
    currentIdx = num;
}

nextBtn.addEventListener('click', function() {

    if(currentIdx<slideCount-3){
        moveSlide(currentIdx + 1);
        console.log(currentIdx);
    } else{
        moveSlide(0);
    }
});

prevBtn.addEventListener('click', function() {

    if(currentIdx > 0){
        moveSlide(currentIdx - 1);
    } else{
        moveSlide(slideCount - 3);
    }
});