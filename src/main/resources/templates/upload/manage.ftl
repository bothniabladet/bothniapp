<#-- @ftlvariable name="upload" type="se.ltu.student.models.upload.UploadModel" -->
<#import "../directives/section.ftl" as section />
<#import "../_layout.ftl" as layout />
<@layout.header>
    <@section.defaultsection title="Uppladdade bilder">
        <div class="row">
            <div class="col-md-8 col-12">
                <div class="row row-cols-1 row-cols-lg-2 align-items-stretch g-4 py-5">
                    <#list upload.images as image>
                        <div class="col">
                            <div class="card card-cover h-100 overflow-hidden text-white bg-dark rounded-5 shadow-lg border-0"
                                 style="background-image: url('/image/${image.id}/preview'); min-height: 350px;">
                                <div class="d-flex flex-column h-100 text-dark">
                                    <h4 class="mt-0 mb-4 lh-1 fw-bold mt-4 mx-4 text-shadow-1 text-white bg-dark p-2">${image.caption!""}</h4>
                                    <ul class="d-flex list-unstyled mt-auto bg-light px-4 py-2 mb-0">
                                        <li class="me-auto">
                                            <small><strong>Kategori</strong>: ${(image.category.name)!"Okategoriserat"}
                                            </small><br/>
                                            <small><strong>Fotograf</strong>: <#if image.photographer??>${(image.photographer.givenName)!"Förnamn"} ${(image.photographer.familyName)!"Efternamn"}<#else>Ej angiven</#if>
                                            </small><br/>
                                            <small><strong>Bildkälla</strong>: ${(image.imageSource.name)!"Intern"}
                                            </small>
                                        </li>
                                        <li class="d-flex align-items-center">
                                            <small><a class="btn btn-primary btn-sm rounded-4"
                                                      href="/image/${image.id}/edit?redirect=/upload/${upload.id}">Redigera</a></small>
                                        </li>
                                    </ul>
                                </div>
                            </div>
                        </div>
                    </#list>
                </div>
            </div>
            <div class="col-md-4 col-12">
                <div class="py-5">
                    <div class="px-3">
                        <h3>Åtgärder</h3>
                        <p class="mb-4">Bilder måste publiceras innan de blir synliga i arkivet.</p>
                    </div>
                    <div class="accordion accordion-flush mb-4" id="accordionFlushExample">
                        <div class="accordion-item">
                            <h2 class="accordion-header" id="flush-headingOne">
                                <button class="accordion-button collapsed" type="button" data-bs-toggle="collapse"
                                        data-bs-target="#flush-collapseOne" aria-expanded="false"
                                        aria-controls="flush-collapseOne">
                                    Kategori
                                </button>
                            </h2>
                            <div id="flush-collapseOne" class="accordion-collapse collapse"
                                 aria-labelledby="flush-headingOne" data-bs-parent="#accordionFlushExample">
                                <div class="accordion-body">
                                    <form action="/upload/${upload.id}/batch-update" method="post">
                                        <div class="form-floating mb-3">
                                            <select class="form-select rounded-4" id="categoryFormControlInput"
                                                    name="category">
                                                <option disabled>Välj en kategori</option>
                                                <option value="none">Okategoriserat</option>
                                                <#list categories as category>
                                                    <option value="${category.id}"
                                                            <#if category.id == (image.category.id)!"">selected</#if>>${category.name}</option>
                                                </#list>
                                            </select>
                                            <label for="categoryFormControlInput">Kategori</label>
                                        </div>
                                        <button class="btn btn-primary rounded-4 w-100" type="submit">Tillämpa</button>
                                    </form>
                                </div>
                            </div>
                        </div>
                        <div class="accordion-item">
                            <h2 class="accordion-header" id="flush-headingTwo">
                                <button class="accordion-button collapsed" type="button" data-bs-toggle="collapse"
                                        data-bs-target="#flush-collapseTwo" aria-expanded="false"
                                        aria-controls="flush-collapseTwo">
                                    Fotograf
                                </button>
                            </h2>
                            <div id="flush-collapseTwo" class="accordion-collapse collapse"
                                 aria-labelledby="flush-headingTwo" data-bs-parent="#accordionFlushExample">
                                <div class="accordion-body">
                                    <form action="/upload/${upload.id}/batch-update" method="post">
                                        <div class="form-floating mb-3">
                                            <select class="form-select rounded-4" id="photographerFormControlInput"
                                                    name="photographer">
                                                <option disabled>Välj en fotograf</option>
                                                <option value="none">Ej angiven</option>
                                                <#list photographers as photographer>
                                                    <option value="${photographer.id}"
                                                            <#if photographer.id == (image.photographer.id)!"">selected</#if>>${photographer.givenName} ${photographer.familyName}</option>
                                                </#list>
                                            </select>
                                            <label for="photographerFormControlInput">Fotograf</label>
                                        </div>
                                        <button class="btn btn-primary rounded-4 w-100" type="submit">Tillämpa</button>
                                    </form>
                                </div>
                            </div>
                        </div>
                        <div class="accordion-item">
                            <h2 class="accordion-header" id="flush-headingThree">
                                <button class="accordion-button collapsed" type="button" data-bs-toggle="collapse"
                                        data-bs-target="#flush-collapseThree" aria-expanded="false"
                                        aria-controls="flush-collapseThree">
                                    Bildkälla
                                </button>
                            </h2>
                            <div id="flush-collapseThree" class="accordion-collapse collapse"
                                 aria-labelledby="flush-headingThree" data-bs-parent="#accordionFlushExample">
                                <div class="accordion-body">
                                    <form action="/upload/${upload.id}/batch-update" method="post">
                                        <div class="form-floating mb-3">
                                            <select class="form-select rounded-4" id="imageSourceFormControlInput"
                                                    name="imageSource">
                                                <option disabled>Välj en bildkälla</option>
                                                <option value="none">Intern</option>
                                                <#list imageSources as imageSource>
                                                    <option value="${imageSource.id}"
                                                            <#if imageSource.id == (image.imageSource.id)!"">selected</#if>>${imageSource.name}</option>
                                                </#list>
                                            </select>
                                            <label for="imageSourceFormControlInput">Bildkälla</label>
                                        </div>
                                        <button class="btn btn-primary rounded-4 w-100" type="submit">Tillämpa</button>
                                    </form>
                                </div>
                            </div>
                        </div>
                        <div class="accordion-item">
                            <h2 class="accordion-header" id="flush-headingFour">
                                <button class="accordion-button collapsed" type="button" data-bs-toggle="collapse"
                                        data-bs-target="#flush-collapseFour" aria-expanded="false"
                                        aria-controls="flush-collapseFour">
                                    Radera
                                </button>
                            </h2>
                            <div id="flush-collapseFour" class="accordion-collapse collapse"
                                 aria-labelledby="flush-headingFour" data-bs-parent="#accordionFlushExample">
                                <div class="accordion-body">
                                    <p>Raderar uppladdningen och alla dess bilder. <strong>Handlingen går inte att
                                            ångra.</strong></p>
                                    <form action="/upload/${upload.id}/delete" method="post">
                                        <button class="btn btn-outline-danger rounded-4 w-100" type="submit">Radera
                                            uppladdning
                                        </button>
                                    </form>
                                </div>
                            </div>
                        </div>
                    </div>

                    <form action="/upload/${upload.id}/publish" method="post">
                        <button class="btn btn-primary btn-lg rounded-4 w-100" type="submit">Publicera</button>
                    </form>
                </div>
            </div>
        </div>
    </@section.defaultsection>
    <section>

    </section>
</@layout.header>