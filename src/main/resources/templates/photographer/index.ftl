<#-- @ftlvariable name="photographer" type="se.ltu.student.models.PhotographerModel" -->
<#import "../directives/section.ftl" as section />
<#import "../_layout.ftl" as layout />

<@layout.header>
    <@section.defaultsection title="${photographer.givenName} ${photographer.familyName}">
        <div class="row row-cols-1 row-cols-lg-3 align-items-stretch g-4 py-5">
            <#list photographer.images as image>
                <div class="col">
                    <div class="card card-cover h-100 overflow-hidden text-white bg-dark rounded-5 shadow-lg" style="background-image: url('/image/${image.id}/preview');">
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
                                    <small><a class="text-white" href="/image/${image.id}/edit?redirect=/photographer/${photographer.id!""}">Redigera</a></small>
                                </li>
                            </ul>
                        </div>
                    </div>
                </div>
            </#list>
        </div>
        <#if photographer.images?size == 0>
            <div class="ratio text-center px-3 border rounded-5 text-muted" style="border-style: dashed !important; --bs-aspect-ratio: 50%;">
                <div class="d-flex align-items-center justify-content-center flex-column w-100 h-100">
                    <h3>Inga bilder</h3>
                    <p>Denna fotograf har inga bilder.</p>
                </div>
            </div>
        </#if>
    </@section.defaultsection>
</@layout.header>