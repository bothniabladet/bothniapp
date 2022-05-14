<#-- @ftlvariable name="categories" type="kotlin.collections.List<se.ltu.student.models.CategoryModel>" -->
<#-- @ftlvariable name="image" type="se.ltu.student.models.ImageModel" -->
<#import "../_layout.ftl" as layout />
<#import '../directives/section.ftl' as section>
<@layout.header>
    <section>
        <header class="mt-5 mb-4">
            <h3 id="caption">${image.caption!""}</h3>
            <p id="description">${image.description!""}</p>
        </header>
        <div class="my-4">
            <div class="row">
                <div class="col-md-6 col-12">
                    <figure class="figure">
                        <img src="/archive/image/${image.id}/preview" class="img-fluid" alt="">
                        <figcaption class="figure-caption mt-1">${image.caption!""}</figcaption>
                    </figure>
                </div>
                <div class="col-md-6 col-12">
                    <form action="/archive/image/${image.id}/edit" method="post">
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
                                <option <#if !image.category??>selected</#if> value="none">None</option>
                                <#list categories as category>
                                    <option value="${category.id}" <#if category.id == (image.category.id)!"">selected</#if>>${category.name}</option>
                                </#list>
                            </select>
                            <label for="categoryFormControlInput">Kategori</label>
                        </div>

                        <div class="mb-3">
                            <button class="btn btn-primary px-4" type="submit">Spara</button>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </section>
<script>
    const caption = document.querySelector('#caption');
    const description = document.querySelector('#description');

    const form = {
        caption: document.querySelector('#captionFormControlInput'),
        description: document.querySelector('#descriptionFormControlInput'),
    };

    form.caption.addEventListener('keyup', (event) => {
       caption.textContent = event.target.value;
    });

    form.description.addEventListener('keyup', (event) => {
        description.textContent = event.target.value;
    });
</script>
</@layout.header>