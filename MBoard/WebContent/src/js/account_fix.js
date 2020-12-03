/**
 *
 */

jQuery("#realText input:text").on('click blur keydown keyup keypress change'
,function(){
var textWrite = jQuery("#realText input:text").val();
jQuery("#realWrite p").html(textWrite);
});


