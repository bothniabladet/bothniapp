<#-- @ftlvariable name="category" type="se.ltu.student.models.category.CategoryModel" -->
<#import "../../_layout.ftl" as layout />
<@layout.header>
    <h1>Categories</h1>
    <section>
        <h3>Redigera kategori</h3>
        <form action="/config/category/${category.id}" method="post">
            <input type="text" name="name" value="${category.name!""}" />
            <textarea name="description">${category.description!""}</textarea>
            <input type="text" name="slug" value="${category.slug!""}" />
            <button type="submit">Save</button>
        </form>
    </section>
</@layout.header>