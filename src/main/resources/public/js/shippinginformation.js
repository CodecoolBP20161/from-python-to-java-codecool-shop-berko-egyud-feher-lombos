$(document).ready(function() {
    $('[id^=detail-]').hide();
    $('.toggle').click(function() {
        $input = $( this );
        $target = $('#'+$input.attr('data-toggle'));
        $target.slideToggle();
        if($input.find('.col-xs-2 i').attr('class')=="fa fa-chevron-down pull-right")
        {
            $input.find('.col-xs-2 i').removeClass('fa-chevron-down');
            $input.find('.col-xs-2 i').addClass('fa-chevron-up');
        }
        else if($input.find('.col-xs-2 i').attr('class')=="fa pull-right fa-chevron-up")
        {
            $input.find('.col-xs-2 i').removeClass('fa-chevron-up');
            $input.find('.col-xs-2 i').addClass('fa-chevron-down');
        }
        else if($input.find('.col-xs-2 i').attr('class')=="fa pull-right fa-chevron-down")
        {
            $input.find('.col-xs-2 i').removeClass('fa-chevron-down');
            $input.find('.col-xs-2 i').addClass('fa-chevron-up');
        }

    });
});
