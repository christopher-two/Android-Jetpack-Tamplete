package org.override.tamplete.core.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

/**
 * Base de datos principal de la aplicación usando Room
 *
 * Room es una biblioteca de persistencia que proporciona una capa de abstracción
 * sobre SQLite para permitir un acceso más fluido a la base de datos.
 *
 * @Database: Marca esta clase como una base de datos Room
 * - entities: Lista de entidades (tablas) que formarán parte de la BD
 * - version: Versión de la base de datos (incrementar cuando cambien las tablas)
 * - exportSchema: Si es true, exporta el esquema de la BD a un archivo JSON
 */
@Database(
    entities = [
        // Aquí se agregan las entidades (tablas)
        // Ejemplo: UserEntity::class, ProductEntity::class
    ],
    version = 1,
    exportSchema = false
)
// @TypeConverters: Para convertir tipos de datos complejos a tipos que Room puede manejar
// Ejemplo: @TypeConverters(DateConverter::class, ListConverter::class)
abstract class AppDatabase : RoomDatabase() {

    /**
     * Aquí se definen los DAOs (Data Access Objects)
     * Los DAOs proporcionan métodos para acceder a los datos de la base de datos
     *
     * Ejemplo:
     * abstract fun userDao(): UserDao
     * abstract fun productDao(): ProductDao
     */

    companion object {
        const val DATABASE_NAME = "tamplete_database"
    }
}

