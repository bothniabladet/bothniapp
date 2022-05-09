<#macro defaultsection title description>
    <section>
        <header class="mt-5 mb-4">
            <h3>${title!""}</h3>
            <p>${description!""}</p>
        </header>
        <div class="my-4">
            <#nested>
        </div>
    </section>
</#macro>