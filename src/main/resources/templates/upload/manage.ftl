<#-- @ftlvariable name="upload" type="se.ltu.student.models.Upload" -->
<#-- @ftlvariable name="images" type="kotlin.collections.List<se.ltu.student.models.Image>" -->
<#import "../_layout.ftl" as layout />
<@layout.header>
    <h1>Manage Upload</h1>
    <section class="mb-4">
        <h2>Images</h2>
        <ul>
            <#list images as image>
                <li>
                    <img class="img-fluid" src="/archive/image/${image.id}/preview" />
                    Image ${image.id}
                </li>
            </#list>
        </ul>
    </section>
</@layout.header>