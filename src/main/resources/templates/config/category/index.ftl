<#-- @ftlvariable name="categories" type="kotlin.collections.List<se.ltu.student.models.CategoryModel>" -->
<#import "../../_layout.ftl" as layout />
<@layout.header>
    <h1>Categories</h1>
    <section>
        <h3>Add category</h3>
        <form action="/config/category" method="post">
            <input type="text" name="name" />
            <button type="submit">Save</button>
        </form>
    </section>
    <section class="mb-4">
        <ul>
            <#list categories as category>
                <li>${category.name} <a href="/config/category/${category.id}">Edit</a></li>
            </#list>
        </ul>
    </section>
</@layout.header>