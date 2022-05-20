<#-- @ftlvariable name="parent" type="String" -->
<#import "../_layout.ftl" as layout />
<#import "../directives/section.ftl" as section />

<@layout.header>
    <@section.defaultsection title="Ny variant">
        <div class="container mt-4" style="max-width: 500px">
            <form class="g-5" action="/image/${parent}/variant" method="post" enctype="multipart/form-data">
                <div class="input-group mb-3">
                    <input type="file" class="form-control" id="inputGroupFile" name="image"
                           aria-describedby="inputGroupFileAddon" aria-label="Upload">
                </div>
                <div class="d-grid gap-4 inputBox text-center">
                    <button type="submit" class="btn btn-primary">Ladda upp</button>
                </div>
            </form>
        </div>
    </@section.defaultsection>
</@layout.header>