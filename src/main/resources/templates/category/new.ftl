<#import "../directives/section.ftl" as section />
<#import "../_layout.ftl" as layout />
<@layout.header>
    <@section.defaultsection title="Ny kategori">
        <div class="container mt-4" style="max-width: 500px">
            <form method="post">
                <div class="form-floating mb-3">
                    <input type="text" class="form-control rounded-4" name="name" id="floatingName"
                           placeholder="Kultur" />
                    <label for="floatingName">Namn</label>
                </div>
                <div class="form-floating mb-3">
                    <input type="text" class="form-control rounded-4" name="slug" id="floatingSlug"
                           placeholder="kultur" pattern="[a\-z]*">
                    <label for="floatingSlug">URL-vänligt namn</label>
                </div>
                <div class="form-floating mb-3">
                    <textarea class="form-control rounded-4" name="description" id="floatingDescription"></textarea>
                    <label for="floatingDescription">URL-vänligt namn</label>
                </div>

                <div class="mb-3">
                    <button class="btn btn-primary btn-lg rounded-4 w-100" type="submit">Spara</button>
                </div>
            </form>
        </div>
    </@section.defaultsection>
</@layout.header>
