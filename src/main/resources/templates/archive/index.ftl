<#-- @ftlvariable name="categories" type="kotlin.collections.List<se.ltu.student.models.CategoryModel>" -->
<#import "../_layout.ftl" as layout />
<@layout.header>
    <h1>Archive</h1>
    <#list categories as category>
        <li>Category ${category.id} <a href="/archive/${category.id}">${category.name}</a></li>
    </#list>
</@layout.header>