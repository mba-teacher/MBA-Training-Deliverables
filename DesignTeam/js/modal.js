
$(function(){
    $('.js-modal-open').on('click',function(){
        $('.js-modal').fadeIn();
        return false;
    });
    $('.js-modal-close').on('click',function(){
        $('.js-modal').fadeOut();
        return false;
    });



        $('.js-modal-open2').on('click',function(){
        $('.js-modal2').fadeIn();
        return false;
    });
    $('.js-modal-close').on('click',function(){
        $('.js-modal2').fadeOut();
        return false;
    });

});