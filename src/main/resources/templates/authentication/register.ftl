<#import "../_layout.ftl" as layout />
<@layout.header>
    <div class="container" style="max-width: 500px;">
        <h1 class="mt-md-5 mt-4 mb-4">Skapa konto</h1>
        <form method="post">
            <div class="form-floating mb-3">
                <input type="text" class="form-control rounded-4" name="givenName" id="floatingGivenName" placeholder="Anders" autocomplete="given-name">
                <label for="floatingInput">Förnamn</label>
            </div>
            <div class="form-floating mb-3">
                <input type="text" class="form-control rounded-4" name="familyName" id="floatingFamilyName" placeholder="Andersson" autocomplete="family-name">
                <label for="floatingInput">Efternamn</label>
            </div>
            <div class="form-floating mb-3">
                <input type="email" class="form-control rounded-4" name="email" id="floatingEmail" placeholder="namn@exempel.se" autocomplete="email">
                <label for="floatingInput">E-postadress</label>
            </div>
            <div class="form-floating mb-3">
                <input type="email" class="form-control rounded-4" name="repeatEmail" id="floatingRepeatEmail" placeholder="namn@exempel.se" autocomplete="email">
                <label for="floatingInput">Upprepa e-postadress</label>
            </div>
            <div class="form-floating mb-3">
                <input type="password" class="form-control rounded-4" name="password" id="floatingPassword" placeholder="Lösenord" autocomplete="new-password">
                <label for="floatingInput">Lösenord</label>
            </div>
            <div class="form-floating mb-3">
                <input type="password" class="form-control rounded-4" name="repeatPassword" id="floatingRepeatPassword" placeholder="Upprepa lösenord" autocomplete="new-password">
                <label for="floatingPassword">Upprepa lösenord</label>
            </div>
            <button class="w-100 mb-2 btn btn-lg rounded-4 btn-primary" type="submit">Skapa konto</button>
            <small class="text-muted">Genom att skapa ett konto godkänner du användarvillkoren.</small>
            <hr class="my-4">
            <h2 class="fs-5 fw-bold mb-3">Or use a third-party</h2>
            <button class="w-100 py-2 mb-2 btn btn-outline-dark rounded-4" type="submit">
                <svg class="bi me-1" width="16" height="16"><use xlink:href="#twitter"></use></svg>
                Sign up with Twitter
            </button>
            <button class="w-100 py-2 mb-2 btn btn-outline-primary rounded-4" type="submit">
                <svg class="bi me-1" width="16" height="16"><use xlink:href="#facebook"></use></svg>
                Sign up with Facebook
            </button>
            <button class="w-100 py-2 mb-2 btn btn-outline-secondary rounded-4" type="submit">
                <svg class="bi me-1" width="16" height="16"><use xlink:href="#github"></use></svg>
                Sign up with GitHub
            </button>
        </form>
    </div>
</@layout.header>