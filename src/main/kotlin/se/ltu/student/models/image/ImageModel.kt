package se.ltu.student.models.image

import org.jetbrains.exposed.sql.SizedIterable
import se.ltu.student.models.category.CategoryModel
import se.ltu.student.models.category.toModel
import se.ltu.student.models.imagesource.ImageSourceModel
import se.ltu.student.models.imagesource.toModel
import se.ltu.student.models.photographer.PhotographerModel
import se.ltu.student.models.photographer.toModel

@kotlinx.serialization.Serializable
data class ImageModel constructor(
    val id: String,
    val parent: ImageModel?,
    val caption: String,
    val description: String?,
    val path: String?,
    val size: Int,
    val width: Int,
    val height: Int,
    val category: CategoryModel?,
    val photographer: PhotographerModel?,
    val imageSource: ImageSourceModel?,
    val metadata: ImageMetadata?,
    val variants: List<ImageModel>,
    val published: Boolean
)

fun ImageEntity.toModel(loadChildren: Boolean = true) = ImageModel(
    id.toString(),
    resolve(parent),
    caption,
    description,
    path,
    size,
    width,
    height,
    category?.toModel(),
    photographer?.toModel(false),
    imageSource?.toModel(),
    metadata,
    if (loadChildren) resolve(parent, variants) else listOf(),
    upload.empty()
)

// Workaround for recursive initializer violations
fun resolve(image: ImageEntity?): ImageModel? {
    return image?.toModel(false)
}

fun resolve(parent: ImageEntity?, images: SizedIterable<ImageEntity>): List<ImageModel> {
    if (parent != null)
        return listOf()
    return images.map(ImageEntity::toModel)
}

fun resolve(images: SizedIterable<ImageEntity>): List<ImageModel> {
    return images.map(ImageEntity::toModel)
}