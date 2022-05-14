<#-- @ftlvariable name="category" type="se.ltu.student.models.CategoryModel" -->
<#-- @ftlvariable name="images" type="kotlin.collections.List<se.ltu.student.models.ImageModel>" -->
<#import "../directives/section.ftl" as section />
<#import "../_layout.ftl" as layout />

<@layout.header>
    <@section.defaultsection title=category.name!"">
        <div class="row row-cols-1 row-cols-lg-3 align-items-stretch g-4 py-5">
            <#list images as image>
                <div class="col">
                    <div class="card card-cover h-100 overflow-hidden text-white bg-dark rounded-5 shadow-lg" style="background-image: url('/archive/image/${image.id}/preview');">
                        <div class="d-flex flex-column h-100 p-5 pb-3 text-white text-shadow-1">
                            <h2 class="pt-5 mt-5 mb-4 display-6 lh-1 fw-bold">${image.caption!""}</h2>
                            <ul class="d-flex list-unstyled mt-auto">
                                <li class="me-auto">
                                    <#if image.variants?size == 0><#else>+${image.variants?size} <#if image.variants?size == 1>variant<#else>varianter</#if></#if>
                                </li>
                                <li class="d-flex align-items-center me-3">
                                    <small><a class="text-white" href="/archive/image/${image.id}">Visa</a></small>
                                </li>
                                <li class="d-flex align-items-center">
                                    <small><a class="text-white" href="/archive/image/${image.id}/edit">Redigera</a></small>
                                </li>
                            </ul>
                        </div>
                    </div>
                </div>
            </#list>
        </div>
    </@section.defaultsection>
</@layout.header>