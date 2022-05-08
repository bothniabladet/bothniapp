<#-- @ftlvariable name="upload" type="se.ltu.student.models.UploadModel" -->
<#import "../_layout.ftl" as layout />
<@layout.header>
    <h1>Manage Upload</h1>
    <section class="mb-4">
        <h2>Images</h2>
        <ul>
            <#list upload.images as image>
                <li>
                    <img class="img-fluid" src="/archive/image/${image.id}/preview" />
                    <a href="/archive/image/${image.id}">Image ${image.id}</a>
                </li>
            </#list>
        </ul>
    </section>
    <section>
        <form action="/upload/${upload.id}/delete" method="post">
            <button type="submit">Delete</button>
        </form>
    </section>
</@layout.header>