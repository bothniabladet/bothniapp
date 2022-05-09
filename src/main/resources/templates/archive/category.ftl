<#-- @ftlvariable name="category" type="se.ltu.student.models.CategoryModel" -->
<#import "../directives/section.ftl" as section />
<#import "../_layout.ftl" as layout />

<@layout.header>
    <@section.defaultsection title=category.name!"" description=category.description!"">

    </@section.defaultsection>
</@layout.header>