<#import "../_layout.ftl" as layout />
<@layout.header>
    <h1>Login</h1>
    <form action="/login" method="post">
        <input type="text" name="username" placeholder="Username">
        <input type="password" name="password" placeholder="Password">
        <button type="submit">Submit</button>
    </form>
</@layout.header>