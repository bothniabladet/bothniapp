<#-- @ftlvariable name="user" type="se.ltu.student.models.user.UserModel" -->
<#import "../_layout.ftl" as layout />
<@layout.header>
    <h1>Hej ${user.givenName}!</h1>
    <h3>${user.email}</h3>
    <a href="/profile/edit">Redigera profil</a>
</@layout.header>