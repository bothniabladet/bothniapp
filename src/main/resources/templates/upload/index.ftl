<#-- @ftlvariable name="uploadCount" type="Long" -->
<#-- @ftlvariable name="uploads" type="kotlin.collections.List<se.ltu.student.models.UploadModel>" -->
<#import "../directives/section.ftl" as section />
<#import "../_layout.ftl" as layout />

<@layout.header>
    <#if uploadCount != 0>
        <@section.defaultsection title="Uploads" description="Your recent uploads">
        <div class="list-group">
        <#list uploads as upload>
            <a href="/upload/${upload.id}" class="list-group-item list-group-item-action">${upload.id}</a>
        </#list>
        </div>
        </@section.defaultsection>
    </#if>
    <section class="border bg-light rounded container py-4 my-5" style="max-width: 480px">
        <form action="/upload" method="post" enctype="multipart/form-data">
            <div class="vstack gap-3 mx-auto px-3">
                <h3 class="mb-0">Ny uppladdning</h3>
                <p class="mb-0">Välj bilder som skall laddas upp.</p>
                <div class="input-group">
                    <input type="file" class="form-control" id="inputGroupFile" name="images" aria-describedby="inputGroupFileAddon" aria-label="Upload" multiple>
                </div>
                <p class="mb-0">
                    <small>Du kan redigera bildtext efter uppladdning.</small>
                </p>
                <div class="hstack gap-2">
                    <button type="reset" class="btn w-100 btn-outline-primary">Rensa</button>
                    <button type="submit" class="btn w-100 btn-primary">Ladda upp</button>
                </div>
            </div>
        </form>
    </section>
</@layout.header>