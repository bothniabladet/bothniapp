<#-- @ftlvariable name="image" type="se.ltu.student.models.ImageModel" -->
<#-- @ftlvariable name="categories" type="kotlin.collections.List<se.ltu.student.models.CategoryModel>" -->
<#import "../_layout.ftl" as layout />
<@layout.header>
    <h1>Image (edit)</h1>
    <form action="/archive/image/${image.id}/edit" method="post">
        <input type="text" name="caption" value="${image.caption!""}" />
        <textarea name="description">${image.description!""}</textarea>
        <select name="category">
            <option <#if !image.category??>selected</#if>>None</option>
            <#list categories as category>
                <option value="${category.id}" <#if category.id == (image.category.id)!"">selected</#if>>${category.name}</option>
            </#list>
        </select>
        <button type="submit">Save</button>
    </form>
</@layout.header>