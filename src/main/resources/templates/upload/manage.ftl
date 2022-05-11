<#-- @ftlvariable name="upload" type="se.ltu.student.models.UploadModel" -->
<#import "../directives/section.ftl" as section />
<#import "../_layout.ftl" as layout />
<@layout.header>
    <@section.defaultsection title="Upload" description="Manage uploaded images">
        <#list upload.images as image>
            <div class="card mb-3">
                <img src="/archive/image/${image.id}/preview" class="img-fluid rounded-top" alt="...">
                <div class="card-body">
                    <h5 class="card-title">${image.caption!""}</h5>
                    <p class="card-text">
                        <#if image.description??>
                            ${image.description!""}
                        <#else>
                            <em>Bilden saknar beskrivning.</em>
                        </#if>
                    </p>
                    <div class="btn-group dropdown">
                        <a href="/archive/image/${image.id}/edit" class="btn btn-outline-secondary">Redigera</a>
                        <button class="btn btn-secondary dropdown-toggle" type="button" id="dropdownMenu" data-bs-toggle="dropdown" aria-expanded="false">
                            Åtgärder
                        </button>
                        <ul class="dropdown-menu" aria-labelledby="dropdownMenu">
                            <li><button class="dropdown-item" type="button">Action</button></li>
                            <li><button class="dropdown-item" type="button">Another action</button></li>
                            <li><button class="dropdown-item" type="button">Something else here</button></li>
                        </ul>
                    </div>
                    <p class="card-text"><small class="text-muted">Last updated 3 mins ago</small></p>
                </div>
            </div>
        </#list>
    </@section.defaultsection>
    <section>
        <form action="/upload/${upload.id}/delete" method="post">
            <button type="submit">Delete</button>
        </form>
        <form action="/upload/${upload.id}/complete" method="post">
            <button type="submit">Complete</button>
        </form>
    </section>
</@layout.header>