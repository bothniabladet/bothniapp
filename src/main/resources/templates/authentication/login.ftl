<#import "../_layout.ftl" as layout />
<@layout.header>
    <div class="container pt-md-5 pt-3 pb-md-5 pb-4" style="max-width: 500px;">
        <h1 class="mt-md-5 mt-4 mb-4">Logga in</h1>
        <form method="post">
            <div class="form-floating mb-3">
                <input type="email" class="form-control rounded-4" name="username" id="floatingInput"
                       placeholder="namn@exempel.se" autocomplete="username">
                <label for="floatingInput">E-postadress</label>
            </div>
            <div class="form-floating mb-3">
                <input type="password" class="form-control rounded-4" name="password" id="floatingPassword"
                       placeholder="Lösenord" autocomplete="current-password">
                <label for="floatingPassword">Lösenord</label>
            </div>
            <button class="w-100 mb-2 btn btn-lg rounded-4 btn-primary" type="submit">Logga in</button>
            <small class="text-muted">Om du glömt ditt lösenord, kontakta din systemadministratör.</small>
        </form>
    </div>
</@layout.header>