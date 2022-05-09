<#-- @ftlvariable name="upload" type="se.ltu.student.models.UploadModel" -->
<#import "../directives/section.ftl" as section />
<#import "../_layout.ftl" as layout />
<@layout.header>
    <@section.defaultsection title="Upload" description="Manage uploaded images">
        <#list upload.images as image>
            <li>
                <img class="img-fluid" src="/archive/image/${image.id}/preview" />
                <a href="/archive/image/${image.id}/edit">Image ${image.id}</a>
            </li>
        </#list>
    </@section.defaultsection>
    <section>
        <form action="/upload/${upload.id}/delete" method="post">
            <button type="submit">Delete</button>
        </form>
    </section>
</@layout.header>