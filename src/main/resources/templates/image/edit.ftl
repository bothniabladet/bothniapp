<#-- @ftlvariable name="categories" type="kotlin.collections.List<se.ltu.student.models.CategoryModel>" -->
<#-- @ftlvariable name="photographers" type="kotlin.collections.List<se.ltu.student.models.PhotographerModel>" -->
<#-- @ftlvariable name="imageSources" type="kotlin.collections.List<se.ltu.student.models.ImageSourceModel>" -->
<#-- @ftlvariable name="image" type="se.ltu.student.models.ImageModel" -->
<#import "../_layout.ftl" as layout />
<#import '../directives/section.ftl' as section>
<@layout.header>
    <@section.defaultsection title="Redigera bild">
        <div class="row">
            <div class="col-md-6 col-12">
                <figure class="figure">
                    <img src="/image/${image.id}/preview" class="img-fluid" alt="">
                    <figcaption class="figure-caption mt-1">${image.caption!""}</figcaption>
                </figure>
            </div>
            <div class="col-md-6 col-12">
                <form method="post">
                    <div class="form-floating mb-3">
                        <input type="text" class="form-control rounded-4" id="captionFormControlInput" name="caption" placeholder="Katt i Maxikasse..." value="${image.caption!""}">
                        <label for="captionFormControlInput">Bildtext</label>
                    </div>
                    <div class="form-floating mb-3">
                        <textarea class="form-control rounded-4" id="descriptionFormControlInput" name="description" style="height: 80px" placeholder="Denna katt föredrar att sova i en maxikasse snarare än på ett katträd...">${image.description!""}</textarea>
                        <label for="descriptionFormControlInput">Beskrivning</label>
                    </div>

                    <div class="form-floating mb-3">
                        <select class="form-select rounded-4" id="categoryFormControlInput" name="category">
                            <option disabled>Välj en kategori</option>
                            <option <#if !image.category??>selected</#if> value="none">Okategoriserat</option>
                            <#list categories as category>
                                <option value="${category.id}" <#if category.id == (image.category.id)!"">selected</#if>>${category.name}</option>
                            </#list>
                        </select>
                        <label for="categoryFormControlInput">Kategori</label>
                    </div>

                    <div class="form-floating mb-3">
                        <select class="form-select rounded-4" id="photographerFormControlInput" name="photographer">
                            <option disabled>Välj en fotograf</option>
                            <option <#if !image.photographer??>selected</#if> value="none">Ej angiven</option>
                            <#list photographers as photographer>
                                <option value="${photographer.id}" <#if photographer.id == (image.photographer.id)!"">selected</#if>>${photographer.givenName} ${photographer.familyName}</option>
                            </#list>
                        </select>
                        <label for="photographerFormControlInput">Fotograf</label>
                    </div>

                    <div class="form-floating mb-3">
                        <select class="form-select rounded-4" id="imageSourceFormControlInput" name="imageSource">
                            <option disabled>Välj en bildkälla</option>
                            <option <#if !image.imageSource??>selected</#if> value="none">Intern</option>
                            <#list imageSources as imageSource>
                                <option value="${imageSource.id}" <#if imageSource.id == (image.imageSource.id)!"">selected</#if>>${imageSource.name}</option>
                            </#list>
                        </select>
                        <label for="imageSourceFormControlInput">Bildkälla</label>
                    </div>

                    <div class="mb-3">
                        <button class="btn btn-primary btn-lg rounded-4 w-100" type="submit">Spara</button>
                    </div>
                </form>
                <hr />
                <div>
                    <button class="btn btn-outline-danger" id="deleteButton">Radera bild <i class="bi-trash3 ms-1"></i></button>
                    <form id="deletionChallenge" action="/image/${image.id}/delete<#if redirect??>?redirect=${redirect}</#if>" method="post" hidden>
                        <#if image.variants?size == 0>
                        <#else>
                            <p class="fw-bold">Bilden har ${image.variants?size} <#if image.variants?size == 1>variant<#else>varianter</#if> som också kommer att raderas. För att undvika att en variant raderas, öppna denna och klicka på &quot;frikoppla&quot;.</p>
                        </#if>

                        <p>För att kunna radera bilden måste du skriva &quot;<strong>radera bild</strong>&quot; i rutan nedan.</p>
                        <div class="form-floating mb-3">
                            <input type="text" class="form-control rounded-4" id="deleteChallengeConfirmation" placeholder="Radera bild">
                            <label for="deleteChallengeConfirmation">Bekräftelse</label>
                        </div>
                        <button class="btn btn-danger btn-lg w-100 rounded-4" id="confirmDeleteButton" type="submit" disabled>Ja, jag vill radera bilden <strong>permanent</strong>.</button>
                    </form>
                </div>
            </div>
        </div>
    </@section.defaultsection>
<script>
    const confirmationText = "radera bild";
    const deleteButton = document.querySelector('#deleteButton');
    const deletionChallenge = document.querySelector('#deletionChallenge');
    const deleteChallengeConfirmation = document.querySelector('#deleteChallengeConfirmation');
    const confirmDeleteButton = document.querySelector('#confirmDeleteButton');

    deleteButton.addEventListener('click', () => {
        deleteButton.setAttribute("hidden", "true");
        deletionChallenge.removeAttribute("hidden");

        deleteChallengeConfirmation.addEventListener('keyup', (event) => {
            if (event.target.value.toLowerCase() === confirmationText)
                confirmDeleteButton.removeAttribute("disabled");
            else
                confirmDeleteButton.setAttribute("disabled", "true");
        });
    });
</script>
</@layout.header>