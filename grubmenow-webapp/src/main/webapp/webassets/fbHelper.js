var fbHelper = {
        initialized: 0,
        
        initialize: function(callback) {
            if(fbHelper.initialized) location.reload();
            // Load the SDK asynchronously
            (function(d, s, id) {
                var js, fjs = d.getElementsByTagName(s)[0];
                if (d.getElementById(id)) return;
                js = d.createElement(s); js.id = id;
                js.src = "//connect.facebook.net/en_US/sdk.js";
                fjs.parentNode.insertBefore(js, fjs);
            }(document, 'script', 'facebook-jssdk'));

            window.fbAsyncInit = function() {
                FB.init({
//                   appId      : '85199433896',
                    appId      : '591805167609392',
                    cookie     : true,  // enable cookies to allow the server to access the session
                    xfbml      : true,  // parse social plugins on this page
                    version    : 'v2.1' // use version 2.1
                });
                fbHelper.initialized = 1;
                callback(1);
            }
        },
        
        handleFBAuthResponse: function(response, callback) {
            var token = response.status == "connected" ? response.authResponse.accessToken : 0;
            if(!token) {
                callback(0, null, null);
            } else {
                fbHelper.getFBNameAndEmail(function(name, email){
                    callback(token, name, email);
                });
            }
        },
        
        getFBNameAndEmail: function(callback) {
            FB.api('/me', function(response) {
                if (response.name) {
                    callback(response.name, response.email)
                }
            });
        },
        
        getFBTokenNameAndEmail: function(callback) {
            FB.getLoginStatus(function(response) {
                fbHelper.handleFBAuthResponse(response, callback);
            });
        },
        
        addAuthChangeSubscription: function(callback) {
            FB.Event.subscribe('auth.authResponseChange', function(response) {
                fbHelper.handleFBAuthResponse(response, callback);
            });
        }
}
