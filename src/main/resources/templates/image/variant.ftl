<#-- @ftlvariable name="parent" type="String" -->
<#import "../_layout.ftl" as layout />

<@layout.header>
    <form class="g-5" action="/archive/image/${parent}/variant" method="post" enctype="multipart/form-data">
        <div class="input-group mb-3">
            <label for="formFile" class="form-label"></label>
            <input class="form-control" type="file" id="formFile" name="image" />
        </div>
        <div class="d-grid gap-4 inputBox text-center">
            <button type="submit" class="btn btn-primary">Ladda upp</button>
        </div>
    </form>
</@layout.header>
