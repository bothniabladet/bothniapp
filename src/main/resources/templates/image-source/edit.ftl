<#-- @ftlvariable name="imageSource" type="se.ltu.student.models.imagesource.ImageSourceModel" -->
<#import "../directives/section.ftl" as section />
<#import "../_layout.ftl" as layout />
<@layout.header>
    <@section.defaultsection title="Redigera bildkälla">
        <div class="container mt-4" style="max-width: 500px">
            <form method="post">
                <div class="form-floating mb-3">
                    <input type="text" class="form-control rounded-4" name="name" id="floatingName"
                           placeholder="Bildföretaget AB" value="${imageSource.name!""}">
                    <label for="floatingInput">Namn</label>
                </div>
                <div class="form-floating mb-3">
                    <input type="text" class="form-control rounded-4" name="website" id="floatingWebsite"
                           placeholder="https://www.exempel.se" value="${imageSource.website!""}">
                    <label for="floatingWebsite">Hemsida</label>
                </div>

                <div class="mb-3">
                    <button class="btn btn-primary btn-lg rounded-4 w-100" type="submit">Spara</button>
                </div>
            </form>

            <hr />

            <div class="accordion" id="accordionExample">
                <div class="accordion-item">
                    <h2 class="accordion-header" id="headingOne">
                        <button class="accordion-button collapsed" type="button" data-bs-toggle="collapse" data-bs-target="#collapseOne" aria-expanded="false" aria-controls="collapseOne">
                            Radera
                        </button>
                    </h2>
                    <div id="collapseOne" class="accordion-collapse collapse" aria-labelledby="headingOne" data-bs-parent="#accordionExample">
                        <div class="accordion-body">
                            <form action="/image-source/${imageSource.id}/delete" method="post">
                                <button class="btn btn-lg btn-outline-danger w-100" type="submit">Radera</button>
                            </form>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </@section.defaultsection>
</@layout.header>