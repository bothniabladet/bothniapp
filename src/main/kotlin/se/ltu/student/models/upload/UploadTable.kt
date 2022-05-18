package se.ltu.student.models.upload

import se.ltu.student.models.BaseTable
import se.ltu.student.models.user.UserTable

object UploadTable : BaseTable("uploads") {
    val user = reference("user", UserTable).nullable()
}