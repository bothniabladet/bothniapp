<#-- @ftlvariable name="uploadCount" type="Long" -->
<#-- @ftlvariable name="uploads" type="kotlin.sequences.Sequence<se.ltu.student.models.Upload>" -->
<#import "../_layout.ftl" as layout />
<@layout.header>
    <h1>Upload</h1>
    <#if uploadCount != 0>
    <section class="mb-4">
        <h2>Active Uploads</h2>
        <ul>
        <#list uploads as upload>
            <li>Upload ${upload.id} <a href="/upload/${upload.id}">Manage</a></li>
        </#list>
        </ul>
    </section>
    </#if>
    <section class="p-3 border bg-light rounded container">
        <h2>New upload</h2>
        <form class="g-5" action="/upload" method="post" enctype="multipart/form-data">
            <div class="input-group mb-3">
                <label for="formFileMultiple" class="form-label"></label>
                <input class="form-control" type="file" id="formFileMultiple" name="images" multiple />
            </div>
            <div class="d-grid gap-4 inputBox text-center">
                <button type="submit" class="btn btn-primary">Ladda upp bilder</button>

                <a class="btn btn-primary" href="main.html" role="button">Gå tillbaka</a>
            </div>
        </form>
    </section>

</@layout.header>