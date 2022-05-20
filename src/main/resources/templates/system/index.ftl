<#import "../directives/section.ftl" as section />
<#import "../_layout.ftl" as layout />

<@layout.header>
    <@section.defaultsection title="Systemöversikt">
        <div class="row row-cols-1 row-cols-lg-3 align-items-stretch g-4 py-5">
            <div class="col">
                <div class="card card-cover h-100 overflow-hidden rounded-5 bg-light shadow-lg">
                    <div class="d-flex flex-column h-100 p-5">
                        <h4 class="mb-4 lh-1">Användare</h4>
                        <ul class="d-flex list-unstyled mt-auto mb-0">
                            <li class="d-flex align-items-center">
                                <small><a class="me-3" href="/user">Visa användare</a></small>
                            </li>
                        </ul>
                    </div>
                </div>
            </div>
            <div class="col">
                <div class="card card-cover h-100 overflow-hidden rounded-5 bg-light shadow-lg">
                    <div class="d-flex flex-column h-100 p-5">
                        <h4 class="mb-4 lh-1">Bildkällor</h4>
                        <ul class="d-flex list-unstyled mt-auto mb-0">
                            <li class="d-flex align-items-center">
                                <small><a class="me-3" href="/image-source">Visa bildkällor</a></small>
                                <small><a href="/image-source/new?redirect=/system">Ny bildkälla</a></small>
                            </li>
                        </ul>
                    </div>
                </div>
            </div>
            <div class="col">
                <div class="card card-cover h-100 overflow-hidden rounded-5 bg-light shadow-lg">
                    <div class="d-flex flex-column h-100 p-5">
                        <h4 class="mb-4 lh-1">Fotografer</h4>
                        <ul class="d-flex list-unstyled mt-auto mb-0">
                            <li class="d-flex align-items-center">
                                <small><a class="me-3" href="/photographer">Visa fotografer</a></small>
                                <small><a href="/photographer/new?redirect=/system">Ny fotograf</a></small>
                            </li>
                        </ul>
                    </div>
                </div>
            </div>
            <div class="col">
                <div class="card card-cover h-100 overflow-hidden rounded-5 bg-light shadow-lg">
                    <div class="d-flex flex-column h-100 p-5">
                        <h4 class="mb-4 lh-1">Kategorier</h4>
                        <ul class="d-flex list-unstyled mt-auto mb-0">
                            <li class="d-flex align-items-center">
                                <small><a class="me-3" href="/category">Visa kategorier</a></small>
                                <small><a href="/category/new?redirect=/system">Ny kategori</a></small>
                            </li>
                        </ul>
                    </div>
                </div>
            </div>
        </div>
    </@section.defaultsection>
</@layout.header>