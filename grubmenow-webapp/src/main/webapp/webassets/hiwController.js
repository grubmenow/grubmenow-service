angular.module('gmnControllers').controller('HIWCtrl', function ($scope, $http) {
    //Update the Nav state
    $( "li", "#gmnNav" ).each(function( index ) {
        $( this ).removeClass('active');
    });
    $('#hiwNav').addClass('active');   
    $('#navbarCollapse').removeClass('in');
});

angular.module('gmnControllers').controller('FAQCtrl', function ($scope, $http) {
    //Update the Nav state
    $( "li", "#gmnNav" ).each(function( index ) {
        $( this ).removeClass('active');
    });
    $('#faqNav').addClass('active');   
    $('#navbarCollapse').removeClass('in');
});


angular.module('gmnControllers').controller('ContactUsController', function ($scope, $http) {
    //Update the Nav state
    $( "li", "#gmnNav" ).each(function( index ) {
        $( this ).removeClass('active');
    });
    $('#cuNav').addClass('active');   
    $('#navbarCollapse').removeClass('in');
});