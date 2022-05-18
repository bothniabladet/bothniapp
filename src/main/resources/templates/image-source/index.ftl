<#-- @ftlvariable name="imageSource" type="se.ltu.student.models.imagesource.ImageSourceModel" -->
<#import "../directives/section.ftl" as section />
<#import "../_layout.ftl" as layout />

<@layout.header>
    <@section.defaultsection title=imageSource.name>
        <div class="d-flex justify-content-start align-items-center">
            <#if imageSource.website??><span><strong>Hemsida</strong>: <a
                        href="${imageSource.website}">${imageSource.website}</a></span></#if>
        </div>
        <#if imageSource.photographers?size == 0>
        <#else>
            <h3 class="mt-5">Fotografer</h3>
        </#if>
        <div class="row row-cols-1 row-cols-lg-3 align-items-stretch g-4 py-5">
            <#list imageSource.photographers as photographer>
                <div class="col">
                    <div class="card card-cover h-100 overflow-hidden rounded-5 bg-light shadow-lg">
                        <div class="d-flex flex-column h-100 p-5">
                            <h4 class="mb-4 lh-1">${photographer.givenName} ${photographer.familyName}</h4>
                            <ul class="d-flex list-unstyled mt-auto mb-0">
                                <li class="d-flex align-items-center me-3">
                                    <small><a href="/photographer/${photographer.id}">Visa</a></small>
                                </li>
                                <li class="d-flex align-items-center">
                                    <small><a href="/photographer/${photographer.id}/edit?redirect=/image-source/${imageSource.id!""}">Redigera</a></small>
                                </li>
                            </ul>
                        </div>
                    </div>
                </div>
            </#list>
        </div>
        <#if imageSource.photographers?size == 0>
            <div class="ratio text-center px-3 border rounded-5 text-muted"
                 style="border-style: dashed !important; --bs-aspect-ratio: 50%;">
                <div class="d-flex align-items-center justify-content-center flex-column w-100 h-100">
                    <h3>Inga fotografer</h3>
                    <p>Denna bildkälla har inga fotografer.</p>
                </div>
            </div>
        </#if>
        <h3 class="mt-5">Bilder</h3>
        <div class="row row-cols-1 row-cols-lg-3 align-items-stretch g-4 py-5">
            <#list imageSource.images as image>
                <div class="col">
                    <div class="card card-cover h-100 overflow-hidden text-white bg-dark rounded-5 shadow-lg"
                         style="background-image: url('/image/${image.id}/preview');">
                        <div class="d-flex flex-column h-100 p-5 pb-3 text-white text-shadow-1">
                            <h2 class="pt-5 mt-5 mb-4 display-6 lh-1 fw-bold">${image.caption!""}</h2>
                            <ul class="d-flex list-unstyled mt-auto">
                                <li class="me-auto">
                                    <small><#if image.variants?size == 0><#else>+${image.variants?size} <#if image.variants?size == 1>variant<#else>varianter</#if></#if></small>
                                </li>
                                <li class="d-flex align-items-center me-3">
                                    <small><a class="text-white" href="/image/${image.id}">Visa</a></small>
                                </li>
                                <li class="d-flex align-items-center">
                                    <small><a class="text-white"
                                              href="/image/${image.id}/edit?redirect=/image-source/${imageSource.id!""}">Redigera</a></small>
                                </li>
                            </ul>
                        </div>
                    </div>
                </div>
            </#list>
        </div>
        <#if imageSource.images?size == 0>
            <div class="ratio text-center px-3 border rounded-5 text-muted"
                 style="border-style: dashed !important; --bs-aspect-ratio: 50%;">
                <div class="d-flex align-items-center justify-content-center flex-column w-100 h-100">
                    <h3>Inga bilder</h3>
                    <p>Denna bildkälla har inga bilder.</p>
                </div>
            </div>
        </#if>
    </@section.defaultsection>
</@layout.header>