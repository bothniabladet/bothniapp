<#-- @ftlvariable name="imageSources" type="kotlin.collections.List<se.ltu.student.models.imagesource.ImageSourceModel>" -->
<#import "../directives/section.ftl" as section />
<#import "../_layout.ftl" as layout />

<@layout.header>
    <@section.defaultsection title="Bildkällor">
        <div class="row row-cols-1 row-cols-lg-3 align-items-stretch g-4 py-5">
            <div class="col">
                <div class="card card-cover h-100 overflow-hidden rounded-5">
                    <div class="d-flex flex-column justify-content-center h-100 p-5">
                        <a class="btn btn-primary" href="/image-source/new">Ny bildkälla</a>
                    </div>
                </div>
            </div>
            <#list imageSources as imageSource>
                <div class="col">
                    <div class="card card-cover h-100 overflow-hidden rounded-5 bg-light shadow-lg">
                        <div class="d-flex flex-column h-100 p-5">
                            <h4 class="mb-4 lh-1">${imageSource.name}</h4>
                            <ul class="d-flex list-unstyled mt-auto mb-0">
                                <li class="d-flex align-items-center me-3">
                                    <small><a href="/image-source/${imageSource.id}">Visa</a></small>
                                </li>
                                <li class="d-flex align-items-center">
                                    <small><a href="/image-source/${imageSource.id}/edit?redirect=/image-source">Redigera</a></small>
                                </li>
                            </ul>
                        </div>
                    </div>
                </div>
            </#list>
        </div>
    </@section.defaultsection>
</@layout.header>