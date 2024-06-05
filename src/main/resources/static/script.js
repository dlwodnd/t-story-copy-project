window.onload = function() {
    // Parse the URL
    const url = new URL(window.location.href);

    // Get the access token from the URL
    const accessToken = url.searchParams.get('access_token');
    const username = url.searchParams.get('nickname');

    // Store the access token in the header
    // Note: This is a simple example. In a real application, you would likely use a more secure method of storing the token.

    // Display login success message
    if (accessToken) {
        document.getElementById('login-status').textContent = 'Login successful! ' + username + ', thank you.';

        // Change the URL to 'localhost:8080' without reloading the page
        window.history.replaceState({}, document.title, "/");
    }
};

document.getElementById('kakao-login').addEventListener('click', function() {
    window.location.href = '/oauth2/authorization/kakao?redirect_uri=http://localhost:8080/oauth/redirect'; // Change this URL to your application's Google OAuth2 authorization URL
});

document.getElementById('naver-login').addEventListener('click', function() {
    window.location.href = '/oauth2/authorization/kakao?redirect_uri=http://localhost:8080/oauth/redirect'; // Change this URL to your application's Facebook OAuth2 authorization URL
});