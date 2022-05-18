<#-- @ftlvariable name="user" type="se.ltu.student.models.user.UserModel" -->
<#import "../directives/section.ftl" as section />
<#import "../_layout.ftl" as layout />
<@layout.header>
    <@section.defaultsection title="Redigera profil">
        <div class="container mt-4" style="max-width: 500px">
            <form action="/profile/edit" method="post">
                <div class="form-floating mb-3">
                    <input type="text" class="form-control rounded-4" id="floatingUserIdentity" value="${user.id!""}" disabled>
                    <label for="floatingUserIdentity">Användaridentitet</label>
                </div>
                <div class="form-floating mb-3">
                    <input type="text" class="form-control rounded-4" name="givenName" id="floatingGivenName" placeholder="Anders" value="${user.givenName!""}">
                    <label for="floatingInput">Förnamn</label>
                </div>
                <div class="form-floating mb-3">
                    <input type="text" class="form-control rounded-4" name="familyName" id="floatingFamilyName" placeholder="Andersson" value="${user.familyName!""}">
                    <label for="floatingInput">Efternamn</label>
                </div>
                <div class="form-floating mb-3">
                    <input type="email" class="form-control rounded-4" name="email" id="floatingEmail" placeholder="namn@exempel.se" value="${user.email!""}" disabled>
                    <label for="floatingInput">E-postadress</label>
                </div>

                <div class="mb-3">
                    <button class="btn btn-primary btn-lg rounded-4 w-100" type="submit">Spara</button>
                </div>
            </form>
        </div>
    </@section.defaultsection>
</@layout.header>