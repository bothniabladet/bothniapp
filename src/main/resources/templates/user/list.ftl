<#-- @ftlvariable name="users" type="kotlin.collections.List<se.ltu.student.models.user.UserModel>" -->
<#import "../directives/section.ftl" as section />
<#import "../_layout.ftl" as layout />

<@layout.header>
    <@section.defaultsection title="Användare">
        <div class="row row-cols-1 row-cols-lg-3 align-items-stretch g-4 py-5">
            <#list users as user>
                <div class="col">
                    <div class="card card-cover h-100 overflow-hidden rounded-5 bg-light shadow-lg">
                        <div class="d-flex flex-column h-100 p-5">
                            <h4 class="mb-4 lh-1">${user.givenName!""} ${user.familyName!""}</h4>
                            <ul class="d-flex list-unstyled mt-auto mb-0">
                                <li class="d-flex align-items-center">
                                    <small><a href="/user/${user.id}/edit?redirect=/user">Redigera</a></small>
                                </li>
                            </ul>
                        </div>
                    </div>
                </div>
            </#list>
        </div>
    </@section.defaultsection>
</@layout.header>
