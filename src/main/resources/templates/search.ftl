<#import "_layout.ftl" as layout />
<#import 'directives/section.ftl' as section>
<@layout.header>
    <@section.defaultsection title="Sök">
        <div class="container" style="max-width: 500px;">
            <input id="search" name="query" type="text" class="form-control form-control-lg rounded-5"
                   placeholder="Type to search..." value="${query!""}">
        </div>

        <ul id="results"></ul>
    </@section.defaultsection>

    <script src="/static/script/search.js"></script>
</@layout.header>