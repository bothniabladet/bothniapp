<#import "_layout.ftl" as layout />
<#import 'directives/section.ftl' as section>
<@layout.header>
    <@section.defaultsection title="Sök">
        <input name="query" type="text" class="form-control form-control-lg" placeholder="Type to search..." value="${query!""}">
    </@section.defaultsection>
</@layout.header>