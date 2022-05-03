<#import "../_layout.ftl" as layout />
<@layout.header>
    <h1>Upload</h1>
    <form class="g-5" action="/upload" method="post" enctype="multipart/form-data">
        <div class="input-group mb-3 container">
            <label for="formFileMultiple" class="form-label"></label>
            <input class="form-control" type="file" id="formFileMultiple" name="images" multiple />
        </div>
        <div class="d-grid gap-4 container inputBox text-center">
            <button type="submit" class="btn btn-primary">Ladda upp bilder</button>

            <a class="btn btn-primary" href="main.html" role="button">Gå tillbaka</a>
        </div>
    </form>
</@layout.header>