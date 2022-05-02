package se.ltu.student.dao

import kotlinx.coroutines.runBlocking

class DAO {
    val user = DAOUser()
}

val dao: DAO = DAO().apply {
    runBlocking {
        // ...
    }
}