<#-- @ftlvariable name="redirect" type="String" -->
<#import "../_layout.ftl" as layout />
<@layout.header>
    <h1>Login</h1>
    <form action="/login<#if redirect??>?redirect=${redirect}</#if>" method="post">
        <input type="text" name="username" placeholder="Username">
        <input type="password" name="password" placeholder="Password">
        <button type="submit">Submit</button>
    </form>
</@layout.header>