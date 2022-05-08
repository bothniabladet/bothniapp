<#-- @ftlvariable name="image" type="se.ltu.student.models.ImageModel" -->
<#import "../_layout.ftl" as layout />
<@layout.header>
    <h1>Image</h1>
    <p>
        <a href="/archive/image/${image.id}/edit">Edit</a>
    </p>
    <p>
        <a href="/archive/image/${image.id}/variant">Add variant</a>
    </p>
    <img class="img-fluid" src="/archive/image/${image.id}/preview" />
    <h3>${image.caption!""}</h3>
    <p>Category: ${(image.category.name)!"None"}</p>
    <p>${image.description!""}</p>
    <p>Width: ${image.width}, Height: ${image.height}</p>
    <#if image.parent??>
        <a href="/archive/image/${image.parent.id}">${image.parent.caption} (parent)</a>
    </#if>
    <h3>Variants</h3>
    <ul>
        <#list image.variants as variant>
            <li>
                <a href="/archive/image/${variant.id}">${variant.caption}</a>
            </li>
        </#list>
    </ul>
    <#if image.metadata??>
        <#list image.metadata.values as directory, tags>
            <li>
                ${directory}
                <ul>
                    <#list tags as key, value>
                        <li>${key}: ${value}</li>
                    </#list>
                </ul>
            </li>
        </#list>
    </#if>
</@layout.header>