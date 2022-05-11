<#-- @ftlvariable name="categories" type="kotlin.collections.List<se.ltu.student.models.CategoryModel>" -->
<#import "../_layout.ftl" as layout />
<#import '../directives/section.ftl' as section>
<@layout.header>
    <@section.defaultsection title="Arkiv" description="Visa bilder i systemet">
    <#list categories as category>
        <li>Category ${category.id} <a href="/archive/${category.id}">${category.name}</a></li>
    </#list>
    </@section.defaultsection>
</@layout.header>