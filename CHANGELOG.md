# 📋 Changelog del Template

Todos los cambios notables en este template serán documentados en este archivo.

El formato está basado en [Keep a Changelog](https://keepachangelog.com/es/1.0.0/),
y este proyecto adhiere a [Semantic Versioning](https://semver.org/lang/es/).

---

## [1.0.0] - 2026-02-12

### 🎉 Lanzamiento Inicial

#### ✨ Agregado

##### Sistema de Inicialización
- **Script de inicialización automática** (`init-project.main.kts`)
  - Configuración interactiva del proyecto
  - Renombrado automático de packages y namespaces
  - Reorganización de estructura de directorios
  - Validación de nombres de package
  - Sistema de backup opcional
  - Prevención de reinicialización accidental
- **Wrapper Bash** (`init-project.sh`) para ejecutar el script Kotlin
- **Documentación completa** del sistema de inicialización
- **Ejemplos de uso** con casos comunes
- **Archivo de configuración** (`project-config.template`)

##### Arquitectura
- **Arquitectura MVI** (Model-View-Intent) completa
- **Clean Architecture** con separación de capas
- Estructura modular por features
- Inyección de dependencias con **Koin 4.1.0**

##### UI/UX
- **Jetpack Compose** con BOM 2025.01.00
- **Material Design 3** con soporte completo
- **Tema Dinámico** (Material You)
- **Modo Oscuro** automático
- **SplashScreen API** nativo (Android 12+)
- **Edge-to-Edge** con WindowInsets
- Componentes UI reutilizables

##### Navegación
- **Navigation Compose 3.0** (type-safe)
- Navegación declarativa
- Deep linking preparado
- Back stack management

##### Datos
- **Room 2.6.1** para base de datos local
- **DataStore 1.1.1** para preferencias
- **Ktor 3.0.3** como cliente HTTP
- Repositorios con patrón Repository
- Serialización con **kotlinx-serialization**

##### Firebase (Opcional)
- **Firebase Auth** para autenticación
- **Firestore** para base de datos en la nube
- **Vertex AI** para IA generativa
- Configuración condicional (solo si existe google-services.json)

##### Características Adicionales
- **Coil 3.0.4** para carga de imágenes
- **WorkManager** para tareas en background
- **Biometric Auth** para autenticación biométrica
- **Material Kolor** para paletas de colores
- **QRose** para generación de códigos QR
- **RichText** para texto enriquecido
- **Haze** para efectos de blur
- **Accompanist Permissions** para permisos
- **FileKit** para gestión de archivos

##### Seguridad y Optimización
- **ProGuard** configurado y optimizado
- Rules para todas las librerías incluidas
- Reducción de APK ~70% en release
- Script de verificación de ProGuard

##### Testing
- **JUnit 4** para tests unitarios
- **Espresso** para tests instrumentados
- **Turbine** para testing de Flows
- **Koin Test** para testing de DI
- **Ktor Mock** para testing de red
- Estructura preparada para TDD

##### Documentación
- **README.md** completo con guía de inicio rápido
- **INIT_TEMPLATE.md** con documentación detallada del inicializador
- **ARQUITECTURA_MVI.md** explicando el patrón arquitectónico
- **FIREBASE_SETUP.md** para configuración de Firebase
- **PROGUARD.md** con reglas y mejores prácticas
- **THEME_SYSTEM.md** para personalización de temas
- **DEPENDENCIAS.md** con catálogo completo
- **SPLASH_SCREEN_CONFIG.md** para configuración del splash
- Comentarios extensivos en el código

##### DevOps
- **Gradle 8.7** con wrapper incluido
- **Kotlin 2.1.0** más reciente
- **Version Catalog** (libs.versions.toml)
- Build variants (debug/release)
- Scripts de automatización

#### 🔧 Configuración Técnica
- **Min SDK**: 29 (Android 10)
- **Target SDK**: 36 (Android 14)
- **Compile SDK**: 36 (Android 14)
- **Java**: 11
- **Kotlin**: 2.1.0
- **AGP**: 8.8.1

#### 📝 Notas
- Primer lanzamiento del template
- Sistema completamente funcional y listo para producción
- Todas las dependencias actualizadas a versiones estables más recientes
- Firebase es completamente opcional

---

## [Unreleased]

### 🚧 Por Implementar

Posibles mejoras futuras:
- [ ] Soporte para módulos multi-proyecto
- [ ] CI/CD templates (GitHub Actions, GitLab CI)
- [ ] Más ejemplos de features comunes
- [ ] Tests adicionales pre-configurados
- [ ] Configuración de lint personalizada
- [ ] Soporte para flavors (dev, staging, prod)
- [ ] Script de migración entre versiones del template

---

## Formato

### Tipos de cambios
- **✨ Agregado** - Nuevas características
- **🔧 Cambiado** - Cambios en funcionalidad existente
- **⚠️ Deprecado** - Características que serán removidas
- **🗑️ Removido** - Características eliminadas
- **🐛 Corregido** - Corrección de bugs
- **🔒 Seguridad** - Parches de seguridad

---

## Versionado

El template sigue **Semantic Versioning**:

- **MAJOR** (X.0.0): Cambios incompatibles con versiones anteriores
- **MINOR** (0.X.0): Nuevas características compatibles
- **PATCH** (0.0.X): Correcciones de bugs

---

## Migración entre Versiones

Cuando se lancen nuevas versiones del template, se incluirán guías de migración si son necesarias.

Por ahora, al estar en la versión 1.0.0, todos los proyectos iniciados desde este template contendrán esta versión en el archivo `.template-initialized`.

---

**Última actualización**: 2026-02-12

