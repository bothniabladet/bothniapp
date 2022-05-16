<#-- @ftlvariable name="categories" type="kotlin.collections.List<se.ltu.student.models.CategoryModel>" -->
<#import "../_layout.ftl" as layout />
<#import '../directives/section.ftl' as section>
<@layout.header>
    <@section.defaultsection title="Arkiv">
        <div class="row row-cols-1 row-cols-sm-2 row-cols-md-3 row-cols-lg-4 g-5 py-5">
            <#list categories as category>
                <div class="col d-flex align-items-start">
                    <i class="bi-images text-muted flex-shrink-0 me-3" style="font-size: 1.75em"></i>
                    <div>
                        <h4 class="fw-bold mb-0">${category.name!""}</h4>
                        <p>${category.description!""}</p>
                        <a href="/archive/${category.slug}" class="icon-link">
                            Call to action
                            <i class="bi bi-chevron-right"></i>
                        </a>
                    </div>
                </div>
            </#list>
            <div class="col d-flex align-items-start">
                <i class="bi-images text-muted flex-shrink-0 me-3" style="font-size: 1.75em"></i>
                <div>
                    <h4 class="fw-bold mb-0">Okategoriserat</h4>
                    <p>Bilder som ej tillhör en annan kategori</p>
                    <a href="/archive/uncategorized" class="icon-link">
                        Call to action
                        <i class="bi bi-chevron-right"></i>
                    </a>
                </div>
            </div>
        </div>
    </@section.defaultsection>
</@layout.header>