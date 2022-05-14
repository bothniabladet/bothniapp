<#macro defaultsection title>
    <section class="container px-4 py-5">
        <header>
            <h2 class="pb-2 border-bottom">${title!""}</h2>
        </header>
        <div class="mb-5">
            <#nested>
        </div>
    </section>
</#macro>