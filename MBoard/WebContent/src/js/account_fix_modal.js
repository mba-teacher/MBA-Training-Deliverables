/**
 *
 */

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


	$('.open3').on('click',function(){
        $('.edit_main').fadeIn();
        return false;
    });
    $('.modal__bg, .close-mark').on('click',function(){
        $('.edit_main').fadeOut();
        return false;
    });


});