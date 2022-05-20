<#-- @ftlvariable name="categories" type="kotlin.collections.List<se.ltu.student.models.category.CategoryModel>" -->
<#import "../directives/section.ftl" as section />
<#import "../_layout.ftl" as layout />

<@layout.header>
    <@section.defaultsection title="Kategorier">
        <div class="row row-cols-1 row-cols-lg-3 align-items-stretch g-4 py-5">
            <div class="col">
                <div class="card card-cover h-100 overflow-hidden rounded-5">
                    <div class="d-flex flex-column justify-content-center h-100 p-5">
                        <a class="btn btn-primary" href="/category/new">Ny kategori</a>
                    </div>
                </div>
            </div>
            <#list categories as category>
                <div class="col">
                    <div class="card card-cover h-100 overflow-hidden rounded-5 bg-light shadow-lg">
                        <div class="d-flex flex-column h-100 p-5">
                            <h4 class="mb-4 lh-1">${category.name}</h4>
                            <ul class="d-flex list-unstyled mt-auto mb-0">
                                <li class="d-flex align-items-center me-3">
                                    <small><a href="/category/${category.id}">Visa</a></small>
                                </li>
                                <li class="d-flex align-items-center">
                                    <small><a href="/category/${category.id}/edit?redirect=/category">Redigera</a></small>
                                </li>
                            </ul>
                        </div>
                    </div>
                </div>
            </#list>
        </div>
    </@section.defaultsection>
</@layout.header>
