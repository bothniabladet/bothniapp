package se.ltu.student.models.imagesource

import org.jetbrains.exposed.sql.SizedIterable
import se.ltu.student.models.image.ImageModel
import se.ltu.student.models.image.resolve
import se.ltu.student.models.photographer.PhotographerEntity
import se.ltu.student.models.photographer.PhotographerModel
import se.ltu.student.models.photographer.toModel

@kotlinx.serialization.Serializable
data class ImageSourceModel constructor(
    val id: String,
    val name: String,
    val website: String?,
    val photographers: List<PhotographerModel>,
    val images: List<ImageModel>
)

fun ImageSourceEntity.toModel(loadChildren: Boolean = false) = ImageSourceModel(
    id.toString(),
    name,
    website,
    if (loadChildren) resolve(photographers) else listOf(),
    if (loadChildren) resolve(images) else listOf()
)

// Workaround for recursive initializer violation
fun resolve(photographers: SizedIterable<PhotographerEntity>): List<PhotographerModel> {
    return photographers.map(PhotographerEntity::toModel)
}