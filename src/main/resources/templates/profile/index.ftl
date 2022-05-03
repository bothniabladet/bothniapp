<#-- @ftlvariable name="userProfile" type="se.ltu.student.plugins.UserProfile" -->
<#import "../_layout.ftl" as layout />
<@layout.header>
    <h1>Hello, ${userProfile.givenName}!</h1>
    <h3>${userProfile.email}</h3>
    <a href="/profile/edit">Edit profile</a>
</@layout.header>