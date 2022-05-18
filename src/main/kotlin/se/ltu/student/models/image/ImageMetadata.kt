package se.ltu.student.models.image

@kotlinx.serialization.Serializable
data class ImageMetadata(val values: Map<String, Map<String, String>>)