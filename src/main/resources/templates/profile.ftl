<#-- @ftlvariable name="user" type="se.ltu.student.models.User" -->
<#import "_layout.ftl" as layout />
<@layout.header>
    <h1>Hello, ${user.givenName}!</h1>
</@layout.header>