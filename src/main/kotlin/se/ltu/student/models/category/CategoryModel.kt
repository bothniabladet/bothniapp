package se.ltu.student.models.category

@kotlinx.serialization.Serializable
data class CategoryModel constructor(
    val id: String,
    val parent: CategoryModel?,
    val name: String,
    val slug: String,
    val description: String?
)

fun CategoryEntity.toModel() = CategoryModel(id.toString(), resolve(parent), name, slug ?: id.toString(), description)

// Workaround for recursive initializer violation
fun resolve(category: CategoryEntity?): CategoryModel? {
    return category?.toModel()
}