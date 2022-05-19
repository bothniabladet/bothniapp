<#-- @ftlvariable name="photographer" type="se.ltu.student.models.photographer.PhotographerModel" -->
<#-- @ftlvariable name="imageSources" type="kotlin.collections.List<se.ltu.student.models.ImageSourceModel>" -->
<#import "../directives/section.ftl" as section />
<#import "../_layout.ftl" as layout />
<@layout.header>
    <@section.defaultsection title="Redigera fotograf">
        <div class="container mt-4" style="max-width: 500px">
            <form method="post">
                <div class="form-floating mb-3">
                    <input type="text" class="form-control rounded-4" name="givenName" id="floatingGivenName"
                           placeholder="Anders" value="${photographer.givenName!""}">
                    <label for="floatingInput">Förnamn</label>
                </div>
                <div class="form-floating mb-3">
                    <input type="text" class="form-control rounded-4" name="familyName" id="floatingFamilyName"
                           placeholder="Andersson" value="${photographer.familyName!""}">
                    <label for="floatingInput">Efternamn</label>
                </div>
                <div class="form-floating mb-3">
                    <input type="email" class="form-control rounded-4" name="email" id="floatingEmail"
                           placeholder="namn@exempel.se" value="${photographer.email!""}">
                    <label for="floatingInput">E-postadress</label>
                </div>
                <div class="form-floating mb-3">
                    <input type="text" class="form-control rounded-4" name="phone" id="floatingPhone"
                           value="${photographer.phone!""}">
                    <label for="floatingPhone">Telefonnummer</label>
                </div>

                <div class="form-floating mb-3">
                    <select class="form-select rounded-4" id="imageSourceFormControlInput" name="imageSource">
                        <option disabled>Välj bildkälla</option>
                        <option <#if !photographer.imageSource??>selected</#if> value="none">Ingen</option>
                        <#list imageSources as imageSource>
                            <option value="${imageSource.id}"
                                    <#if imageSource.id == (photographer.imageSource.id)!"">selected</#if>>${imageSource.name}</option>
                        </#list>
                    </select>
                    <label for="imageSourceFormControlInput">Bildkälla</label>
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
                            <form action="/photographer/${photographer.id}/delete" method="post">
                                <button class="btn btn-lg btn-outline-danger w-100" type="submit">Radera</button>
                            </form>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </@section.defaultsection>
</@layout.header>