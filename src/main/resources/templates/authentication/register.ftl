<#import "../_layout.ftl" as layout />
<@layout.header>
    <h1>Register</h1>
    <form action="/register" method="post">
        <input type="text" name="givenName" placeholder="Given name">
        <input type="text" name="familyName" placeholder="Family name">
        <input type="email" name="email" placeholder="Email">
        <input type="email" name="repeatEmail" placeholder="Repeat email">
        <input type="password" name="password" placeholder="Password">
        <input type="password" name="repeatPassword" placeholder="Repeat Password">
        <button type="submit">Submit</button>
    </form>
</@layout.header>