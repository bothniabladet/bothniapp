<#-- @ftlvariable name="user" type="se.ltu.student.plugins.UserSession" -->
<#import "_layout.ftl" as layout />
<@layout.header>
    <h1>Hello, ${user.name}!</h1>
</@layout.header>