package se.ltu.student.models.photographer

import se.ltu.student.models.image.ImageModel
import se.ltu.student.models.image.resolve
import se.ltu.student.models.imagesource.ImageSourceModel
import se.ltu.student.models.imagesource.toModel

@kotlinx.serialization.Serializable
data class PhotographerModel constructor(
    val id: String,
    val givenName: String,
    val familyName: String,
    val email: String?,
    val phone: String?,
    val imageSource: ImageSourceModel?,
    val images: List<ImageModel>
)

fun PhotographerEntity.toModel(loadChildren: Boolean = false) = PhotographerModel(
    id.toString(),
    givenName,
    familyName,
    email,
    phone,
    imageSource?.toModel(),
    if (loadChildren) resolve(images).filter { image -> image.published && image.parent == null } else listOf())