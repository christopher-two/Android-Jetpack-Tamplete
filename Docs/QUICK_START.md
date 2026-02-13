# 🚀 Guía Rápida de Inicialización

## Inicio Rápido (5 minutos)

### 1️⃣ Clonar el Template

```bash
git clone <url-del-template> MiNuevoProyecto
cd MiNuevoProyecto
```

### 2️⃣ Ejecutar el Inicializador

```bash
./Scripts/init-project.sh
```

### 3️⃣ Responder las Preguntas

```
Nombre del proyecto: TaskManager
Package base: com.acme.taskmanager
Application ID: [presiona Enter para usar el mismo]
Nombre visible de la app: [presiona Enter para usar el mismo]
```

### 4️⃣ Confirmar y Ejecutar

```
¿Es correcta esta configuración? (S/n): S
¿Deseas crear un backup antes de continuar? (S/n): S
```

### 5️⃣ Sincronizar y Construir

```bash
./gradlew clean build
```

## 📖 Documentación Completa

Para información detallada, consulta:

- **[README.md](../README.md)** - Guía completa del template
- **[INIT_TEMPLATE.md](INIT_TEMPLATE.md)** - Documentación detallada del inicializador

## 🎯 Ejemplos Comunes

### Ejemplo 1: App Personal

```
Nombre del proyecto: MyWeatherApp
Package base: io.github.myuser.weather
Application ID: [usar el mismo]
Nombre visible: My Weather
```

### Ejemplo 2: App Empresarial

```
Nombre del proyecto: CorporateApp
Package base: com.mycompany.corporate
Application ID: [usar el mismo]
Nombre visible: Corporate Solutions
```

### Ejemplo 3: App con ID Diferente

```
Nombre del proyecto: ShoppingInternal
Package base: com.acme.internal.shopping
Application ID: com.acme.shopping
Nombre visible: ACME Shopping
```

## ⚠️ Notas Importantes

### Formato del Package

✅ **Correcto:**
- `com.mycompany.myapp`
- `io.github.username.project`
- `dev.myapp.android`

❌ **Incorrecto:**
- `MyApp` (sin dominio)
- `com.My-Company.app` (guiones)
- `com.mycompany` (muy corto)

### Formato del Nombre del Proyecto

✅ **Correcto:**
- `TaskManager`
- `MyAwesomeApp`
- `WeatherForecast`

❌ **Incorrecto:**
- `My App` (espacios)
- `task-manager` (guiones)
- `my_app` (underscores)

## 🔧 Scripts Disponibles

```bash
# Inicializar el template
./Scripts/init-project.sh

# Ver ejemplos de uso
./Scripts/example-usage.sh

# Verificar el template (antes de inicializar)
./Scripts/verify-template.sh

# Verificar ProGuard (después de configurar)
./Scripts/verify-proguard.sh
```

## 🆘 Solución de Problemas

### "kotlinc no está disponible"

**Solución**: Ejecuta el script desde Android Studio o instala Kotlin:

```bash
# Ubuntu/Debian
sudo snap install kotlin --classic

# macOS
brew install kotlin
```

### "Package inválido"

**Causa**: El package no sigue el formato correcto.

**Solución**: Usa el formato `com.company.app` (minúsculas, puntos).

### "El proyecto ya ha sido inicializado"

**Causa**: Ya ejecutaste el script anteriormente.

**Solución**: 
- Responde 's' para reinicializar (perderás cambios), o
- Clona nuevamente el template para un proyecto nuevo

## 📞 Necesitas Ayuda?

Consulta la documentación completa:
- [INIT_TEMPLATE.md](INIT_TEMPLATE.md) - Documentación completa
- [README.md](../README.md) - Guía del template
- [GitHub Issues] - Reportar problemas

---

**¡Listo para comenzar! 🎉**

