<#-- @ftlvariable name="image" type="se.ltu.student.models.image.ImageModel" -->
<#import "../directives/section.ftl" as section />
<#import "../_layout.ftl" as layout />
<@layout.header>
    <@section.defaultsection title=image.caption!"">
        <div class="row">
            <div class="col-md-6 col-12">
                <figure class="figure">
                    <img src="/image/${image.id}/preview" class="img-fluid" alt="">
                    <figcaption class="figure-caption mt-1">${image.caption!""}</figcaption>
                </figure>
            </div>
            <div class="col-md-6 col-12">
                <a class="btn btn-outline-primary border-0" href="/image/${image.id}/edit"><i class="bi-pencil ms-auto"
                                                                                              title="Redigera bild"></i></a>
                <a class="btn btn-outline-primary border-0" href="/image/${image.id}/download"><i
                            class="bi-download ms-auto" title="Ladda ned bild"></i></a>
                <#if image.parent??>
                    <form class="d-inline" action="/image/${image.id}/decouple" method="post">
                        <button class="btn btn-outline-primary border-0"><i class="bi-diagram-2 ms-auto"
                                                                            title="Frikoppla"></i></button>
                    </form>
                </#if>

                <hr/>

                <dl>
                    <#if image.parent??>
                        <dt>Original</dt>
                        <dd><a href="/image/${image.parent.id}">${image.parent.caption!""}</a></dd>
                    </#if>
                    <dt>Kategori</dt>
                    <dd><#if image.category??><a
                            href="/category/${(image.category.slug)!""}">${(image.category.name)!""}</a><#else><em><a
                                    href="/category/uncategorized">Okategoriserat</a></em></#if></dd>
                    <dt>Fotograf</dt>
                    <dd><#if image.photographer??><a
                            href="/photographer/${(image.photographer.id)!""}">${(image.photographer.givenName)!""} ${(image.photographer.familyName)!""}</a><#else>
                            <em>Ej angiven</em></#if></dd>
                    <dt>Bildkälla</dt>
                    <dd><#if image.imageSource??><a
                            href="/image-source/${(image.imageSource.id)!""}">${(image.imageSource.name)!""}</a><#else>Intern</#if>
                    </dd>
                    <dt>Filstorlek</dt>
                    <dd><#if image.size??>${(image.size)!""}<#else><em>Filstorlek saknas</em></#if></dd>
                    <dt>Bildstorlek</dt>
                    <dd><#if image.width??>${(image.width)!""} x ${(image.height)!""}<#else><em>Bildstorlek
                            saknas</em></#if></dd>
                    <dt>Publicerad</dt>
                    <dd><#if image.published>Ja<#else>Nej</#if></dd>
                </dl>

                <hr/>

                <#if !image.parent??>
                    <div class="list-group list-group-flush">
                        <div class="list-group-item fw-bold">Varianter</div>
                        <#list image.variants as variant>
                            <a href="/image/${variant.id}"
                               class="list-group-item list-group-item-action">${variant.caption}</a>
                        </#list>
                        <a href="/image/${image.id}/variant"
                           class="list-group-item list-group-item-action text-primary">Ny variant...</a>
                    </div>
                </#if>
            </div>
        </div>

        <#if image.metadata??>
            <div class="accordion accordion-flush" id="accordionFlushExample">
                <div class="accordion-item">
                    <h2 class="accordion-header" id="flush-headingOne">
                        <button class="accordion-button collapsed" type="button" data-bs-toggle="collapse"
                                data-bs-target="#flush-collapseOne" aria-expanded="false"
                                aria-controls="flush-collapseOne">
                            Metadata
                        </button>
                    </h2>
                    <div id="flush-collapseOne" class="accordion-collapse collapse" aria-labelledby="flush-headingOne"
                         data-bs-parent="#accordionFlushExample">
                        <div class="accordion-body">
                            <dl class="row">
                                <#list image.metadata.values as directory, tags>
                                    <dt class="col-sm-3">${directory}</dt>
                                    <dd class="col-sm-9">
                                        <#list tags as key, value>
                                            <p>${key}: ${value}</p>
                                        </#list>
                                    </dd>
                                </#list>
                            </dl>
                        </div>
                    </div>
                </div>
            </div>
        </#if>
    </@section.defaultsection>
</@layout.header>