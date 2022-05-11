<#-- @ftlvariable name="category" type="se.ltu.student.models.CategoryModel" -->
<#-- @ftlvariable name="images" type="kotlin.collections.List<se.ltu.student.models.ImageModel>" -->
<#import "../directives/section.ftl" as section />
<#import "../_layout.ftl" as layout />

<@layout.header>
    <@section.defaultsection title=category.name!"" description=category.description!"">
        <#list images as image>
            <a href="/archive/image/${image.id}">${image.caption}</a>
        </#list>
    </@section.defaultsection>
</@layout.header>