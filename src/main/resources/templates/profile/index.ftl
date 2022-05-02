<#-- @ftlvariable name="user" type="se.ltu.student.models.User" -->
<#import "../_layout.ftl" as layout />
<@layout.header>
    <h1>Hello, ${user.givenName}!</h1>
    <h3>${user.email}</h3>
    <a href="/profile/edit">Edit profile</a>
</@layout.header>